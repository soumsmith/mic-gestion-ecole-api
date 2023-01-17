package com.vieecoles.steph.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;

import com.vieecoles.steph.entities.Personnel;

@ApplicationScoped
public class PersonnelService {
	
	Logger logger = Logger.getLogger(Personnel.class.getName());

	public List<Personnel> getList() {
		try {
			return Personnel.listAll();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Personnel>();
		}
	}

	
	
	public List<Personnel> getListByFonction(int fonctionId) {
		return Personnel.find("fonction.id = ?1", fonctionId)
				.list();
	}	
	public List<Personnel> getListByFonctionAndClasse(int fonctionId, long classeId) {
		return Personnel.find("fonction.id = ?1 and classe.id=?2", fonctionId, classeId)
				.list();
	}

}
