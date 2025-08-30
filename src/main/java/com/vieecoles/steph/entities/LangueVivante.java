package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Data
@Entity
@Table(name = "Langue_Vivante")
public class LangueVivante extends PanacheEntityBase {

	@Id
	private long id;
	private String code;
	private String libelle;
	@Column(name = "typeId")
	private String type;
}
