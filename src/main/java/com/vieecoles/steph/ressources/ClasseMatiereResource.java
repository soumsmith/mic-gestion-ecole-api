
/**
 * VERIFIER REQUETE BYBRANCHE AJOUT ANNEE
 */
package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.ClasseMatiere;
import com.vieecoles.steph.services.ClasseMatiereService;

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
	
	@POST
    @Path("/maj-coefficients")
    @Tag(name = "ClasseMatiere")
    public Response saveAndUpdateDisplay(List<ClasseMatiere> classeMatieres) {
		Gson g = new Gson();
		try {
		List<ClasseMatiere> cmList = new ArrayList<ClasseMatiere>();
		Long brancheId = (long) 0; 
		if(classeMatieres!= null && classeMatieres.size()>0) {
			classeMatiereService.handleSaveOrUpdate(classeMatieres);
			System.out.println(g.toJson(classeMatieres));
			brancheId = classeMatieres.get(0).getBranche().getId();
			System.out.println(brancheId);
			cmList = classeMatiereService.getByBranche(brancheId, 0, 0);
		}
        return Response.ok(cmList).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
    }
}
