package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.NiveauEnseignement;
import com.vieecoles.steph.projections.GenericBasicProjectionLongId;
import com.vieecoles.steph.services.NiveauEnseignementService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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

	@Path("/list-projection")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Tag(name = "Niveau Enseignement")
	public List<GenericBasicProjectionLongId> getListProjection(){
		return niveauEnseignementService.getListProjection();
	}

}
