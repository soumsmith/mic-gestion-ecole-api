package com.vieecoles.ressource.steph;

import com.vieecoles.dao.entities.Activite;

import com.vieecoles.services.stephServi.ActiviteService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/activite")
@Produces(MediaType.APPLICATION_JSON)
public class ActiviteRessource {

	@Inject
    ActiviteService activiteService;

	@GET
    @Path("/list")
    @Tag(name = "Activite")
    public Response list() {
        return Response.ok().entity(activiteService.getList()).build();
    }


    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir la classe par son id", summary = "")
	@Tag(name = "Activite")
    public Activite get(@PathParam("id") long id) {
    	Activite atv = activiteService.findById(id);
    	if(atv == null) {
    		System.out.println("activite non trouvee avec id = "+id);
    		return new Activite();
    	} else {
    		return atv;
    	}
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Activite")
    public Response create(Activite activite) {
    	try {
    		System.out.println("Saving ...");
    		activiteService.save(activite);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity("Erreur de creation").build();
    	 }
    	return Response.ok("Enregistrement reussi").build();
    }

    @POST
    @Path("/saveAndDisplay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Activite")
    public Response createAndDisplay(Activite activite) {
    	try {
    		System.out.println("Saving and display ...");
    		activiteService.save(activite);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(new Activite()).build();
    	 }
    	return Response.ok().entity(activiteService.findById(activite.getId())).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Activite")
    public Activite update(Activite activite) {

    	Activite atv = activiteService.update(activite);
		if(atv==null) {
			throw new NotFoundException();
		}
    	return atv;
    }

    @POST
    @Path("/update-display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Activite")
    public Response updateAndDisplay(Activite activite) {

    	Activite atv = activiteService.updateAndDisplay(activite);
		if(atv==null) {
			throw new NotFoundException();
		}
    	return Response.ok().entity(atv).build();
    }


    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Activite")
    public Response delete(@PathParam("id") String id) {
    	try {
    		activiteService.delete(id);
			return Response.ok("Suppression activite id = "+id).build();
		}catch(RuntimeException re){
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}


    }
}
