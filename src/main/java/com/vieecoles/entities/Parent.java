package com.vieecoles.entities;

import lombok.Data;

@Data
public class Parent {

	private long id;
	private String code;
	private String nom;
	private String prenom;
	private String phone;
	private String phone2;
	private TypeParent typeParent;

}
