package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Jour extends PanacheEntityBase {
	@Id
	private int id;
	private String code;
	@Column(name = "id_sys")
	private int idSys;
	private String libelle;
}
