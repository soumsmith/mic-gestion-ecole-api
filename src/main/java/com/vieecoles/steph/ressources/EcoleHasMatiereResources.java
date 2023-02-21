package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.EcoleMatiereDto;
import com.vieecoles.steph.dto.MatiereDto;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.services.EcoleHasMatiereService;

@Path("/matiere-ecole")
@Tag(name = "Matiere Ecole")
public class EcoleHasMatiereResources  {
	@Inject
	EcoleHasMatiereService matiereEcoleService;

	//Logger logger = Logger.getLogger(MatiereResource.class.getName());

	/*
	 * A MODIFIER matiereDto
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		List<EcoleHasMatiere> matieres = matiereEcoleService.getList();
		List<EcoleMatiereDto> ecoleMatieresDto = new ArrayList<EcoleMatiereDto>();
		for(EcoleHasMatiere m: matieres) {
			ecoleMatieresDto.add(matiereEcoleService.buildEntityToDto(m));
		}
		
		return Response.ok().entity(ecoleMatieresDto).build();
	}
	
	@GET
	@Path("/list-by-ecole/{ecole}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listByEcole(@PathParam("ecole") Long ecoleId) {
		List<EcoleHasMatiere> matieres = matiereEcoleService.getListByEcole(ecoleId);
		List<EcoleMatiereDto> ecoleMatieresDto = new ArrayList<EcoleMatiereDto>();
		for(EcoleHasMatiere m: matieres) {
			ecoleMatieresDto.add(matiereEcoleService.buildEntityToDto(m));
		}
		
		return Response.ok().entity(ecoleMatieresDto).build();
	}

	@GET
	@Path("/get-by-id")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@QueryParam("id") long id) {
		return Response.ok().entity(matiereEcoleService.findById(id)).build();
	}
	
	// Quand le niveau d enseignement sera pris en compte dans les variables  d env alors remplacer le param ecole par niveau enseign
	@GET
	@Path("/get-by-niveau-enseignement")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByNiveau(@QueryParam("id") Long ecoleId) {
		Ecole ecole = Ecole.findById(ecoleId);
		
		return Response.ok().entity(matiereEcoleService.getByNiveauEnseignement(ecole.getNiveauEnseignement().getId())).build();
	}

	@POST
	@Path("/update-display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateAndDisplay(EcoleHasMatiere matiereEcole) {

		EcoleHasMatiere ev = matiereEcoleService.updateAndDisplay(matiereEcole);
		if (ev == null) {
			throw new NotFoundException();
		}
		return Response.ok().entity(matiereEcoleService.buildEntityToDto(ev)).build();
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public Response create(EcoleHasMatiere matiereEcole) {
		try {
		//	logger.info("Saving ...");
			matiereEcoleService.create(matiereEcole);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erreur de creation").build();
		}
		return Response.ok("Enregistrement reussi").build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.TEXT_PLAIN)

	public Response delete(@PathParam("id") String id) {
		try {

			matiereEcoleService.delete(Long.parseLong(id));
			return Response.ok("Suppression EcoleHasMatiere id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}

	}

	   @POST
	    @Path("/saveAndDisplay")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)

	    public Response createAndDisplay(EcoleHasMatiere matiere) {
	    	try {
	    	//	logger.info("Saving and display ...");
	    		matiereEcoleService.create(matiere);
	    		EcoleHasMatiere entity = matiereEcoleService.findById(matiere.getId());
	    		return Response.ok().entity(matiereEcoleService.buildEntityToDto(entity)).build();
	    		
	    	} catch(Exception e) {
	    		 e.printStackTrace();
	    		 return Response.serverError().entity(new Evaluation()).build();
	    	 }
	    }
}
