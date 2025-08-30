package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.EvaluationPeriode;
import com.vieecoles.steph.services.EvaluationPeriodeService;

@Path("/evaluation-periode")
public class EvaluationPeriodeResource {
	@Inject
	EvaluationPeriodeService evaluationPeriodeService;

	@GET
	@Path("/get-by-annee-ecole/{annee}/{ecole}")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation Periode")
	public Response getListByAnneeAndEcole(@PathParam("annee") Long annee, @PathParam("ecole") Long ecole){
		List<EvaluationPeriode> list = new ArrayList<>();
		try {
			list = evaluationPeriodeService.findByAnneeAndEcole(annee, ecole);
			return Response.ok(list).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(list).build();
		}
	}
	@GET
	@Path("/get-by-annee-ecole-periode-niveau/{annee}/{ecole}/{periode}/{niveau}")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation Periode")
	public Response getListByAnneeAndEcole(@PathParam("annee") Long annee, @PathParam("ecole") Long ecole, @PathParam("periode") Long periode, @PathParam("niveau") Long niveau ){
		EvaluationPeriode obj = null;
		try {
			obj = evaluationPeriodeService.findByAnneeAndEcoleAndPeriodeAndNiveau(annee, ecole, periode, niveau);
			System.out.println(obj);
			return Response.ok(obj).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(obj).build();
		}
	}
	@GET
	@Path("/get-by-annee-ecole/{annee}/{ecole}/{niveau}")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation Periode")
	public Response getListByAnneeAndEcoleAndNiveau(@PathParam("annee") Long annee, @PathParam("ecole") Long ecole, @PathParam("niveau") Long niveau){
		List<EvaluationPeriode> list = new ArrayList<>();
		try {
			list = evaluationPeriodeService.findByAnneeAndEcoleAndNiveau(annee, ecole, niveau);
			return Response.ok(list).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(list).build();
		}
	}

	@GET
	@Path("/get-by-id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation Periode")
	public Response getById(@PathParam("id") String id){
		EvaluationPeriode evalPeriode = null;
		try {
			evalPeriode = EvaluationPeriode.findById(id);
			return Response.ok(evalPeriode).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(evalPeriode).build();
		}
	}

	@POST
	@Path("/save-handle")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation Periode")
	public Response saveHandle(EvaluationPeriode evaluationPeriode){
		EvaluationPeriode obj = new EvaluationPeriode();
		String id = "";
		try {
			id = evaluationPeriodeService.saveHandle(evaluationPeriode);
			System.out.println("Id >>> "+id);
			return Response.ok(evaluationPeriodeService.getById(id)).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(obj).build();
		}
	}

	@DELETE
	@Path("/delete-process/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Tag(name = "Evaluation Periode")
	public Response delete(@PathParam("id") String id){
		try {
			evaluationPeriodeService.delete(id);
			System.out.println("Id supprimé >>> "+id);
			return Response.ok().build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
