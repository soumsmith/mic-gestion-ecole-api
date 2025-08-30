package com.vieecoles.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
//@Entity
//@Table(name = "Classe_Annee")
public class ClasseAnnee extends PanacheEntityBase{

//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private long id;
//	@ManyToOne
//	private AnneeScolaire annee;
//	@ManyToOne
//	private Classe classe;
}
