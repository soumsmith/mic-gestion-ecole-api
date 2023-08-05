package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "personnel")
@Data
public class Personnel extends PanacheEntityBase {

	@Id
	@Column(name = "personnelid")
	private long id;
	@Column(name = "personnelcode")
	private String code;
	@Column(name = "personnelnom")
	private String nom;
	@Column(name = "personnelprenom")
	private String prenom;
	@ManyToOne
	@JoinColumn(name="fonction_fonctionid")
	private Fonction fonction;
	@ManyToOne
	@JoinColumn(name="ecole_ecoleid")
	private Ecole ecole;
	@Column(name="sous_attent_personn_sous_attent_personnid")
	private Long souscriptionAttenteId;
	@Column(name = "personnel_contact")
	private String contact;
}
