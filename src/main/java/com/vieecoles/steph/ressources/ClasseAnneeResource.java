package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.ClasseAnneeService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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
