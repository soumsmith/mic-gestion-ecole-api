package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.EvaluationsProfStatDto;
import com.vieecoles.steph.dto.ImportEvaluationDto;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.services.EvaluationService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/evaluations")
public class EvaluationResource {

	@Inject
	EvaluationService evaluationService;

	// Logger logger = Logger.getLogger(EvaluationResource.class.getName());

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des evaluations", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response list() {
		// logger.info("--------> listAll ------>");
		List<Evaluation> list = evaluationService.getList();
		return Response.ok().entity(list).build();
	}

	@GET
	@Path("/get-count-classe-matiere")
	@Operation(description = "Obtenir le nombre des evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getCountByClasseAndMatiere(@QueryParam("anneeId") Long anneeId,
			@QueryParam("classeId") Long classeId, @QueryParam("matiereId") Long matiereId) {
		// logger.info("--------> get count ------>");

		return Response.ok().entity(evaluationService.getCountByClasseAndMatiere(anneeId, classeId, matiereId)).build();
	}

	@GET
	@Path("/get-by-classe-and-matiere")
	@Operation(description = "Obtenir par classe an la matiere", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getByClasseAndMatiere(@QueryParam("anneeId") Long anneeId, @QueryParam("classeId") Long classeId,
			@QueryParam("matiereId") Long matiereId) {
		// logger.info("--------> get count ------>");

		return Response.ok().entity(evaluationService.getByClasseAndMatiere(anneeId, classeId, matiereId)).build();
	}

	@GET
	@Path("/get-non-groupe")
	@Operation(description = "Obtenir par classe an la matiere", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getByClasseAndMatiere(@QueryParam("annee") Long anneeId, @QueryParam("ecole") Long ecoleId,
			@QueryParam("niveau") Long niveauId, @QueryParam("matiere") Long matiereId,
			@QueryParam("periode") Long periodeId) {

		return Response.ok().entity(evaluationService.getNonGroupe(anneeId, ecoleId, niveauId, matiereId, periodeId))
				.build();
	}

	@GET
	@Path("/get-classe-matiere")
	@Operation(description = "Obtenir les evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getByClasseAndMatiere(@QueryParam("anneeId") String anneeId,
			@QueryParam("classeId") String classeId, @QueryParam("matiereId") String matiereId) {

		return Response.ok().entity(evaluationService.getByClasseAndMatiere(Long.parseLong(anneeId),
				Long.parseLong(classeId), Long.parseLong(matiereId))).build();
	}

	@GET
	@Path("/get-classe-matiere-periode")
	@Operation(description = "Obtenir les evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getByClasseAndMatierePeriode(@QueryParam("classeId") String classeId,
			@QueryParam("matiereId") String matiereId, @QueryParam("periodeId") String periodeId,
			@QueryParam("annee") String anneeId) {
		List<Evaluation> evaluations = null;
		try {
			evaluations = evaluationService.getByClasseAndMatiereAndPeriode(Long.parseLong(classeId),
					Long.parseLong(matiereId), Long.parseLong(periodeId), Long.parseLong(anneeId));
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return Response.ok().entity(evaluations).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Obtenir l evaluation par son id", summary = "")
	@Tag(name = "Evaluation")
	public Evaluation get(@PathParam("id") long id) {
		Evaluation ev = evaluationService.findById(id);
		if (ev == null) {
			// logger.info(String.format("Evaluation non trouvee [ id = %s ]",id));
			return new Evaluation();
		} else {
			return ev;
		}
	}

	@GET
	@Path("/is-locked/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "info de verrouillage potentiel de la saisie d une évaluation", summary = "")
	@Tag(name = "Evaluation")
	public Response isLocked(@PathParam("id") long id) {
		return Response.ok(evaluationService.isLockable(id)).build();
	}

	@GET
	@Path("/code/{code}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Obtenir l evaluation par son code", summary = "")
	@Tag(name = "Evaluation")
	public Evaluation getByCode(@PathParam("code") String code) {
		// logger.info("EvaluationResource.getByCode()");
		Evaluation ev = evaluationService.findByCode(code);
		if (ev == null) {
			// logger.info(String.format("Evaluation non trouvee [ code = %s ]",code));
			return new Evaluation();
		} else {
			return ev;
		}
	}

	@GET
	@Path("/statistique-prof/{ecole}/{annee}/{periode}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Obtenir les statistiques par type d'evaluation", summary = "")
	@Tag(name = "Evaluation")
	public Response getStatistiquesProf(@PathParam("ecole") Long ecole, @PathParam("annee") Long annee,
			@PathParam("periode") Long periode, @PathParam("id") Long id) {
		// logger.info("EvaluationResource.getByCode()");
		EvaluationsProfStatDto dto = evaluationService.getEvaluationStatByProf(id, annee, ecole, periode);
		return Response.ok(dto).build();
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Evaluation")
	public Response create(Evaluation evaluation) {
		try {
			// logger.info("Saving ...");
			evaluationService.create(evaluation);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erreur de creation").build();
		}
		return Response.ok("Enregistrement reussi").build();
	}

	@POST
	@Path("/import")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response importation(List<ImportEvaluationDto> importEvaluationDtos) {
		try {
			// logger.info("Saving ...");
			Gson g = new Gson();
			System.out.println(g.toJson(importEvaluationDtos));
//    		evaluationService.create(evaluation);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError()
					.entity(new Message("Info", "Opération d'importation", "Erreur lors de l'importation")).build();
		}
		return Response.ok(new Message("Info", "Opération d'importation", "Importation bien effectuée")).build();
	}

	@POST
	@Path("/saveAndDisplay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response createAndDisplay(Evaluation evaluation) {
		try {
			// logger.info("Saving and display ...");
			evaluationService.create(evaluation);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(new Evaluation()).build();
		}
		return Response.ok().entity(evaluationService.findById(evaluation.getId())).build();
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Evaluation update(Evaluation evaluation) {

		Evaluation ev = evaluationService.update(evaluation);
		if (ev == null) {
			throw new NotFoundException();
		}
		return ev;
	}

	@POST
	@Path("/update-display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response updateAndDisplay(Evaluation evaluation) {
		System.out.println(evaluation);

		Evaluation ev = evaluationService.updateAndDisplay(evaluation);
		if (ev == null) {
			throw new NotFoundException();
		}
		return Response.ok().entity(ev).build();
	}

	@GET
	@Path("/get-max-numero/{ecoleId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Long getMaxNumero(@PathParam("ecoleId") Long ecoleId) {
		return evaluationService.getMaxNumeroByEcole(ecoleId);
	}

	@DELETE
	@Path("/delete-handle/{id}/{user}")
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Evaluation")
	public Response delete(@PathParam("id") String id, @PathParam("user") String user) {
		try {
			evaluationService.deleteHandle(Long.parseLong(id), user);
			return Response.ok("Suppression Evaluation id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity(re.getMessage()).build();
		}

	}
}
