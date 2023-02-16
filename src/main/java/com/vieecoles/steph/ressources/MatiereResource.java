package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.services.MatiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/matiere")
@Tag(name = "Matiere")
public class MatiereResource {

	@Inject
	MatiereService matiereService;

	//Logger logger = Logger.getLogger(MatiereResource.class.getName());

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		return Response.ok().entity(matiereService.getList()).build();
	}

	@GET
	@Path("/get-by-id")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@QueryParam("id") long id) {
		return Response.ok().entity(matiereService.findById(id)).build();
	}
	
	// Quand le niveau d enseignement sera pris en compte dans les variables  d env alors remplacer le param ecole par niveau enseign
	@GET
	@Path("/get-by-niveau-enseignement")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByNiveau(@QueryParam("id") Long ecoleId) {
		Ecole ecole = Ecole.findById(ecoleId);
		
		return Response.ok().entity(matiereService.getByNiveauEnseignement(ecole.getNiveauEnseignement().getId())).build();
	}

	@POST
	@Path("/update-display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateAndDisplay(Matiere matiere) {

		Matiere ev = matiereService.updateAndDisplay(matiere);
		if (ev == null) {
			throw new NotFoundException();
		}
		return Response.ok().entity(ev).build();
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)

	public Response create(Matiere matiere) {
		try {
		//	logger.info("Saving ...");
			matiereService.create(matiere);
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

			matiereService.delete(Long.parseLong(id));
			return Response.ok("Suppression Matiere id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}

	}

	   @POST
	    @Path("/saveAndDisplay")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)

	    public Response createAndDisplay(Matiere matiere) {
	    	try {
	    	//	logger.info("Saving and display ...");
	    		matiereService.create(matiere);
	    	} catch(Exception e) {
	    		 e.printStackTrace();
	    		 return Response.serverError().entity(new Evaluation()).build();
	    	 }
	    	return Response.ok().entity(matiereService.findById(matiere.getId())).build();
	    }
}
