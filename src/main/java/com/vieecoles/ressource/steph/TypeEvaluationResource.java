package com.vieecoles.ressource.steph;

import com.vieecoles.dao.entities.TypeEvaluation;

import com.vieecoles.services.stephServi.TypeEvaluationService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/typeevaluations")
public class TypeEvaluationResource {

	@Inject
    TypeEvaluationService typeEvaluationService;

	@GET
    @Path("/list")
    @Tag(name = "TypeEvaluation")
    public Response list() {
        return Response.ok().entity(typeEvaluationService.getList()).build();
    }


    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir la classe par son id", summary = "")
	@Tag(name = "TypeEvaluation")
    public TypeEvaluation get(@PathParam("id") long id) {
    	TypeEvaluation te = typeEvaluationService.findById(id);
    	if(te == null) {
    		System.out.println("activite non trouvee avec id = "+id);
    		return new TypeEvaluation();
    	} else {
    		return te;
    	}
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "TypeEvaluation")
    public Response create(TypeEvaluation typeEvaluation) {
    	try {
    		System.out.println("Saving ...");
    		typeEvaluationService.save(typeEvaluation);
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
    @Tag(name = "TypeEvaluation")
    public Response createAndDisplay(TypeEvaluation typeEvaluation) {
    	try {
    		System.out.println("Saving and display ...");
    		typeEvaluationService.save(typeEvaluation);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(new TypeEvaluation()).build();
    	 }
    	return Response.ok().entity(typeEvaluationService.findById(typeEvaluation.getId())).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "TypeEvaluation")
    public TypeEvaluation update(TypeEvaluation typeEvaluation) {

    	TypeEvaluation tes = typeEvaluationService.update(typeEvaluation);
		if(tes==null) {
			throw new NotFoundException();
		}
    	return tes;
    }

    @POST
    @Path("/update-display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "TypeEvaluation")
    public Response updateAndDisplay(TypeEvaluation typeEvaluation) {

    	TypeEvaluation te = typeEvaluationService.updateAndDisplay(typeEvaluation);
		if(te==null) {
			throw new NotFoundException();
		}
    	return Response.ok().entity(te).build();
    }


    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "TypeEvaluation")
    public Response delete(@PathParam("id") String id) {
    	try {
    		typeEvaluationService.delete(id);
			return Response.ok("Suppression TypeEvaluation id = "+id).build();
		}catch(RuntimeException re){
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}


    }
}
