package com.vieecoles.ressource.steph;

import com.vieecoles.dao.entities.ClasseEleve;

import com.vieecoles.services.stephServi.ClasseEleveService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/classe-eleve")
public class ClasseEleveResource {

	@Inject
    ClasseEleveService classeEleveService;

	@GET
    @Path("/list")
    @Tag(name = "Classe-Eleve")
    public Response list() {
        return Response.ok().entity(classeEleveService.getList()).build();
    }


    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir la classeEleve par son id", summary = "")
	@Tag(name = "Classe-Eleve")
    public ClasseEleve get(@PathParam("id") long id) {
    	ClasseEleve ce = classeEleveService.findById(id);
    	if(ce == null) {
    		System.out.println("classe eleve non trouvee avec id = "+id);
    		return new ClasseEleve();
    	} else {
    		return ce;
    	}
    }

    @GET
    @Path("/getByAnneeAndClasse")
    @Operation(description = "Obtenir la classeEleve par annee et par classe", summary = "")
	@Tag(name = "Classe-Eleve")
    public List<ClasseEleve> getByClasseAnnee(long classeId, Long anneeId) {
    	return  classeEleveService.getByClasseAnnee(classeId, anneeId);

    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Classe-Eleve")
    public Response create(ClasseEleve ce) {
    	try {
    		System.out.println("Saving ...");
    		classeEleveService.create(ce);
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
    @Tag(name = "Classe-Eleve")
    public Response createAndDisplay(ClasseEleve ce) {
    	try {
    		System.out.println("Saving and display ...");
    		classeEleveService.create(ce);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(new ClasseEleve()).build();
    	 }
    	return Response.ok().entity(classeEleveService.findById(ce.getId())).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Classe-Eleve")
    public ClasseEleve update(ClasseEleve classeEleve) {

    	ClasseEleve ce = classeEleveService.update(classeEleve);
		if(ce==null) {
			throw new NotFoundException();
		}
    	return ce;
    }

    @POST
    @Path("/update-display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Classe-Eleve")
    public Response updateAndDisplay(ClasseEleve classeEleve) {

    	ClasseEleve ce = classeEleveService.updateAndDisplay(classeEleve);
		if(ce==null) {
			throw new NotFoundException();
		}
    	return Response.ok().entity(ce).build();
    }


    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Classe-Eleve")
    public Response delete(@PathParam("id") String id) {
    	try {
    		classeEleveService.delete(id);
			return Response.ok("Suppression ClasseEleve id = "+id).build();
		}catch(RuntimeException re){
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}


    }
}
