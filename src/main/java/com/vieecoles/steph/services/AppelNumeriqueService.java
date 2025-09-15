package com.vieecoles.steph.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vieecoles.steph.dto.AppelEleveDto;
import com.vieecoles.steph.entities.AppelNumerique;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailAppelNumerique;
import com.vieecoles.steph.entities.Personnel;
import com.vieecoles.steph.entities.Seances;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@RequestScoped
public class AppelNumeriqueService implements PanacheRepositoryBase<AppelNumerique, String> {

	@Inject
	SeanceService seanceService;
	@Inject
	ClasseEleveService classeEleveService;
	@Inject
	ClasseService classeService;
	@Inject
	DetailAppelNumeriqueService detailAppel;

	public List<AppelNumerique> getAll() {
		return AppelNumerique.listAll();
	}

	@Transactional
	public String save(AppelNumerique appel) {
		appel.setId(UUID.randomUUID().toString());
		appel.persist();
		return appel.getId();
	}

	public AppelNumerique getById(String id) {
		return AppelNumerique.findById(id);
	}

	AppelNumeriqueDto dtoBuilder(List<ClasseEleve> listeClasseEleve, Seances seance,
			List<DetailAppelNumerique> appelExistant) {
		// Pour chaque eleve renseigner les infos necesaires
		AppelNumeriqueDto dto = new AppelNumeriqueDto();
		List<AppelEleveDto> eleveAppelList = new ArrayList<>();
		dto.setClasseId(seance.getClasse().getId());
		dto.setClasseCode(seance.getClasse().getCode());
		dto.setClasseLibelle(seance.getClasse().getLibelle());
		dto.setSeanceId(seance.getId());
		dto.setTypeSeance(seance.getTypeActivite().getLibelle());
		dto.setDateSeance(seance.getDateSeance());
		dto.setHeureDebutSeance(seance.getHeureDeb());
		dto.setHeureFinSeance(seance.getHeureFin());
		dto.setMatiereId(seance.getMatiere().getId());
		dto.setMatiereLibelle(seance.getMatiere().getLibelle());
		dto.setEnseignantNomPrenom(seance.getProfesseur().getNom() + " " + seance.getProfesseur().getPrenom());
		dto.setEffectif(listeClasseEleve.size());
		dto.setAbsenceCmpt(0);
		dto.setPresenceCmpt(listeClasseEleve.size());
		for (ClasseEleve ce : listeClasseEleve) {
			AppelEleveDto eleveAppel = new AppelEleveDto();
			eleveAppel.setInscriptionClasseEleveId(ce.getId());
			eleveAppel.setMatricule(ce.getInscription().getEleve().getMatricule());
			eleveAppel.setNom(ce.getInscription().getEleve().getNom());
			eleveAppel.setPrenoms(ce.getInscription().getEleve().getPrenom());
			Optional<DetailAppelNumerique> optClasseEleve = appelExistant.stream()
					.filter(x -> x.getClasseEleve().getId().equals(ce.getId())).findFirst();
			if (optClasseEleve.isPresent()) {
				eleveAppel.setPresence(optClasseEleve.get().getPresence());
				if (optClasseEleve.get().getPresence().equals(Constants.ABSENCE)) {
					dto.setPresenceCmpt(dto.getPresenceCmpt() - 1);
					dto.setAbsenceCmpt(dto.getAbsenceCmpt() + 1);
				}
			}
			eleveAppelList.add(eleveAppel);
		}
//		dto.setEleves(eleveAppelList.stream().sorted(Comparator.comparing(AppelNumeriqueDto.AppelEleveDto::getNom)).collect(Collectors.toList()));
		dto.setEleves(eleveAppelList);
		return dto;
	}

