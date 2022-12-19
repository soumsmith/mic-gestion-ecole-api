package com.vieecoles.ressource.steph.services;

import com.vieecoles.entities.Personnel;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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

}
