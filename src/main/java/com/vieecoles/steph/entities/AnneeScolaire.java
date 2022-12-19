package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "annee_scolaire")
public class AnneeScolaire extends PanacheEntityBase{

	@Id
	@Column(name = "annee_scolaireid")
	private long id;
	@Column(name = "annee_scolaire_code")
	private String code;
	@Column(name = "annee_scolaire_libelle")
	private String libelle;
}
