package com.vieecoles.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private String note;
	@Column(name = "note_sur")
	private String noteSur;
	@ManyToOne
	@JoinColumn(name = "detail_bulletin_id")
	private DetailBulletin detailBulletin;
}
