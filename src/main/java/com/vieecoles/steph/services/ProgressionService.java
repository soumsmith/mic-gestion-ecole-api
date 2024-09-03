package com.vieecoles.steph.services;

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
import com.vieecoles.steph.projections.GenericProjectionStringId;

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

	public Boolean ifAlreadyExist(ProgressionDto dto) {
		// Vérifier si un nouvel enregistrement de la progression existe déjà
		if (dto.getId() != null) {
			// getById si valeur retournée
			return true;
		}
		// sinon get by annee, niveau, branche, matiere
		// si valeur retournée alors return true
		// sinon
		return false;
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
		return "OK";

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
