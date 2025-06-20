package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.Jour;
import com.vieecoles.steph.services.JourService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
