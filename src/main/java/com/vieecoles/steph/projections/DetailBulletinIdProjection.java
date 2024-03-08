package com.vieecoles.steph.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class DetailBulletinIdProjection {
	
	private final String id;
	
	public DetailBulletinIdProjection(String id) {
		this.id = id;
	}

}
