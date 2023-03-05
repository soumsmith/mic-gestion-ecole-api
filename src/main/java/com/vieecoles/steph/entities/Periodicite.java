package com.vieecoles.steph.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Periodicite {
	@Id
	private int id;
	private String code;
	private String libelle;
}
