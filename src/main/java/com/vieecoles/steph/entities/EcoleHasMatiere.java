package com.vieecoles.steph.entities;

import java.time.LocalDateTime;

import com.vieecoles.steph.dto.MatiereDto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ecole_has_matiere")
@Data
@ToString(of = { "id" })
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class EcoleHasMatiere extends PanacheEntityBase implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@EqualsAndHashCode.Include
	private Long id;
	@ManyToOne
	@EqualsAndHashCode.Include
	@JoinColumn(name = "ecole_ecoleid")
	private Ecole ecole;
	@ManyToOne
	@JoinColumn(name = "matiere_matiereid")
	@EqualsAndHashCode.Include
	private Matiere matiere;
	@EqualsAndHashCode.Include
	@Column(name = "alias_matiere_code")
	private String code;
	@EqualsAndHashCode.Include
	@Column(name = "alias_matiere_libelle")
	private String libelle;

	private Integer pec;
	private Integer bonus;
	@Column(name = "num_ordre_affichage")
	private Integer numOrdre;
	@Transient
	private Double moyenne;
	@Transient
	private String rang;
	@Transient
	private String coef;
	@Transient
	private String appreciation;
	// Reservé pour indiquer si la moyenne d un élève a subit un ajustement
	@Transient
	private String isAdjustment;

	@Transient
	private Double moyenneAnnuelle;
	@Transient
	private String rangAnnuel;

	@Transient
	private Double testLourdNote;
	@Transient
	private Integer testLourdNoteSur;

	// Moyenne intermediaire sans l'ajout des tests lourds
	@Transient
	private Double moyenneIntermediaire;

	@ManyToOne
	@JoinColumn(name = "matiere_parent_id")
	@JsonbTransient
	private EcoleHasMatiere matiereParent;
	
	@Transient
	private Long matiereParentId;
	
	@ManyToOne
	@JoinColumn(name = "categorie")
	@EqualsAndHashCode.Include
	private CategorieMatiere categorie;

	@ManyToOne
	@JoinColumn(name = "niveau_enseign_id")
	@EqualsAndHashCode.Include
	private NiveauEnseignement niveauEnseignement;

	@Transient
	private String eleveMatiereIsClassed;

	@Column(name = "parent_matiere")
	private String parentMatiereLibelle;

	@Column(name = "is_emr")
	private String isEMR;

	private LocalDateTime dateCreation;
	private LocalDateTime dateUpdate;

	@Transient
	private String user;

	@Transient
	private MatiereDto parent;

	// Permet de charger le parent s'il en existe et d'eviter les erreur de données récursives avec JSON ou JACKSON
	public void loadParent() {
		MatiereDto parent = null;
		if (getMatiereParentId() != null) {
			EcoleHasMatiere matiereEcole = EcoleHasMatiere.findById(this.matiereParentId);
			this.matiereParent = matiereEcole;
			parent = new MatiereDto(this.matiereParent.getId(), this.matiereParent.getCode(), this.matiereParent.getLibelle());
		}
		this.parent = parent;
	}

}
