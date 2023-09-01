package com.vieecoles.steph.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Periodicite extends PanacheEntityBase{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String code;
	private String libelle;
	private String ordre;
	@Column(name = "is_Default")
	private String isDefault;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	private String user;
}
