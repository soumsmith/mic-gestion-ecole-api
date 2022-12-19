package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "type_personnel")
@Data
@EqualsAndHashCode(callSuper = false)
public class TypePersonnel extends PanacheEntityBase{

	@Id
	@Column(name = "type_personnelid")
	private int id;
	@Column(name = "type_personnelcode")
	private int code;
	@Column(name = "type_personnellibelle")
	private int libelle;
}
