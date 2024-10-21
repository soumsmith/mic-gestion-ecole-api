package com.vieecoles.steph.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.vieecoles.steph.dto.ProgressionSeanceDto;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.DetailProgressionSeance;
import com.vieecoles.steph.entities.ProgressionSeance;
import com.vieecoles.steph.entities.Seances;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ProgressionSeanceService implements PanacheRepositoryBase<ProgressionSeance, String> {

	@Inject
	DetailProgressionSeanceService detailProgressionSeanceService;

	@Transactional
	public String handleSave(ProgressionSeance progressionSeance) {
		try {
			String progressionSeanceId = "";
			// Verifier l'existance de l'enregistrement
			if (!ifExist(progressionSeance)) {
				// Si enregistrement non existant effectuer la création
				UUID uuid = UUID.randomUUID();
				progressionSeance.setId(uuid.toString());
				progressionSeance.setDateCreation(new Date());
				progressionSeance.setDateCessation(new Date());
				progressionSeance.persist();
				progressionSeanceId = uuid.toString();
			} else {
				// si enregistrememnt existant faire la modification
				ProgressionSeance p = getBySeanceAndPosition(progressionSeance.getSeance().getId(),
						progressionSeance.getPosition());
				if (p != null) {
					p.setDuree(progressionSeance.getDuree());
					p.setDureeTotale(progressionSeance.getDureeTotale());
					p.setObservations(progressionSeance.getObservations());
					p.setPosition(progressionSeance.getPosition());
					p.setDateCessation(new Date());
					p.setSeance(progressionSeance.getSeance());
					p.setAttachmentUrl(progressionSeance.getAttachmentUrl());
					progressionSeanceId = p.getId();
				} else {
					throw new RuntimeException("Progression Seance non trouvée pour modification");
				}
				List<DetailProgressionSeance> dpsAlreadySavedList = detailProgressionSeanceService
						.getListByProgressionSeance(p.getId());
				List<String> ids = dpsAlreadySavedList.stream().map(d -> d.getId()).collect(Collectors.toList());
				if (ids.size() > 0) {
					detailProgressionSeanceService.deleteMany(ids);
				}
			}

			for (String p : progressionSeance.getDetails()) {
				DetailProgressionSeance det = new DetailProgressionSeance();
				det.setDetailProgression(DetailProgression.getEntityManager().getReference(DetailProgression.class, p));
				det.setProgressionSeance(ProgressionSeance.getEntityManager().getReference(ProgressionSeance.class,
						progressionSeanceId));
				detailProgressionSeanceService.save(det);
			}

			return "Sauvegarde effectuée";
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new RuntimeException("Sauvegarde non effectuée");
		}

	}

	public Boolean ifExist(ProgressionSeance progressionSeance) {
		long size = getCountBySeanceAndPosition(progressionSeance.getSeance().getId(), progressionSeance.getPosition());
		if (size > 0) {
			return true;
		} else {
			return false;
		}
	}

	public ProgressionSeance getBySeanceAndPosition(String seanceId, Integer position) {
		ProgressionSeance obj = null;
		try {
			obj = ProgressionSeance.find("seance.id = ?1 and position = ? 2", seanceId, position).singleResult();
		} catch (RuntimeException e) {
			if (e.getClass().equals(NoResultException.class)) {
				System.out.println(e.getMessage());
			} else {
				e.printStackTrace();
			}
		}
		return obj;
	}

	public ProgressionSeance populate(ProgressionSeance ps) {
		if (ps != null) {
			List<DetailProgressionSeance> details = detailProgressionSeanceService
					.getListByProgressionSeance(ps.getId());
			ps.setDetails(details.stream().map(d -> d.getDetailProgression().getId()).collect(Collectors.toList()));
		}
		return ps;
	}

	public ProgressionSeanceDto convertToDto(ProgressionSeance obj) {
		ProgressionSeanceDto dto = new ProgressionSeanceDto();
		dto.setAttachmentUrl(obj.getAttachmentUrl());
		dto.setDetailProgressions(obj.getDetails());
		dto.setDuree(obj.getDuree());
		dto.setDureeTotale(obj.getDureeTotale());
		dto.setObservations(obj.getObservations());
		dto.setPosition(obj.getPosition());
		dto.setSeanceId(obj.getSeance().getId());
		return dto;
	}

	public ProgressionSeanceDto getDtoBySeanceAndPosition(String seance, Integer position) {
		ProgressionSeance ps = populate(getBySeanceAndPosition(seance, position));
		ProgressionSeanceDto dto = new ProgressionSeanceDto();
		if (ps != null) {
			dto = convertToDto(ps);
		}
		return dto;
	}

	public long getCountBySeanceAndPosition(String seanceId, Integer position) {
		return ProgressionSeance.find("seance.id = ?1 and position = ? 2", seanceId, position).count();
	}

	public ProgressionSeance convertToEntity(ProgressionSeanceDto dto) {
		ProgressionSeance entity = new ProgressionSeance();
		entity.setDuree(dto.getDuree());
		entity.setDureeTotale(dto.getDureeTotale());
		entity.setObservations(dto.getObservations());
		entity.setPosition(dto.getPosition());
		entity.setSeance(Seances.getEntityManager().getReference(Seances.class, dto.getSeanceId()));
		entity.setAttachmentUrl(dto.getAttachmentUrl());
		entity.setDetails(dto.getDetailProgressions());
		return entity;
	}
}
