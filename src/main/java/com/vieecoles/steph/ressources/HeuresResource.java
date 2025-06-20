package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.Heures;
import com.vieecoles.steph.services.HeuresService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
