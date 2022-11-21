package com.vieecoles.ressource.steph;

import com.vieecoles.dao.entities.Evaluation;

import com.vieecoles.services.stephServi.EvaluationService;
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

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des evaluations", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Evaluation")
	public Response list() {
		System.out.println("--------> listAll ------>");
		List<Evaluation> list = evaluationService.getList();
		System.out.println(list.size());
		return Response.ok().entity(list).build();
	}

	@GET
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtenir l evaluation par son id", summary = "")
	@Tag(name = "Evaluation")
    public Evaluation get(@PathParam("id") long id) {
		Evaluation ev = evaluationService.findById(id);
    	if(ev == null) {
    		System.out.println(String.format("Evaluation non trouvee [ id = %s ]",id));
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
		System.out.println("EvaluationResource.getByCode()");
		Evaluation ev = evaluationService.findByCode(code);
    	if(ev == null) {
    		System.out.println(String.format("Evaluation non trouvee [ code = %s ]",code));
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
    		System.out.println("Saving ...");
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
    		System.out.println("Saving and display ...");
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
