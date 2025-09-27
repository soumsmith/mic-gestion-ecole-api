package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import com.vieecoles.steph.dto.DetailProgressionDto;
import com.vieecoles.steph.dto.IdLongCodeLibelleDto;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.entities.Progression;
import com.vieecoles.steph.entities.ProgressionSeance;
import com.vieecoles.steph.projections.GenericProjectionStringId;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class DetailProgressionService implements PanacheRepositoryBase<DetailProgression, String> {

	public List<DetailProgression> getList() {
		return DetailProgression.listAll();
	}

	public DetailProgression getById(String id) {
		return DetailProgression.findById(id);
	}

	public List<DetailProgression> getByProgressionId(String id) {
		List<DetailProgression> list = new ArrayList<DetailProgression>();
		try {
			list = DetailProgression.find("progression.id = ?1 order by ordre, dateDeb, numTitre", id).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
			list = new ArrayList<DetailProgression>();
		}
		return list;
	}

	public List<GenericProjectionStringId> getProjectionIdByProgression(String id) {
		List<GenericProjectionStringId> list = new ArrayList<GenericProjectionStringId>();
		try {
			list = DetailProgression.find("progression.id = ?1 order by ordre, dateDeb, numTitre", id).project(GenericProjectionStringId.class).list();
		} catch (RuntimeException e) {
			e.printStackTrace();
			list = new ArrayList<GenericProjectionStringId>();
		}
		return list;
	}

	@Transactional
	public void create(DetailProgression detail) {
		UUID uuid = UUID.randomUUID();
		//Si l ordre n'est pas saisi on genere l'ordre
		if(detail.getOrdre() == null && detail.getProgression()!=null  && detail.getProgression().getId()!=null) {
			detail.setOrdre(getProjectionIdByProgression(detail.getProgression().getId()).size() + 1);
		}
		detail.setId(uuid.toString());
		detail.setDateCreation(new Date());
		detail.setDateUpdate(new Date());
		detail.persist();
	}

	@Transactional
	public void update(DetailProgression obj) {
		DetailProgression entity = DetailProgression.findById(obj.getId());
		entity.setHeure(obj.getHeure());
		entity.setNiveauTitre(obj.getNiveauTitre());
		entity.setNumTitre(obj.getNumTitre());
		entity.setTitre(obj.getTitre());
		entity.setOrdre(obj.getOrdre());
		entity.setNbreSeance(obj.getNbreSeance());
		entity.setPeriode(obj.getPeriode());
		entity.setDateDeb(obj.getDateDeb());
		entity.setDateFin(obj.getDateFin());
		entity.setDateUpdate(new Date());
		System.out.println("Detail progression mis à jour");
	}

	@Transactional
	public void delete(String id) {
		DetailProgression.deleteById(id);
	}

	@Transactional
	public String handleDelete(List<String> ids) {
		if (verifyBeforeDelete(ids)) {
			DetailProgression.delete("id in ?1", ids);
		} else {
			throw new RuntimeException("Erreur: Impossible d'effectuer la suppression dans détail progression");
		}
		System.out.println(String.format("Suppression de %s détail(s) progression", ids.size()) );
		return "Supression bien effectuée ";
	}

	/**
	 * Cette fonction vérifie si des détails de progresion peuvent être supprimés
	 *
	 * @param ids liste des id à supprimer
	 * @return true si l'ensemble est supprimable et false si au moins un élément
	 *         dans l'ensemble n'est pas supprimable
	 */
	public Boolean verifyBeforeDelete(List<String> ids) {
		List<DetailProgression> list;
		Boolean flat = true;
		try {
			list = DetailProgression.find("id in ?1", ids).list();
		} catch (RuntimeException e) {
			list = new ArrayList<DetailProgression>();
		}
		Long psSize = 0L;
		for (DetailProgression d : list) {
			try {
				psSize = ProgressionSeance.find("detailProgression.id = ?1", d.getId()).count();
			} catch (RuntimeException e) {

			}
			if (psSize > 0L) {
				flat = false;
				break;
			}
		}
		return flat;
	}

	public Boolean validator(DetailProgressionDto dto) {
		Boolean flat = true;
		if (dto.getPeriode() == null) {
			flat = false;
		} else if (dto.getNumLecon() == null) {
			flat = false;
		} else if (dto.getTitre() == null) {
			flat = false;
		} else if (dto.getHeure() == null) {
			flat = false;
		}
		return flat;
	}

	public Boolean ifAlreadyExist(DetailProgressionDto dto) {
		Boolean flat = false;
		if (dto.getId() != null) {
			flat = true;
		}
		return flat;
	}

	@Transactional
	public Message handleSave(DetailProgressionDto dto) {
		Message message = new Message();
		try {
			DetailProgression obj = convertToEntity(null, dto);
			if (obj.getId() != null && !obj.getId().isBlank()) {
				message.setTitle(Constants.UPDATE_TITLE);
				update(obj);
			} else {
				message.setTitle(Constants.RECORD_TITLE);
				create(obj);
			}
			message.setId(obj.getId());
			message.setType(Constants.SUCCESS_TYPE);
			message.setDetail("Opération dans Detail progression bien efectuée");
		} catch (RuntimeException e) {
			message.setType(Constants.ERROR_TYPE);
			message.setDetail(e.getMessage());
			e.printStackTrace();
		}
		return message;
	}

	public DetailProgression convertToEntity(DetailProgression entity, DetailProgressionDto dto) {
		if (entity == null) {
			entity = new DetailProgression();
		}
		if (dto == null) {
			return entity;
		}
		Progression progression = Progression.getEntityManager().getReference(Progression.class,
				dto.getProgressionId());
		entity.setId(dto.getId());
		entity.setPeriode(Periode.getEntityManager().getReference(Periode.class, dto.getPeriode().getId()));
		entity.setDateDeb(dto.getDateDeb());
		entity.setDateFin(dto.getDateFin());
		entity.setNumTitre(dto.getNumLecon());
		entity.setTitre(dto.getTitre());
		entity.setHeure(dto.getHeure());
		entity.setNbreSeance(dto.getNbreSeance());
		entity.setOrdre(dto.getOrdre());
		if (progression != null) {
			entity.setProgression(progression);
		}
		return entity;
	}

	public DetailProgressionDto convertToDto(DetailProgression entity, DetailProgressionDto dto) {
		if (dto == null && entity != null) {
			dto = new DetailProgressionDto();
		}
		if(entity==null) {
			return dto;
		}
		dto.setId(entity.getId());
		dto.setPeriode(new IdLongCodeLibelleDto(entity.getPeriode().getId(), null, entity.getPeriode().getLibelle()));
		dto.setDateDeb(entity.getDateDeb());
		dto.setDateFin(entity.getDateFin());
		dto.setNumLecon(entity.getNumTitre());
		dto.setTitre(entity.getTitre());
		dto.setHeure(entity.getHeure());
		dto.setProgressionId(entity.getProgression().getId());
		dto.setNbreSeance(entity.getNbreSeance());
		dto.setOrdre(entity.getOrdre());
		return dto;
	}
}
