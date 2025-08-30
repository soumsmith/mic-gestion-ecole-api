package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.GroupeEvaluations;
import com.vieecoles.steph.services.AnneePeriodeService;
import com.vieecoles.steph.services.AnneeService;
import com.vieecoles.steph.services.EcoleService;
import com.vieecoles.steph.services.GroupeEvaluationService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/groupe-evaluations")
public class GroupeEvaluationsResource {
	@Inject
	GroupeEvaluationService groupeEvaluationService;

	@Inject
	AnneePeriodeService anneePeriodeService;

	@Inject
	EcoleService ecoleService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des groupes", summary = "")
	@Tag(name = "Groupe Evaluations")
	public Response list() {
		return Response.ok().entity(groupeEvaluationService.getList()).build();
	}


	@GET
	@Path("/get-by-id/{id}")
	@Operation(description = "Obtenir un groupe d evaluations via son id", summary = "")
	@Tag(name = "Groupe Evaluations")
	public Response getById(@PathParam("id") String id) {
		return Response.ok().entity(groupeEvaluationService.getById(id)).build();
	}

	@GET
	@Path("/get-by-ecole-annee")
	@Operation(description = "Obtenir un groupe d evaluations via son id", summary = "")
	@Tag(name = "Groupe Evaluations")
	public Response getByEcoleAndAnnee(@QueryParam("ecole") Long ecole, @QueryParam("annee") Long annee) {
		System.out.println("GroupeEvaluationsResource.getByEcoleAndAnnee()");
		return Response.ok().entity(groupeEvaluationService.getByEcoleAndAnnee(ecole, annee)).build();
	}

	@GET
	@Path("/delete-handle/{id}")
	@Operation(description = "supprimer un groupe d evaluations via son id", summary = "")
	@Tag(name = "Groupe Evaluations")
	public Response delete(@PathParam("id") String id) {
		try {
			groupeEvaluationService.deleteHandle(id);
			return Response.ok().build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().entity(r).build();
		}
	}

	@POST
	@Path("/save-handle")
	@Operation(description = "persister un groupe d evaluations via son id", summary = "")
	@Tag(name = "Groupe Evaluations")
	public Response saveHandle(GroupeEvaluations groupe) {
		try {
			System.out.println("--> creation modification de groupe");
			GroupeEvaluations grp = groupeEvaluationService.saveHandle(groupe);
			return Response.ok(grp).build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().entity(r).build();
		}
	}

}
