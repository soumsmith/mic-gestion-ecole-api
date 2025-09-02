package com.vieecoles.steph.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Data
@Table(name = "fonction")
@EqualsAndHashCode(callSuper=false)
public class Fonction extends PanacheEntityBase{
	@Id
	@Column(name = "fonctionid")
	private int id;
	@Column(name = "fonctioncode")
	private String code;
	@Column(name = "fonctionlibelle")
	private String libelle;
}
