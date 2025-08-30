package com.vieecoles.steph.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

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
	@ManyToOne
	@JoinColumn(name = "seance_id")
	private Seances seance;
	private Integer duree;
	@Column(name = "duree_totale")
	private Integer dureeTotale;
//	@ManyToOne
//	@JoinColumn(name = "detail_progression_id")
//	private DetailProgression detailProgression;
	private Integer position;
	private String observations;
	@Column(name = "attachment_url")
	private String attachmentUrl;

	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateCessation;

	@Transient
	List<String> details;
}
