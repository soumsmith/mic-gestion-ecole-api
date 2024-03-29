package com.vieecoles.steph.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "detail_appel")
@Getter
@Setter
public class DetailAppelNumerique extends PanacheEntityBase{

	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "eleve_classe_id")
	private ClasseEleve classeEleve;
	@Column(name = "is_present")
	private String presence;
	@ManyToOne
	@JoinColumn(name = "appel_numerique_id")
	private AppelNumerique appelNumerique;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	private String user;
}
