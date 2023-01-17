package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "fonction")
public class Fonction extends PanacheEntityBase{
	@Id
	@Column(name = "fonctionid")
	private int id;
	@Column(name = "fonctioncode")
	private String code;
	@Column(name = "fonctionlibelle")
	private String libelle;
}
