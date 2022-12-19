package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.entities.Evaluation;
import com.vieecoles.services.EvaluationService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@Path("/evaluations")
public class EvaluationResource {

	@Inject
	EvaluationService evaluationService;

	Logger logger = Logger.getLogger(EvaluationResource.class.getName());

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des evaluations", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response list() {
		logger.info("--------> listAll ------>");
		List<Evaluation> list = evaluationService.getList();
		return Response.ok().entity(list).build();
	}

	@GET
	@Path("/get-count-classe-matiere")
	@Operation(description = "Obtenir le nombre des evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getCountByClasseAndMatiere(@QueryParam("classeId") Long classeId,@QueryParam("matiereId") Long matiereId) {
		logger.info("--------> get count ------>");

		return Response.ok().entity(evaluationService.getCountByClasseAndMatiere(classeId, matiereId)).build();
	}


	@GET
	@Path("/get-classe-matiere")
	@Operation(description = "Obtenir les evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getByClasseAndMatiere(@QueryParam("classeId") String classeId,@QueryParam("matiereId") String matiereId) {


		return Response.ok().entity(evaluationService.getByClasseAndMatiere(Long.parseLong(classeId),Long.parseLong(matiereId))).build();
	}

	@GET
	@Path("/get-classe-matiere-periode")
	@Operation(description = "Obtenir les evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response getByClasseAndMatierePeriode(@QueryParam("classeId") String classeId,@QueryParam("matiereId") String matiereId, @QueryParam("periodeId") String periodeId) {


		return Response.ok().entity(evaluationService.getByClasseAndMatiereAndPeriode(Long.parseLong(classeId),Long.parseLong(matiereId),Long.parseLong(periodeId))).build();
	}

	@GET
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtenir l evaluation par son id", summary = "")
	@Tag(name = "Evaluation")
    public Evaluation get(@PathParam("id") long id) {
		Evaluation ev = evaluationService.findById(id);
    	if(ev == null) {
    		logger.info(String.format("Evaluation non trouvee [ id = %s ]",id));
    		return new Evaluation();
    	} else {
    		return ev;
    	}
    }

	@GET
    @Path("/code/{code}")
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtenir l evaluation par son code", summary = "")
	@Tag(name = "Evaluation")
    public Evaluation getByCode(@PathParam("code") String code) {
		logger.info("EvaluationResource.getByCode()");
		Evaluation ev = evaluationService.findByCode(code);
    	if(ev == null) {
    		logger.info(String.format("Evaluation non trouvee [ code = %s ]",code));
    		return new Evaluation();
    	} else {
    		return ev;
    	}
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Evaluation")
    public Response create(Evaluation evaluation) {
    	try {
    		logger.info("Saving ...");
    		evaluationService.create(evaluation);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity("Erreur de creation").build();
    	 }
    	return Response.ok("Enregistrement reussi").build();
    }

    @POST
    @Path("/saveAndDisplay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Evaluation")
    public Response createAndDisplay(Evaluation evaluation) {
    	try {
    		logger.info("Saving and display ...");
    		evaluationService.create(evaluation);
    	} catch(Exception e) {
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
		if(ev==null) {
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

    	Evaluation ev = evaluationService.updateAndDisplay(evaluation);
		if(ev==null) {
			throw new NotFoundException();
		}
    	return Response.ok().entity(ev).build();
    }


    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Evaluation")
    public Response delete(@PathParam("id") String id) {
    	try {
    		evaluationService.delete(id);
			return Response.ok("Suppression Evaluation id = "+id).build();
		}catch(RuntimeException re){
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}


    }
}
