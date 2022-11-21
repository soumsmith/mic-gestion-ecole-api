package com.vieecoles.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.vieecoles.entities.Periode;

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
