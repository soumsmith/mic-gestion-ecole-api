package com.vieecoles.steph.projections;

import javax.persistence.ManyToOne;

import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Fonction;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RegisterForReflection
public class GenericPersonelProjectionLongIdFonctionEcole {
	
	private final Long id;
	@ManyToOne
	private final Fonction fonction;
	@ManyToOne
	private final Ecole ecole;

}
