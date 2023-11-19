package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "periode")
public class Periode extends PanacheEntityBase {
	@Id
	@Column(name = "periodeid")
	private long id;
	@ManyToOne
	@JoinColumn(name = "periodicite_id")
	private Periodicite periodicite;
	@Column(name = "periodelibelle")
	private String libelle;
	private Integer niveau;
	private String coef;
	@Column(name = "final")
	private String isfinal;
}
