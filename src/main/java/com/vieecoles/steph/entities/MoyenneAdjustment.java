package com.vieecoles.steph.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Moyenne_Adjustment")
@Getter
@Setter
public class MoyenneAdjustment extends PanacheEntityBase{
	@Id
	private String id;
	private Long annee;
	private String matricule;
	private Long periode;
	private Long matiere;
	private Long classe;
	private Double moyenne;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	private Long user;
	private String statut;
}
