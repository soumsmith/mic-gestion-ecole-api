package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.entities.Heures;
import com.vieecoles.services.HeuresService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/heures")
public class HeuresResource {

	@Inject
	HeuresService heuresService;

	@GET
    @Path("/list")
    @Tag(name = "Heures")
	@Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.ok().entity(heuresService.getList()).build();
    }


    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir l heure par son id", summary = "")
	@Tag(name = "Heures")
    public Heures get(@PathParam("id") long id) {
    	Heures hr = heuresService.findById(id);
    	if(hr == null) {
    		System.out.println("heure non trouvee avec id = "+id);
    		return new Heures();
    	} else {
    		return hr;
    	}
    }

}
