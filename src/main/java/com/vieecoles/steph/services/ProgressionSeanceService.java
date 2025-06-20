package com.vieecoles.steph.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import com.vieecoles.steph.dto.ProgressionSeanceDto;
import com.vieecoles.steph.entities.AppelNumerique;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.DetailProgressionSeance;
import com.vieecoles.steph.entities.ProgressionSeance;
import com.vieecoles.steph.entities.Seances;
import com.vieecoles.steph.util.DateUtils;

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

	public List<ProgressionSeance> getByEcoleAndAnnee(Long ecoleId, Long anneeId) {
		List<ProgressionSeance> list = new ArrayList<>();
		try {
			list = ProgressionSeance.find("seance.classe.ecole.id = ?1 and seance.annee = ?2", ecoleId, anneeId.toString()).list();
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Long countByEcoleAndAnnee(Long ecoleId, Long anneeId) {
		Long count = 0L;
		try {
			count = ProgressionSeance.find("seance.classe.ecole.id = ?1 and seance.annee = ?2", ecoleId, anneeId.toString()).count();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<ProgressionSeance> getByEcoleAndDate(Long ecoleId, Date date) {
		List<ProgressionSeance> list = new ArrayList<>();
		LocalDate dateToLocalDate = DateUtils.asLocalDate(date);
		date = java.sql.Date.valueOf(dateToLocalDate);
		try {
			list = ProgressionSeance.find("seance.classe.ecole.id = ?1 and seance.dateSeance = ?2", ecoleId, date).list();
		}catch(RuntimeException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Long countByEcoleAndDate(Long ecoleId, Date date) {
		Long count = 0L;
		LocalDate dateToLocalDate = DateUtils.asLocalDate(date);
		date = java.sql.Date.valueOf(dateToLocalDate);
		try {
			count = ProgressionSeance.find("seance.classe.ecole.id = ?1 and seance.dateSeance = ?2", ecoleId, date).count();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return count;
	}
}
