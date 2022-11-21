package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
