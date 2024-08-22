package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.vieecoles.steph.dto.DetailProgressionDto;
import com.vieecoles.steph.entities.DetailProgression;
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
			list = DetailProgression.find("progression.id = ?1 ", id).list();
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
//		entity.setOrdre(obj.getOrdre());
		entity.setPeriodeId(obj.getPeriodeId());
		entity.setSemaineDeb(obj.getSemaineDeb());
		entity.setDateUpdate(new Date());
	}

	@Transactional
	public void handleSave(DetailProgressionDto dto) {
		try {
			DetailProgression obj = convertToEntity(null, dto);
			if (obj.getId() != null && !obj.getId().isBlank()) {
				update(obj);
			} else {
				create(obj);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	public DetailProgression convertToEntity(DetailProgression entity, DetailProgressionDto dto) {
		if (entity == null) {
			entity = new DetailProgression();
		}
		Progression progression = Progression.findById(dto.getProgressionId());
		entity.setId(dto.getId());
		entity.setPeriodeId(dto.getPeriode());
		entity.setMoisDeb(dto.getPeriode());
		entity.setSemaineDeb(dto.getSemaine());
		entity.setNiveauTitre(null);
		entity.setNumTitre(dto.getNumLecon());
		entity.setTitre(dto.getTitre());
		entity.setHeure(dto.getHeure());
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
		dto.setPeriode(entity.getPeriodeId());
		dto.setMois(entity.getMoisDeb());
		dto.setSemaine(entity.getSemaineDeb());
		dto.setNumLecon(entity.getNumTitre());
		dto.setTitre(entity.getTitre());
		dto.setHeure(entity.getHeure());
		return dto;
	}
}
