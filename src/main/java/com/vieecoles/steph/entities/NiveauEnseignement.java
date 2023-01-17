package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Niveau_Enseignement")
@Data
@EqualsAndHashCode(callSuper = false)
public class NiveauEnseignement extends PanacheEntityBase{
	@Id
	private long id;
	@Transient
	private String [] ids;
	private String code;
	private String libelle;

}
