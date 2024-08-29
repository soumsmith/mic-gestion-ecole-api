package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.vieecoles.steph.dto.DetailProgressionDto;
import com.vieecoles.steph.dto.IdLongCodeLibelleDto;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.entities.Periode;
import com.vieecoles.steph.entities.Progression;

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
			list = DetailProgression.find("progression.id = ?1 order by ordre, semaineDeb, numTitre", id).list();
		} catch (RuntimeException e) {
			list = new ArrayList<DetailProgression>();
		}
		return list;
	}

	@Transactional
	public void create(DetailProgression detail) {
		UUID uuid = UUID.randomUUID();
		detail.setId(uuid.toString());
		detail.setDateCreation(new Date());
		detail.setDateUpdate(new Date());
		detail.persist();
	}

	@Transactional
	public void update(DetailProgression obj) {
		DetailProgression entity = DetailProgression.findById(obj.getId());
		entity.setHeure(obj.getHeure());
		entity.setMoisDeb(obj.getMoisDeb());
		entity.setNiveauTitre(obj.getNiveauTitre());
		entity.setNumTitre(obj.getNumTitre());
		entity.setTitre(obj.getTitre());
		entity.setOrdre(obj.getOrdre());
		entity.setPeriode(obj.getPeriode());
		entity.setSemaineDeb(obj.getSemaineDeb());
		entity.setDateUpdate(new Date());
		System.out.println("Detail progression mis à jour");
	}

	public Boolean validator(DetailProgressionDto dto) {
		Boolean flat = true;
		if (dto.getPeriode() == null) {
			flat = false;
		} else if (dto.getMois() == null) {
			flat = false;
		} else if (dto.getSemaine() == null) {
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
		if(dto.getId() != null) {
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
		Progression progression = Progression.getEntityManager().getReference(Progression.class, dto.getProgressionId());
		entity.setId(dto.getId());
		entity.setPeriode(Periode.getEntityManager().getReference(Periode.class, dto.getPeriode().getId()));
		entity.setMoisDeb(dto.getMois());
		entity.setSemaineDeb(dto.getSemaine());
		entity.setNumTitre(dto.getNumLecon());
		entity.setTitre(dto.getTitre());
		entity.setHeure(dto.getHeure());
		entity.setOrdre(dto.getOrdre());
		if (progression != null) {
			entity.setProgression(progression);
		}
		return entity;
	}

	public DetailProgressionDto convertToDto(DetailProgression entity, DetailProgressionDto dto) {
		if (dto == null) {
			dto = new DetailProgressionDto();
		}
		dto.setId(entity.getId());
		dto.setPeriode(new IdLongCodeLibelleDto(entity.getPeriode().getId(), null, entity.getPeriode().getLibelle()));
		dto.setMois(entity.getMoisDeb());
		dto.setSemaine(entity.getSemaineDeb());
		dto.setNumLecon(entity.getNumTitre());
		dto.setTitre(entity.getTitre());
		dto.setHeure(entity.getHeure());
		dto.setProgressionId(entity.getProgression().getId());
		dto.setOrdre(entity.getOrdre());
		return dto;
	}
}
