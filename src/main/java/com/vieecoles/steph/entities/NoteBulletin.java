package com.vieecoles.steph.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Notes_Bulletin")
@Data
@EqualsAndHashCode(callSuper = false)
public class NoteBulletin extends PanacheEntityBase {
	@Id
	private String id;
	private Double note;
	@Column(name = "note_sur")
	private String noteSur;
	@ManyToOne
	@JoinColumn(name = "detail_bulletin_id")
	private DetailBulletin detailBulletin;
}
