package com.vieecoles.steph.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.DetailProgressionDto;
import com.vieecoles.steph.dto.IdLongCodeLibelleDto;
import com.vieecoles.steph.dto.ProgressionDto;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.NiveauEnseignement;
import com.vieecoles.steph.entities.Progression;
import com.vieecoles.steph.entities.Seances;
import com.vieecoles.steph.projections.GenericProjectionStringId;
import com.vieecoles.steph.util.DateUtils;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ProgressionService implements PanacheRepositoryBase<Progression, String> {

	@Inject
	DetailProgressionService detailProgressionService;

	public ProgressionDto convertToFullDto(Progression entity) {
		ProgressionDto dto = convertToDto(entity);
		return addDetailToConvertedDto(dto);
	}

	public List<Progression> listByAnnee(Long annee) {
		List<Progression> list;
		try {
			list = Progression.find("annee.id = ?1", annee).list();
		} catch (RuntimeException e) {
			list = new ArrayList<Progression>();
		}
		return list;
	}

	public List<ProgressionDto> listDtoByAnnee(Long annee) {
		List<Progression> list = listByAnnee(annee);
		List<ProgressionDto> dtos = new ArrayList<ProgressionDto>();
		for (Progression p : list) {
			dtos.add(convertToDto(p));
		}
		return dtos;
	}

	public ProgressionDto getProgressionBySeance(String seanceId) {
		Seances seance = Seances.findById(seanceId);
		Progression prog = null;
		if (seance != null) {
			prog = getByAnneeAndNiveauAndBrancheAndMatiere(Long.parseLong(seance.getAnnee()),
					seance.getClasse().getEcole().getNiveauEnseignement().getId(),
					seance.getClasse().getBranche().getId(), seance.getMatiere().getMatiere().getId());
			if (prog != null) {
				return convertToFullDto(prog);
			}
		}
		return null;
	}

	/**
	 * Cette méthode renvoie la liste des progressions d'une matiere à la date
	 * actuelle.
	 *
	 * @param annee
	 * @param niveau
	 * @param branche
	 * @param matiere
	 * @return ProgressionDto
	 */
	public ProgressionDto getProgressionAtNow(Long annee, Long niveau, Long branche, Long matiere) {
		LocalDate today = LocalDate.now();
		Progression prog = getByAnneeAndNiveauAndBrancheAndMatiere(annee, niveau, branche, matiere);
		ProgressionDto dto = new ProgressionDto();
		if (prog != null) {
			dto = convertToFullDto(prog);
			if (dto.getDatas() != null && dto.getDatas().size() > 0) {
				try {
					List<DetailProgressionDto> details = dto.getDatas().stream()
							.filter(d -> d.getDateFin() != null && DateUtils
									.getDateWithStringPatternDDMMYYYY(d.getDateDeb().replace("/", "-")).isBefore(today))
							.collect(Collectors.toList());
					dto.setDatas(details);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
		return dto;
	}

	public ProgressionDto addDetailToConvertedDto(ProgressionDto dto) {
		List<DetailProgression> details = new ArrayList<DetailProgression>();
		if (dto.getId() != null) {
			details = detailProgressionService.getByProgressionId(dto.getId());
			for (DetailProgression obj : details) {
				dto.getDatas().add(detailProgressionService.convertToDto(obj, null));
			}
		}
		return dto;
	}

	public Progression convertToEntity(ProgressionDto dto) {
		Progression obj = new Progression();
		obj.setId(dto.getId());
		obj.setAnnee(AnneeScolaire.getEntityManager().getReference(AnneeScolaire.class, dto.getAnnee().getId()));
		obj.setBranche(Branche.getEntityManager().getReference(Branche.class, dto.getBranche().getId()));
		obj.setMatiere(Matiere.getEntityManager().getReference(Matiere.class, dto.getMatiere().getId()));
		obj.setNiveauEnseignant(
				NiveauEnseignement.getEntityManager().getReference(NiveauEnseignement.class, dto.getNiveau().getId()));
		return obj;
	}

	public ProgressionDto convertToDto(Progression entity) {
		ProgressionDto dto = new ProgressionDto();
		dto.setAnnee(new IdLongCodeLibelleDto(entity.getAnnee().getId(), null, entity.getAnnee().getCustomLibelle()));
		dto.setBranche(new IdLongCodeLibelleDto(entity.getBranche().getId(), null, entity.getBranche().getLibelle()));
		dto.setId(entity.getId());
		dto.setMatiere(new IdLongCodeLibelleDto(entity.getMatiere().getId(), null, entity.getMatiere().getLibelle()));
		dto.setNiveau(new IdLongCodeLibelleDto(entity.getNiveauEnseignant().getId(), null,
				entity.getNiveauEnseignant().getLibelle()));
		return dto;
	}

	@Transactional
	public void create(Progression entity) {
		UUID uuid = UUID.randomUUID();
		entity.setId(uuid.toString());
		entity.setDateCreation(new Date());
		entity.setDateUpdate(new Date());
		entity.persist();
	}

	public Boolean progressionValidator(ProgressionDto dto) {
		Boolean flag = true;
		if (dto.getAnnee().getId() != null && dto.getAnnee().getId() != 0) {
			flag = flag && true;
		} else {
			return false;
		}
		if (dto.getBranche().getId() != 0L) {
			flag = flag && true;
		} else {
			return false;
		}
		if (dto.getMatiere().getId() != null && dto.getMatiere().getId() != 0) {
			flag = flag && true;
		} else {
			return false;
		}
		if (dto.getNiveau().getId() != 0L) {
			flag = flag && true;
		} else {
			return false;
		}
		if (dto.getDatas() != null && dto.getDatas().size() > 0) {
			flag = flag && true;
		} else {
			return false;
		}
		// Faire la validation des données de détails

		return flag;
	}

	/**
	 * Vérifie si un enregistrement existe
	 * 
	 * @param dto
	 * @return true si un enregistrement existe déjà et false sinon
	 */
	public Boolean ifAlreadyExist(ProgressionDto dto) {
		// Vérifier si un nouvel enregistrement de la progression existe déjà
		if (dto.getId() != null) {
			// getById si valeur retournée
			return true;
		} else {
			Long count = getCountByAnneeAndNiveauAndBrancheAndMatiere(dto.getAnnee().getId(), dto.getNiveau().getId(),
					dto.getBranche().getId(), dto.getMatiere().getId());
			System.out.println("count  ::: " + count);
			if (count > 0) {
				return true;
			}
		}
		return false;
	}

	public Long getCountByAnneeAndNiveauAndBrancheAndMatiere(Long annee, Long niveau, Long branche, Long matiere) {
		return Progression.find("annee.id =?1 and niveauEnseignant.id=?2 and branche.id=?3 and matiere.id =?4", annee,
				niveau, branche, matiere).count();
	}

	public Progression getByAnneeAndNiveauAndBrancheAndMatiere(Long annee, Long niveau, Long branche, Long matiere) {
		Progression prog = null;
		try {
			prog = Progression.find("annee.id =?1 and niveauEnseignant.id=?2 and branche.id=?3 and matiere.id =?4",
					annee, niveau, branche, matiere).singleResult();
		} catch (Exception e) {
			prog = null;
		}
		return prog;
	}

	@Transactional
	public String handleSave(ProgressionDto dto) {
		try {
			int heureTotal = 0;
			Progression obj = convertToEntity(dto);
			if (obj.getId() != null && !obj.getId().isBlank()) {
				update(obj);
			} else {
				create(obj);
			}
			if (dto.getDatas() != null && dto.getDatas().size() > 0) {
				heureTotal = dto.getDatas().stream().mapToInt(d -> d.getHeure().intValue()).sum();
				int ordre = 1;
				for (DetailProgressionDto detail : dto.getDatas()) {
					detail.setOrdre(ordre);
					detail.setProgressionId(obj.getId());
					detailProgressionService.handleSave(detail);
					ordre++;
				}
				obj.setVolumeHoraire(heureTotal);
				update(obj);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return "Progression enregistrée avec succès";

	}

	public void update(Progression obj) {
		Progression entity = Progression.findById(obj.getId());
		entity.setAnnee(obj.getAnnee());
		entity.setBranche(obj.getBranche());
		entity.setDateUpdate(new Date());
		entity.setMatiere(obj.getMatiere());
		entity.setNiveauEnseignant(obj.getNiveauEnseignant());
		entity.setVolumeHoraire(obj.getVolumeHoraire());
		entity.setDateUpdate(new Date());
	}

	@Transactional
	public Boolean delete(String id) {
		return Progression.deleteById(id);
	}

	@Transactional
	public String handleDelete(String id) {
		try {
			List<GenericProjectionStringId> details = detailProgressionService.getProjectionIdByProgression(id);
			List<String> list = details.stream().map(d -> d.getId()).collect(Collectors.toList());
			if (list.size() > 0) {
				detailProgressionService.handleDelete(list);
			}
			delete(id);
			System.out.println("Progression supprimée");
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("Impossible de Supprimer la progression");
		}
		return "Progression supprimée";
	}
}
