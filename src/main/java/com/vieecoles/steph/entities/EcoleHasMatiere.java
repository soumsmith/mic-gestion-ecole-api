package com.vieecoles.steph.entities;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ecole_has_matiere")
@Data
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class EcoleHasMatiere extends PanacheEntityBase{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@EqualsAndHashCode.Include
    private Long  id ;
	@ManyToOne
	@EqualsAndHashCode.Include
	@JoinColumn(name = "ecole_ecoleid")
    private  Ecole ecole;
	@ManyToOne
	@JoinColumn(name = "matiere_matiereid")
	@EqualsAndHashCode.Include
    private  Matiere matiere;
	@EqualsAndHashCode.Include
	@Column(name = "alias_matiere_code")
    private  String code;
	@EqualsAndHashCode.Include
	@Column(name = "alias_matiere_libelle")
    private  String libelle;

	private Integer pec;
	private Integer bonus;
	@Column(name = "num_ordre_affichage")
	private Integer numOrdre;
    @Transient
    private Double moyenne;
    @Transient
    private String rang;
    @Transient
    private String  coef ;
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
    private EcoleHasMatiere matiereParent;
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
}
