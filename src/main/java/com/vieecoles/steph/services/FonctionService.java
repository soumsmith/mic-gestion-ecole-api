package com.vieecoles.steph.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import com.vieecoles.steph.entities.Fonction;
import com.vieecoles.steph.entities.TypePersonnel;

@RequestScoped
public class FonctionService {
	public List<Fonction> getList(){
		return Fonction.listAll();
	}
	
	// A modifier en filtrant les infos pour ne retenir que les éducateurs et les professeurs.
	
	public List<Fonction> getEducateurAndProf(){
		return Fonction.listAll();
	}
}
