
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
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
	public Response getByBranche(@QueryParam("branche") long brancheId, @QueryParam("annee") long anneeId,
			@QueryParam("ecole") long ecoleId) {
		return Response.ok().entity(classeMatiereService.getByBranche(brancheId, ecoleId)).build();
	}

	@GET
	@Path("/get-by-branche-via-classe")
	@Tag(name = "ClasseMatiere")
	public Response getByBrancheViaClasse(@QueryParam("classe") long classeId, @QueryParam("annee") long anneeId) {
		System.out.println(classeId+" - "+anneeId);
		return Response.ok().entity(classeMatiereService.getByBrancheViaClasse(classeId)).build();
	}
	
	@GET
	@Path("/get-all-by-branche-via-classe")
	@Tag(name = "ClasseMatiere")
	public Response getAllMatiereByBrancheViaClasse(@QueryParam("branche") long brancheId, @QueryParam("ecole") long ecoleId) {
		return Response.ok().entity(classeMatiereService.getAllMatieresByBrancheViaClasse(brancheId,ecoleId)).build();
	}
	
	@POST
	@Path("/maj-coefficients")
	@Tag(name = "ClasseMatiere")
	public Response saveAndUpdateDisplay(List<ClasseMatiere> classeMatieres) {
		Gson g = new Gson();
		try {
			List<ClasseMatiere> cmList = new ArrayList<ClasseMatiere>();
			Long brancheId = (long) 0;
			Long ecoleId = (long) 0;
			if (classeMatieres != null && classeMatieres.size() > 0) {
				classeMatiereService.handleSaveOrUpdate(classeMatieres);
				System.out.println(g.toJson(classeMatieres));
				brancheId = classeMatieres.get(0).getBranche().getId();
				ecoleId = classeMatieres.get(0).getEcole().getId();
//			System.out.println(brancheId);
				cmList = classeMatiereService.getByBranche(brancheId, ecoleId);
			}
			return Response.ok(cmList).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/generate-coefficients/{ecoleId}")
	@Tag(name = "ClasseMatiere")
	@Produces(MediaType.TEXT_PLAIN)
	public Response generateCoef(@PathParam("ecoleId") Long ecoleId) {

		try {
			classeMatiereService.generateDefaultCoeficientsByEcole(ecoleId);
			return Response.ok("Coeficient mis à jour").build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
