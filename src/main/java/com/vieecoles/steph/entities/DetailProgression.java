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
@Table(name = "ref_detail_progression")
@Getter
@Setter
public class DetailProgression extends PanacheEntityBase {
	@Id
	private String id;
	@Column(name = "numero_titre")
	private Integer numTitre;
	private String titre;
	@Column(name = "semaine_deb")
	private Integer semaineDeb;
	@Column(name = "niveau_titre")
	private Integer niveauTitre;
	@Column(name = "mois_deb")
	private Integer moisDeb;
	@Column(name = "periode_id")
	private Integer periodeId;
	@ManyToOne
	private Progression progression;
	@Column(name = "volume_horaire")
	private Integer heure;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
	private Integer ordre;
}
