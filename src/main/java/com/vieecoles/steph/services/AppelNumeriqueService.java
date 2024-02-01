package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.vieecoles.steph.dto.AppelNumeriqueDtoAbstract.AppelEleveDto;
import com.vieecoles.steph.entities.AppelNumerique;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.DetailAppelNumerique;
import com.vieecoles.steph.entities.Seances;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class AppelNumeriqueService implements PanacheRepositoryBase<AppelNumerique, String> {

	@Inject
	SeanceService seanceService;
	@Inject
	ClasseEleveService classeEleveService;

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
		List<AppelNumeriqueDto.AppelEleveDto> eleveAppelList = new ArrayList<>();
		dto.setClasseId(seance.getClasse().getId());
		dto.setClasseCode(seance.getClasse().getCode());
		dto.setClasseLibelle(seance.getClasse().getLibelle());
		dto.setSeance(seance.getTypeActivite().getLibelle());
		dto.setDateSeance(seance.getDateSeance());
		dto.setHeureDebutSeance(seance.getHeureDeb());
		dto.setHeureFinSeance(seance.getHeureFin());
		dto.setMatiereId(seance.getMatiere().getId());
		dto.setMatiereLibelle(seance.getMatiere().getLibelle());
		dto.setEnseignantNomPrenom(seance.getProfesseur().getNom()+" "+seance.getProfesseur().getPrenom());
		dto.setEffectif(listeClasseEleve.size());
		for (ClasseEleve ce : listeClasseEleve) {
			AppelNumeriqueDto.AppelEleveDto eleveAppel = dto.new AppelEleveDto();
			eleveAppel.setInscriptionClasseEleveId(ce.getId());
			eleveAppel.setMatricule(ce.getInscription().getEleve().getMatricule());
			eleveAppel.setNom(ce.getInscription().getEleve().getNom());
			eleveAppel.setPrenoms(ce.getInscription().getEleve().getPrenom());
			Optional<DetailAppelNumerique> optClasseEleve = appelExistant.stream()
					.filter(x -> x.getClasseEleve().getId().equals(ce.getId())).findFirst();
			if (optClasseEleve.isPresent()) {
				eleveAppel.setPresence(optClasseEleve.get().getPresence());
			}
			eleveAppelList.add(eleveAppel);
		}
//		dto.setEleves(eleveAppelList.stream().sorted(Comparator.comparing(AppelNumeriqueDto.AppelEleveDto::getNom)).collect(Collectors.toList()));
		dto.setEleves(eleveAppelList);
		return dto;
	}

	public AppelNumeriqueDto getListeAppel(String seanceId) {
		// obtenir la seance

		Seances seance = seanceService.findById(seanceId);
		// Liste de la classe pour l'annee
		List<ClasseEleve> listeClasseEleve = classeEleveService.getByClasseAnnee(seance.getClasse().getId(),
				Long.parseLong(seance.getAnnee()));
		// Rechercher un eventuel appel deja effectué et en extraire les presences
		AppelNumerique appelExistant = getBySeance(seanceId);
		List<DetailAppelNumerique> detailAppelExistant = detailAppel.getByAppel(appelExistant.getId());
		// Construire le dto
		return dtoBuilder(listeClasseEleve, seance, detailAppelExistant);
	}

	public AppelNumerique getBySeance(String seanceId) {
		AppelNumerique apNum = new AppelNumerique();
		try {
			apNum = AppelNumerique.find("seance.id = ?1", seanceId).singleResult();
			return apNum;
		} catch (RuntimeException e) {
			return apNum;
		}
	}

	@Transactional
	public void update(AppelNumerique appel) {
		AppelNumerique appelDb = findById(appel.getId());
		appelDb.setCode(null);
		appelDb.setDate(null);
		appelDb.setHeureDebut(null);
		appelDb.setHeureFin(null);
		appelDb.setPersonnel(null);
		appelDb.setProgression(null);
		appelDb.setSeance(null);
		appelDb.setEcole(null);
		appelDb.setCommentaire(null);
	}
}
