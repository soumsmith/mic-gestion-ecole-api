package com.vieecoles.steph.projections;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterForReflection
public class NotesBulletinIdProjection {
	
	private final String id;
	
	public NotesBulletinIdProjection(String id) {
		this.id = id;
	}

}
