package com.vieecoles.steph.ressources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

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
