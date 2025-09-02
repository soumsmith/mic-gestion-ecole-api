package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper=false)
public class Filiere extends PanacheEntityBase {
	@Id
	private Integer id;
	private String code;
	private String libelle;
}
