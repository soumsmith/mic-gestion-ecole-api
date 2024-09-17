package com.vieecoles.steph.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
