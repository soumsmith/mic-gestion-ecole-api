package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.vieecoles.steph.dto.DetailProgressionDto;
import com.vieecoles.steph.dto.ProgressionDto;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.Progression;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ProgressionService implements PanacheRepositoryBase<Progression, String> {

	@Inject
	DetailProgressionService detailProgressionService;

	public ProgressionDto convertToFullDto(Progression entity) {
		ProgressionDto dto = convertToDto(entity);
		return addDetailToConvertedDto(dto);
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
		obj.setAnnee(dto.getAnnee());
		obj.setBranche(dto.getBranche());
		obj.setMatiere(dto.getMatiere());
		obj.setNiveauEnseignant(dto.getNiveau());
		return obj;
	}

	public ProgressionDto convertToDto(Progression entity) {
		ProgressionDto dto = new ProgressionDto();
		dto.setAnnee(entity.getAnnee());
		dto.setBranche(entity.getBranche());
		dto.setId(entity.getId());
		dto.setMatiere(entity.getMatiere());
		dto.setNiveau(entity.getNiveauEnseignant());
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

	@Transactional
	public String handleSave(ProgressionDto dto) {
		try {
			int heureTotal = 0;
			Progression obj = convertToEntity(dto);
			if (obj.getId() != null && !obj.getId().isBlank()) {
				update(obj);
			}else {
				create(obj);
			}
			if (dto.getDatas() != null && dto.getDatas().size() > 0) {
				heureTotal = dto.getDatas().stream().mapToInt(d -> d.getHeure().intValue()).sum();
				for (DetailProgressionDto detail : dto.getDatas()) {
					detail.setProgressionId(obj.getId());
					detailProgressionService.handleSave(detail);
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
}
