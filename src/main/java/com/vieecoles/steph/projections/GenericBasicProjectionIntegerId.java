package com.vieecoles.steph.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RegisterForReflection
public class GenericBasicProjectionIntegerId {
	
	private final Long id;
	private final String libelle;

}
