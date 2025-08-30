package com.vieecoles.steph.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "detail_progression_seance")
public class DetailProgressionSeance extends PanacheEntityBase {
	@Id
	private String id;
	@ManyToOne
	@JoinColumn(name = "detail_progression_id")
	private DetailProgression detailProgression;
	@ManyToOne
	@JoinColumn(name = "progression_seance_id")
	private ProgressionSeance progressionSeance;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateCessation;
}
