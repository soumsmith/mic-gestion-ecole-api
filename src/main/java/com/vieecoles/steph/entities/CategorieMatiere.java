package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorie_matiere")
@Data
@EqualsAndHashCode(callSuper = false)
public class CategorieMatiere extends PanacheEntityBase {

	@Id
	@Column(name = "categorie_matiereid")
	private long id;
	@Column(name = "categorie_matierecode")
	private String code;
	@Column(name = "categorie_matierelibelle")
	private String libelle;
}
