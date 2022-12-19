package com.vieecoles.ressource.steph.entities;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

@Data
public class Professeur extends Personnel{


	@Transient
	@Enumerated(EnumType.STRING)
	private Civilite civilite;
}
