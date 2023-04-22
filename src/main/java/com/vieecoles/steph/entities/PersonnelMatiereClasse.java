package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Personnel_matiere_classe")
@Data
@EqualsAndHashCode(callSuper = false)
public class PersonnelMatiereClasse extends PanacheEntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Personnel_matiere_classeid")
	private long id;
	@Column(name = "Personnel_matiere_classe_date_creation")
	private Date dateCreation;
	@ManyToOne
	private Personnel personnel;
	@ManyToOne
	@JoinColumn(name = "classe_classeid")
	private Classe classe;
	@ManyToOne
	@JoinColumn(name = "matiere_matiereid")
	private EcoleHasMatiere matiere;
	@ManyToOne
	@JoinColumn(name = "annee_scolaire_annee_scolaireid")
	private AnneeScolaire annee;
}
