package com.vieecoles.entities;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Data;

@Data
@Entity
public class UtilisateurParent extends PanacheEntityBase{

	private long id;
	private String code;
	private String login;
	private String password;
	private Parent parent;
}
