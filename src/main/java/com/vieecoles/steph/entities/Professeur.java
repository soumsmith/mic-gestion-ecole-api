package com.vieecoles.steph.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper=false)
public class Professeur extends Personnel{


	@Transient
	@Enumerated(EnumType.STRING)
	private Civilite civilite;
}
