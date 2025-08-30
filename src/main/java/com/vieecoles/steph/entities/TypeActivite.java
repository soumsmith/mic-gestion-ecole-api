package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Type_Activite")
@Data
@EqualsAndHashCode(callSuper = false)
public class TypeActivite extends PanacheEntityBase {

	@Id
	private long id;
	private String code;
	private String libelle;
	@Column(name = "niveau_enseignement")
	private Long niveauEnseignement;
	@Column(name = "type_seance")
	private String typeSeance;
	@Column(name = "date_creation")
	private Date dateCreation;
	@Column(name = "date_update")
	private Date dateUpdate;
}
