package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
