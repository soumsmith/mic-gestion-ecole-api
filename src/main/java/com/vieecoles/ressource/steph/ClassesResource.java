package com.vieecoles.ressource.steph;


import com.vieecoles.dao.entities.operations.classe;
import com.vieecoles.services.stephServi.ClasseService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/classes")
@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
public class ClassesResource {

	    @Inject
		ClasseService classeService ;

	    @GET
	    @Path("/list")
	    @Tag(name = "Classe")
	    public Response list() {
	        return Response.ok().entity(classeService.getListClasse()).build();
	    }

	    @GET
	    @Path("/list-populate")
	    @Tag(name = "Classe")
	    public Response listPopulate() {
	    	System.out.println("ClassesResource.listPopulate()");
	        return Response.ok().entity(classeService.getListClasseAllFields()).build();
	    }

	    @GET
	    @Path("/{id}")
	    @Operation(description = "Obtenir la classe par son id", summary = "")
		@Tag(name = "Classe")
	    public classe get(@PathParam("id") long id) {
	    	classe classe = classeService.findById(id);
	    	if(classe == null) {
	    		System.out.println("classe non trouvee avec id = "+id);
	    		return new classe();
	    	} else {
	    		return classe;
	    	}
	    }

	    @POST
	    @Path("/save")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.TEXT_PLAIN)
	    @Tag(name = "Classe")
	    public Response create(classe classe) {
	    	try {
	    		System.out.println("Saving ...");
	    		classeService.save(classe);
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
	    @Tag(name = "Classe")
	    public Response createAndDisplay(classe classe) {
	    	try {
	    		System.out.println("Saving and display ...");
	    		classeService.save(classe);
	    	} catch(Exception e) {
	    		 e.printStackTrace();
	    		 return Response.serverError().entity(new classe()).build();
	    	 }
	    	return Response.ok().entity(classeService.findById(classe.getClasseid())).build();
	    }

	    @POST
	    @Path("/update")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    @Tag(name = "Classe")
	    public classe update(classe classe) {

	    	classe c = classeService.update(classe);
    		if(c==null) {
    			throw new NotFoundException();
    		}
	    	return c;
	    }

	    @POST
	    @Path("/update-display")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    @Tag(name = "Classe")
	    public Response updateAndDisplay(classe classe) {

	    	classe c = classeService.updateAndDisplay(classe);
    		if(c==null) {
    			throw new NotFoundException();
    		}
//    		System.out.println(new Gson().toJson(c));
	    	return Response.ok().entity(c).build();
	    }


	    @DELETE
	    @Path("/delete/{id}")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.TEXT_PLAIN)
	    @Tag(name = "Classe")
	    public Response delete(@PathParam("id") String id) {
	    	try {
				classeService.delete(id);
				return Response.ok("Suppression de classe id = "+id).build();
			}catch(RuntimeException re){
				re.printStackTrace();
				return Response.serverError().entity("Erreur lors de la suppression").build();
			}


	    }

}
