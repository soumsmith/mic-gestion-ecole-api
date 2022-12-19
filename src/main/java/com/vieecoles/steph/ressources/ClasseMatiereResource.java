package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.services.ClasseMatiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/classe-matiere")
public class ClasseMatiereResource {

	@Inject
	ClasseMatiereService classeMatiereService;

	@GET
    @Path("/list")
    @Tag(name = "ClasseMatiere")
    public Response list() {
        return Response.ok().entity(classeMatiereService.list()).build();
    }

	@GET
    @Path("/get-by-branche")
    @Tag(name = "ClasseMatiere")
    public Response getByBranche(@QueryParam("branche") long brancheId, @QueryParam("annee") long anneeId, @QueryParam("ecole") long ecoleId) {
        return Response.ok().entity(classeMatiereService.getByBranche(brancheId, anneeId, ecoleId)).build();
    }

	@GET
    @Path("/get-by-branche-via-classe")
    @Tag(name = "ClasseMatiere")
    public Response getByBrancheViaClasse(@QueryParam("classe") long classeId, @QueryParam("annee") long anneeId, @QueryParam("ecole") long ecoleId) {
        return Response.ok().entity(classeMatiereService.getByBrancheViaClasse(classeId, anneeId, ecoleId)).build();
    }
}
