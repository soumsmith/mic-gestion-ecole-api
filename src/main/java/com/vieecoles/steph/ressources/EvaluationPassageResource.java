package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.dto.ImportEvaluationDto;
import com.vieecoles.steph.entities.Evaluation;
import com.vieecoles.steph.entities.EvaluationLoader;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.services.EvaluationLoaderService;
import com.vieecoles.steph.services.EvaluationService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;


@Path("/evaluations-passage")
public class EvaluationPassageResource {

	@Inject
	EvaluationService evaluationService;
	
	@Inject
	EvaluationLoaderService evaluationLoaderService;

	//Logger logger = Logger.getLogger(EvaluationResource.class.getName());

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des evaluations", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "EvaluationLoader")
	public Response list() {
	//	logger.info("--------> listAll ------>");
		List<Evaluation> list = evaluationService.getList();
		return Response.ok().entity(list).build();
	}

	@GET
	@Path("/get-count-classe-matiere")
	@Operation(description = "Obtenir le nombre des evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "EvaluationLoader")
	public Response getCountByClasseAndMatiere(@QueryParam("anneeId") Long anneeId, @QueryParam("classeId") Long classeId,@QueryParam("matiereId") Long matiereId) {
		//logger.info("--------> get count ------>");

		return Response.ok().entity(evaluationService.getCountByClasseAndMatiere(classeId, matiereId)).build();
	}


	@GET
	@Path("/get-classe-matiere")
	@Operation(description = "Obtenir les evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "EvaluationLoader")
	public Response getByClasseAndMatiere(@QueryParam("classeId") String classeId,@QueryParam("matiereId") String matiereId) {


		return Response.ok().entity(evaluationService.getByClasseAndMatiere(Long.parseLong(classeId),Long.parseLong(matiereId))).build();
	}

	@GET
	@Path("/get-classe-matiere-periode")
	@Operation(description = "Obtenir les evaluations par matiere et classe", summary = "")
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "EvaluationLoader")
	public Response getByClasseAndMatierePeriode(@QueryParam("classeId") String classeId,@QueryParam("matiereId") String matiereId, @QueryParam("periodeId") String periodeId,@QueryParam("annee") String anneeId) {


		return Response.ok().entity(evaluationService.getByClasseAndMatiereAndPeriode(Long.parseLong(classeId),Long.parseLong(matiereId),Long.parseLong(periodeId),Long.parseLong(anneeId))).build();
	}

	@GET
    @Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtenir l evaluation par son id", summary = "")
	@Tag(name = "EvaluationLoader")
    public EvaluationLoader get(@PathParam("id") long id) {
		EvaluationLoader ev = evaluationLoaderService.findById(id);
    	if(ev == null) {
    		//logger.info(String.format("Evaluation non trouvee [ id = %s ]",id));
    		return new EvaluationLoader();
    	} else {
    		return ev;
    	}
    }

	@GET
    @Path("/code/{code}")
	@Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Obtenir l evaluation par son code", summary = "")
	@Tag(name = "EvaluationLoader")
    public List<EvaluationLoader> getByCode(@PathParam("code") String code) {
		//logger.info("EvaluationResource.getByCode()");
		List<EvaluationLoader> ev = evaluationLoaderService.findByCode(code);
    	if(ev == null) {
    	//	logger.info(String.format("Evaluation non trouvee [ code = %s ]",code));
    		return new ArrayList<EvaluationLoader>();
    	} else {
    		return ev;
    	}
    }

    @POST
    @Path("/apply-data/{matiere}/{periode}/{annee}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "EvaluationLoader")
    public Response appliquer(List<EvaluationLoader> evaLoaders, @PathParam("matiere") Long matiere, @PathParam("periode") Long periode, @PathParam("annee") Long annee)  {
    	try {
    		
    		evaluationLoaderService.appliquerChargement(evaLoaders, matiere, periode, annee);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity("Erreur d' application des données").build();
    	 }
    	return Response.ok("Opération reussie").build();
    }
    
    @POST
    @Path("/import/{classe}/{annee}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "EvaluationLoader")
    public Response importation(List<ImportEvaluationDto> importEvaluationDtos, @PathParam("classe") Long classe, @PathParam("annee") Long annee) {
    	String uuid  = null;
    	try {
    		System.out.println("Classe :: "+ classe+" :: "+annee);
    		List<EvaluationLoader> evaluationLoaders = new ArrayList<EvaluationLoader>(); 
//    		Gson g =new Gson();
//    		System.out.println(g.toJson(importEvaluationDtos));
    		for(ImportEvaluationDto dto : importEvaluationDtos) {
    			EvaluationLoader load  = new EvaluationLoader();
    			load.setMatricule(dto.getMatricule());
    			load.setNotes(dto.getNotes());
    			evaluationLoaders.add(load);
    		}
    		
    		uuid = evaluationLoaderService.handleLoading(evaluationLoaders, classe, annee);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(new Message("Info","Opération d'importation","Importation bien effectuée")).build();
    	 }
    	return Response.ok(evaluationLoaderService.findByCode(uuid)).build();
    }

    @POST
    @Path("/saveAndDisplay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "EvaluationLoader")
    public Response createAndDisplay(Evaluation evaluation) {
    	try {
    		//logger.info("Saving and display ...");
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
    @Tag(name = "EvaluationLoader")
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
    @Tag(name = "EvaluationLoader")
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
    @Tag(name = "EvaluationLoader")
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
