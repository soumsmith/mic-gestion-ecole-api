package com.vieecoles.steph.ressources;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.Fonction;
import com.vieecoles.steph.services.FonctionService;

@Path("/fonctions")
public class FonctionResource {

	@Inject
	FonctionService fonctionService;


	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Fonction",description = "Liste des fonctions")
	public List<Fonction> getList(){
		return Fonction.listAll();
	}
}
