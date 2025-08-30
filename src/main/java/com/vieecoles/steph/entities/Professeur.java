package com.vieecoles.steph.entities;

import lombok.Data;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;

@Data
public class Professeur extends Personnel{


	@Transient
	@Enumerated(EnumType.STRING)
	private Civilite civilite;
}
