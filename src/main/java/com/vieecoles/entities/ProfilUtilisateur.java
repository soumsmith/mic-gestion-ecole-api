package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ProfilUtilisateur {

	@Id
	private long id;
	private Utilisateur utilisateur;
	private Profil profil;
}
