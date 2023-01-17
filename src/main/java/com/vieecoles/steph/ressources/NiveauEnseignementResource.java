package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.NiveauEnseignement;
import com.vieecoles.steph.services.NiveauEnseignementService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/niveau-enseignement")
public class NiveauEnseignementResource {

	@Inject
	NiveauEnseignementService niveauEnseignementService;

	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Tag(name = "Niveau Enseignement")
	public List<NiveauEnseignement> getList(){
		return niveauEnseignementService.getList();
	}

}
