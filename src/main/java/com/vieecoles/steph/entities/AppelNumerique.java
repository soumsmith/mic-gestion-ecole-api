package com.vieecoles.steph.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "appel_numerique")
public class AppelNumerique extends PanacheEntityBase{
	
	@Id
	@GeneratedValue(generator = "uuid-hibernate-generator")
	@GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	private String code;
	private Date date;
	@Column(name = "heure_debut")
	private String heureDebut;
	@Column(name = "heure_fin")
	private String heureFin;
	private Seances seance;
	private Personnel personnel;
	private Ecole ecole;
	@Column(name = "progression_id")
	private String progression;
	private String commentaire;

}
