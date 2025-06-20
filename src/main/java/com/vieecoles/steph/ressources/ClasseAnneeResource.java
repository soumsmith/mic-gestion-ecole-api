package com.vieecoles.steph.ressources;

import com.vieecoles.steph.services.ClasseAnneeService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/classe-annee")
public class ClasseAnneeResource {

	@Inject
	ClasseAnneeService classeAnneeService;

	@GET
    @Path("/list")
    @Tag(name = "Classe-Annee")
    public Response list() {
        return Response.ok().entity(classeAnneeService.getList()).build();
    }


    	}
