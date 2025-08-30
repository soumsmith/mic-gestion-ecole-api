package com.vieecoles.steph.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "programme_ecole")
@Entity
public class ProgrammeEcole extends PanacheEntityBase {
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "fk_programme_id")
	private Programme programme;
	@ManyToOne
	@JoinColumn(name = "fk_ecole_id")
	private Ecole ecole;
	private String statut;

}
