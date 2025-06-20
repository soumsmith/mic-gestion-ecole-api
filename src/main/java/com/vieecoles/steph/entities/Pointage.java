package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Pointage extends PanacheEntityBase{

	@Id
	private long id;
	private String code;
	private String type;
	@Transient
	private Eleve eleve;
	private String observation;
	@Column(name = "date")
	private String datePointage;
	@Transient
	private Badgeuse badgeuse;
}
