package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Filiere extends PanacheEntityBase {
	@Id
	private Integer id;
	private String code;
	private String libelle;
}
