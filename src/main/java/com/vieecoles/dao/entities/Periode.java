package com.vieecoles.dao.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "periode")
public class Periode extends PanacheEntityBase {
	@Id
	@Column(name = "periodeid")
	private long id;
	@Column(name = "periodecode")
	private String code;
	@Column(name = "periodelibelle")
	private String libelle;

}
