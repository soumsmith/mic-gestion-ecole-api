package com.vieecoles.steph.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
