package com.vieecoles.ressource.steph.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ecole")
public class Ecole {

	@Id
	@Column(name = "ecoleid")
	private long id;
	@Column(name = "ecolecode")
	private String Code;
	@Column(name= "ecoleclibelle")
	private String libelle;
}
