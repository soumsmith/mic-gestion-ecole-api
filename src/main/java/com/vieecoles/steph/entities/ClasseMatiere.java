package com.vieecoles.ressource.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Renommer en BrancheMatiere et y ajouter l 'école au cas où la branche est créee de façon locale (niveau ecole)
 * @author stephane
 *
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "Classe_Matiere")
public class ClasseMatiere extends PanacheEntityBase{

	@Id
	private long id;
	private String coef;
	@ManyToOne
	@JoinColumn(name = "matiereid")
	private Matiere matiere;
	@Transient
//	@ManyToOne
//	@JoinColumn(name = "Classe_id")
	private Classe classe;
	// A supprimer après verification des incidences
	@ManyToOne
	private Branche branche;

}
