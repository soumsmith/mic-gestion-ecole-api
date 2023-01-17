package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "type_personnel")
@Data
@EqualsAndHashCode(callSuper = false)
public class TypePersonnel extends PanacheEntityBase{

	@Id
	@Column(name = "type_personnelid")
	private int id;
	@Column(name = "type_personnelcode")
	private String code;
	@Column(name = "type_personnellibelle")
	private String libelle;
}
