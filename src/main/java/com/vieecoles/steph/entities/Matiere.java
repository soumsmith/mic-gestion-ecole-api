package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "matiere")
@Data
@EqualsAndHashCode(callSuper = false)
public class Matiere extends PanacheEntityBase {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "matiereid")
    private Long  id ;
	@Column(name = "matierecode")
    private  String code;
	@Column(name = "matierelibelle")
    private  String libelle;

	private Integer pec;
	private Integer bonus;
	@ManyToOne
	@JoinColumn(name = "niveau_enseign_id")
	private NiveauEnseignement niveauEnseignement;
    @Transient
    private Double moyenne;
    @Transient
    private String rang;
    @Transient
    private String  coef ;
    @Transient
    private String appreciation;
    @Transient
    private String eleveMatiereIsClassed;
//    @ManyToOne
//    @JoinColumn(name = "matiereparent_id")
    @Column(name = "matiereparent_id")
    private String matiereParent;
    // Ajouté par Soum pour sa gestion des bulletins
    @Column(name = "parent_matiere")
    private String parentMatiereLibelle;
    @Column(name = "num_ordre_affichage")
    private Integer numOrdre;

    @ManyToOne
    @JoinColumn(name = "categorie_matiere_categorie_matiereid")
    private CategorieMatiere categorie;

    private LocalDateTime dateCreation;
    private LocalDateTime dateUpdate;

    @Transient
    private String user;

}
