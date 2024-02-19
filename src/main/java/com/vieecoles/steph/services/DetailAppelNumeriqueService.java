package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import com.vieecoles.steph.dto.AppelEleveDto;
import com.vieecoles.steph.entities.AppelNumerique;
import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailAppelNumerique;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@RequestScoped
public class DetailAppelNumeriqueService implements PanacheRepositoryBase<DetailAppelNumerique, String> {

	Logger logger = Logger.getLogger(this.getClass().getName());

	public List<DetailAppelNumerique> getByAppel(String appelId) {
		List<DetailAppelNumerique> details = new ArrayList<>();
		try {
			details = DetailAppelNumerique.find("appelNumerique.id = ?1 ", appelId).list();
		} catch (RuntimeException e) {
			logger.warning(e.getMessage());
		}
		return details;
	}

	@Transactional
	public void save(DetailAppelNumerique detail) {
		detail.setId(UUID.randomUUID().toString());
		detail.setDateCreation(new Date());
		detail.setDateUpdate(new Date());
		detail.persist();
	}

	List<DetailAppelNumerique> convertDtoToEntity(List<AppelEleveDto> list, String appelNumeriqueId) {
		List<DetailAppelNumerique> detailList = new ArrayList<DetailAppelNumerique>();
		for (AppelEleveDto det : list) {
			DetailAppelNumerique detail = new DetailAppelNumerique();
			ClasseEleve ce = new ClasseEleve();
			ce.setId(det.getInscriptionClasseEleveId());
			detail.setClasseEleve(ce);
			AppelNumerique aNum = new AppelNumerique();
			aNum.setId(appelNumeriqueId);
			detail.setAppelNumerique(aNum);
			if (det.getPresence() != null && det.getPresence().equals(Constants.ABSENCE)) {
				detail.setPresence(det.getPresence());
			} else {
				detail.setPresence(Constants.PRESENCE);
			}
			detailList.add(detail);
		}
		return detailList;
	}
	
	@Transactional
	void removeExistingDetail(String appelId) {
		List<DetailAppelNumerique> details = getByAppel(appelId);
		for(DetailAppelNumerique det : details) {
			det.delete();
		}
		logger.info(String.format("Nombre détail d'appel supprimé %s", details.size()));
	}

	@Transactional
	public void saveHandle(AppelNumeriqueDto appelDto, String appelId) {
		List<DetailAppelNumerique> details = convertDtoToEntity(appelDto.getEleves(), appelId);
		removeExistingDetail(appelId);
		for(DetailAppelNumerique det : details) {
			save(det);
		}
	}
}
