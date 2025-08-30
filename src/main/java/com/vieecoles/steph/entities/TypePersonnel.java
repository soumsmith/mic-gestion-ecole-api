package com.vieecoles.steph.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
