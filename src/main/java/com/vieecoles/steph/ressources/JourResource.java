package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.entities.Jour;
import com.vieecoles.services.JourService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jour")
public class JourResource {

	@Inject
	JourService jourService;

	@GET
    @Path("/list")
    @Tag(name = "Jour")
	@Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok().entity(jourService.getList()).build();
    }


    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir le jour par son id", summary = "")
	@Tag(name = "Activite")
    public Jour get(@PathParam("id") long id) {
    	Jour jr = jourService.findById(id);
    	if(jr == null) {
    		System.out.println("activite non trouvee avec id = "+id);
    		return new Jour();
    	} else {
    		return jr;
    	}
    }
}