	public AppelNumerique saverFromDto(AppelNumeriqueDto dto) {
		AppelNumerique appel = getBySeance(dto.getSeanceId(), dto.getPosition());
		System.out.println("Heure debut ::: " + dto.getHeureDebutAppel());
//		appel.setCode(null);
		appel.setDate(new Date());
		Classe classe = classeService.findById(dto.getClasseId());
		appel.setEcole(classe.getEcole());
		appel.setHeureDebut(dto.getHeureDebutAppel());
		appel.setHeureFin(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		appel.setDuree(dto.getDuree());
//		System.out.println("HEURE DE FIN ::: " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		Personnel personnel = new Personnel();
		if (dto.getPersonnelId() != null)
			personnel.setId(Long.parseLong(dto.getPersonnelId()));
		appel.setPersonnel(personnel);
		Seances seance = new Seances();
		seance.setId(dto.getSeanceId());
		appel.setSeance(seance);
		appel.setPosition(dto.getPosition());
		if (appel.getId() == null) {
			save(appel);
		} else {
			update(appel);
		}
		detailAppel.saveHandle(dto, appel.getId());

		return appel;
	}

	public AppelNumeriqueDto getListeAppel(String seanceId, int position) {
		// obtenir la seance
		Seances seance = seanceService.findById(seanceId);
		// Liste de la classe pour l'annee
		List<ClasseEleve> listeClasseEleve = classeEleveService.getByClasseAnnee(seance.getClasse().getId(),
				Long.parseLong(seance.getAnnee()));
		// Rechercher un eventuel appel deja effectué et en extraire les presences
		AppelNumerique appelExistant = getBySeance(seanceId, position);
		List<DetailAppelNumerique> detailAppelExistant = detailAppel.getByAppel(appelExistant.getId());
		// Construire le dto
		AppelNumeriqueDto dto = dtoBuilder(listeClasseEleve, seance, detailAppelExistant);
		dto.setPosition(position);
		if (appelExistant.getId() != null)
			dto.setDateAppel(appelExistant.getDate());
		return dto;
	}

	public AppelNumerique getBySeance(String seanceId) {
		AppelNumerique apNum = new AppelNumerique();
		try {
			apNum = AppelNumerique.find("seance.id = ?1 and (position is null or position = 0)", seanceId)
					.singleResult();
		} catch (RuntimeException e) {
			if (e.getClass().equals(NoResultException.class)) {
				System.out.println("Aucune séance lié à l'appel numérique trouvée");
			} else {
				e.printStackTrace();
			}
		}
		System.out.println(seanceId);
		System.out.println(apNum);
		return apNum;
	}

	public List<AppelNumerique> getBySeanceWithDestruct(String seanceId) {
		List<AppelNumerique> apNum = new ArrayList<>();
		try {
			apNum = AppelNumerique.find("seance.id = ?1", seanceId).singleResult();
			return apNum;
		} catch (RuntimeException e) {
			return apNum;
		}
	}

	/**
	 * Cette méthode permet de savoir si une séance a deja fait l'objet d'un appel
	 * avec la position étant l occurence n d'une séance ayant été destructurée par
	 * rapport à l'unité de temps.
	 *
	 * @param seanceId
	 * @param position
	 * @return
	 */
	public AppelNumerique getBySeance(String seanceId, int position) {
		AppelNumerique apNum = new AppelNumerique();
		try {
			apNum = AppelNumerique.find("seance.id = ?1 and position =?2", seanceId, position).singleResult();
			return apNum;
		} catch (RuntimeException e) {
			return apNum;
		}
	}

	public List<AppelNumerique> getByEcoleAndAnnee(Long ecoleId, Long anneeId) {
		List<AppelNumerique> list = new ArrayList<>();
		try {
			list = AppelNumerique.find("ecole.id = ?1 and seance.annee = ?2 ", ecoleId, anneeId.toString()).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Long countByEcoleAndAnnee(Long ecoleId, Long anneeId) {
		Long count = 0L;
		try {
			count = AppelNumerique.find("ecole.id = ?1 and seance.annee = ?2 ", ecoleId, anneeId.toString()).count();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<AppelNumerique> getByEcoleAndDate(Long ecoleId, Date date) {
		List<AppelNumerique> list = new ArrayList<>();
		LocalDate dateToLocalDate = DateUtils.asLocalDate(date);
		date = java.sql.Date.valueOf(dateToLocalDate);
		try {
			list = AppelNumerique.find("ecole.id = ?1 and date = ?2 ", ecoleId, date).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Long countByEcoleAndDate(Long ecoleId, Date date) {
		LocalDate dateToLocalDate = DateUtils.asLocalDate(date);
		date = java.sql.Date.valueOf(dateToLocalDate);
		Long count = 0L;
		try {
			count = AppelNumerique.find("ecole.id = ?1 and date = ?2 ", ecoleId, date).count();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Transactional
	public void update(AppelNumerique appel) {
		AppelNumerique appelDb = findById(appel.getId());
		appelDb.setCode(appel.getCode());
		appelDb.setDate(appel.getDate());
		appelDb.setHeureDebut(appel.getHeureDebut());
		appelDb.setHeureFin(appel.getHeureFin());
		appelDb.setPersonnel(appel.getPersonnel());
		appelDb.setSeance(appel.getSeance());
		appelDb.setEcole(appel.getEcole());
		appelDb.setCommentaire(appel.getCommentaire());
	}
}
