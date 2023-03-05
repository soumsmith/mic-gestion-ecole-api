package com.vieecoles.steph.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "Detail_bulletins")
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailBulletin extends PanacheEntityBase{

	@Id
	private String id;
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
	private String  categorie;
    private int  num_ordre;
	private String nom_prenom_professeur ;
//	@Column(name="is_classed")
//	private String isClassed;
	
	@ManyToOne
	private Bulletin bulletin;
}
