package com.vieecoles.steph.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class BulletinIdProjection {
	
	private final String id;
	
	public BulletinIdProjection(String id) {
		this.id = id;
	}

}
