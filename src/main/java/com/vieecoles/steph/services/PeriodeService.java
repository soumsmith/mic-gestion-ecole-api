package com.vieecoles.steph.services;

import com.vieecoles.steph.entities.Periode;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PeriodeService {
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

}
