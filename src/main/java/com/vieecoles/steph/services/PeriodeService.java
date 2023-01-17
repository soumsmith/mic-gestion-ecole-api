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

}
