package com.vieecoles.steph.services;

import com.vieecoles.steph.dto.AnneeInfoDto;
import com.vieecoles.steph.entities.Periode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PeriodeService {
	
	@Inject
	AnneeService anneeService;
	
	public List<Periode> getList() {
		try {
			return Periode.listAll();
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Periode>() ;
		}
	}
	
	public List<Periode> getListByPeriodicite(Integer periodiciteId) {
		try {
			return Periode.list("periodicite.id=?1 order by niveau", periodiciteId);
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<Periode>() ;
		}
	}
	/**
	 * Cette Méthode renvoie la liste des periodes passées et en cours.
	 *
	 * @param periodiciteId
	 * @param ecoleId
	 * @return la liste des périodes
	 */
	public List<Periode> getListFilterByDateByPeriodicite(Integer periodiciteId, Long ecoleId) {
		List<Periode> periodes = Periode.list("periodicite.id=?1 order by niveau", periodiciteId);
		List<Periode> periodesFiltered =  new ArrayList<>();
		AnneeInfoDto anneeInfoDto = anneeService.getAnneeInfo(ecoleId);
		if(anneeInfoDto.getPeriodeId() != null) {
			Periode periode = new Periode();
			periode.setNiveau(0);
			Integer niveau = periodes.stream().filter(p -> p.getId()==anneeInfoDto.getPeriodeId()).findFirst().orElse(periode).getNiveau();
			periodesFiltered = periodes.stream().filter(p -> p.getNiveau()<=niveau).collect(Collectors.toList());
		}
		
		return periodesFiltered;
	}

}
