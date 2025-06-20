package com.vieecoles.steph.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Detail_bulletins")
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailBulletin extends PanacheEntityBase {

	@Id
	private String id;
	@Column(name = "id_matiere")
	private Long matiereId;
	@Column(name = "id_matiere_real")
	private Long matiereRealId;
	@Column(name = "code_matiere")
	private String matiereCode;
	@Column(name = "libelle_matiere")
	private String matiereLibelle;
	@Column(name = "categorie")
	private String categorieMatiere;
	private Double moyenne;
	private Integer rang;
	private Double coef;
	@Column(name = "moy_coef")
	private Double moyCoef;
	private String appreciation;
	@Column(name = "code_categorie")
	private String categorie;
	private Integer num_ordre;
	private String nom_prenom_professeur;
	@Column(name = "is_adjustment")
	private String isAdjustment;

	@Column(name = "moy_An")
	private Double moyAn;
	@Column(name = "rang_An")
	private String rangAn;
	@Column(name = "appr_an")
	private String appreciationAn;
	private Integer pec;
	private Integer bonus;
	@Column(name = "parent_matiere")
	private String parentMatiere;

	@Column(name = "test_lourd_note")
	private Double testLourdNote;
	@Column(name = "test_lourd_note_sur")
	private Integer testLourdNoteSur;

	@Column(name = "moy_intermediate")
	private Double moyenneIntermediaire;

	@Column(name = "is_classed")
	private String isRanked;

	@ManyToOne
	private Bulletin bulletin;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "sexe_professeur")
	private String sexeProfesseur;
}
