package com.vieecoles.entities;

import javax.persistence.Id;

public class AbsenceEleve {

	@Id
	private long id;
	private String code;
	private Integer status;
	private String comments;
	private AppelNumerique appelNumerique;
	private Eleve eleve;
}
