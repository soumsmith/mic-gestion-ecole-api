package com.vieecoles.steph.entities;

public enum Civilite {

	M("Monsieur"),
	MME("Madame"),
	MLLE("Mademoiselle");

	public String value;

	Civilite(String value){
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

}
