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

@Getter
@Setter
@Entity
@Table(name = "appel_numerique")
public class AppelNumerique extends PanacheEntityBase{
	
	@Id
	private String id;
	private String code;
	private Date date;
	@Column(name = "heure_debut")
	private String heureDebut;
	@Column(name = "heure_fin")
	private String heureFin;
	@ManyToOne
	@JoinColumn(name = "seance_id")
	private Seances seance;
	@JoinColumn(name = "personnel_id")
	@ManyToOne
	private Personnel personnel;
	@JoinColumn(name = "ecole_id")
	@ManyToOne
	private Ecole ecole;
//	@Column(name = "progression_id")
//	private String progression;
	private String commentaire;
	private Integer position;
	@Column(name = "duree_seance")
	private Integer duree;
	@Column(name = "duree_totale")
	private Integer dureeTotale;

}
