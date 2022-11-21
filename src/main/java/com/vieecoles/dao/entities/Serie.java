package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Serie extends PanacheEntityBase {

	@Id
	private long id;
	private String code;
	private String libelle;
}
