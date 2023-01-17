package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Heures extends PanacheEntityBase{
	@Id
	private int id;
	private String rang;
	private String libelle;
	private String heure_deb;
	private String heure_fin;

}
