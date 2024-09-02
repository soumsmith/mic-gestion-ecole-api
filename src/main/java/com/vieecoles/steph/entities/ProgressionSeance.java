package com.vieecoles.steph.entities;

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
@Table(name = "progression_seance")
@Getter
@Setter
public class ProgressionSeance extends PanacheEntityBase{
	@Id
	private String id;
	@Column(name = "seance_id")
	private String seanceId;
	private Integer duree;
	@ManyToOne
	@JoinColumn(name = "detail_progression_id")
	private DetailProgression detailProgression;
	@Column(name = "position_detail_progression")
	private Integer position;
	private String observations;
}
