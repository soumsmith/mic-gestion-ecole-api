package com.vieecoles.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class AbsencePersonnel {

	@Id
	private long id;
	private String code;
	private Integer status;
	private String comments;
	private AppelNumerique appelNumerique;
	private personnel personnel;
}
