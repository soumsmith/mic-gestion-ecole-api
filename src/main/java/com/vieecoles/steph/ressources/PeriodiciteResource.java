package com.vieecoles.steph.ressources;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.Periodicite;
import com.vieecoles.steph.services.PeriodiciteService;

@Path("/periodicite")
@Tag(name = "Periodicite")
public class PeriodiciteResource {
	
	@Inject
	PeriodiciteService periodiciteService;
	
	@Path("/list")
	@GET
	public List<Periodicite> listAll() {
		return periodiciteService.listAll();
	}

}
