package com.vieecoles.entities;

import com.vieecoles.steph.entities.Bulletin;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Detail_bulletins_import")
@Data
@EqualsAndHashCode(callSuper = false)
public class DetailBulletinImport extends PanacheEntityBase{

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
    private Integer  num_ordre;
	private String nom_prenom_professeur ;
	@Column(name = "moy_An")
	private Double moyAn;
	@Column(name = "rang_An")
	private String rangAn;
	private Integer pec;
	private Integer bonus;
	@Column(name = "parent_matiere")
	private String parentMatiere ;
	@Column(name = "is_classed")
	private String isRanked;
	@ManyToOne
	private BulletinImport bulletin;
	@Column(name = "date_creation")
	private Date dateCreation;
}
