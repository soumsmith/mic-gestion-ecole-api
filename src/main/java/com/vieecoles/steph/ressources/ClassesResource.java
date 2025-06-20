package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.services.ClasseService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


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
	    @Path("/list-by-ecole")
	    @Tag(name = "Classe")
	    public Response findByEcole(@QueryParam("ecole") Long ecole) {
	        return Response.ok().entity(classeService.getListClasseByEcole(ecole)).build();
	    }

	    @GET
	    @Path("/list-by-ecole-sorted")
	    @Tag(name = "Classe")
	    public Response getListSortedByClasseByEcole(@QueryParam("ecole") Long ecole) {
	        return Response.ok().entity(classeService.getListSortedByClasseByEcole(ecole)).build();
	    }

	    @GET
	    @Path("/list-populate")
	    @Tag(name = "Classe")
	    public Response listPopulate() {
//	    	System.out.println("ClassesResource.listPopulate()");
	        return Response.ok().entity(classeService.getListClasseAllFields()).build();
	    }

	    @GET
	    @Path("/list-populate-by-ecole")
	    @Tag(name = "Classe")
	    public Response listPopulateByEcole(@QueryParam("ecole") Long ecole) {
//	    	System.out.println("ClassesResource.listPopulateByEcole()");
	        return Response.ok().entity(classeService.getListClasseAllFields(ecole)).build();
	    }

	    @GET
	    @Path("/list-classe-by-matricule-annee")
	    @Tag(name = "Classe")
	    public Response listClasseByMatricule(@QueryParam("matricule") String matricule, @QueryParam("annee") Long annee) {
//	    	System.out.println("ClassesResource.listPopulateByEcole()");
	        return Response.ok().entity(classeService.getListClasseStudentByMatricule(matricule, annee)).build();
	    }

	    @GET
	    @Path("/list-all-populate-by-ecole")
	    @Tag(name = "Classe")
	    public Response listAllPopulateByEcole(@QueryParam("ecole") Long ecole) {
//	    	System.out.println("ClassesResource.listPopulateByEcole()");
	        return Response.ok().entity(classeService.getListAllClasseAllFields(ecole)).build();
	    }

	    @GET
	    @Path("/{id}")
	    @Operation(description = "Obtenir la classe par son id", summary = "")
		@Tag(name = "Classe")
	    public Classe get(@PathParam("id") long id) {
	    	Classe classe = classeService.findById(id);
	    	if(classe == null) {
	    		System.out.println("classe non trouvee avec id = "+id);
	    		return new Classe();
	    	} else {
	    		return classe;
	    	}
	    }

	    @GET
	    @Path("/get-by-branche")
	    @Operation(description = "Obtenir les classes de meme branche", summary = "")
		@Tag(name = "Classe")
	    public List<Classe> getByBranche(@QueryParam("branche") long brancheId,@QueryParam("ecole") long ecole ) {
	    	List<Classe> classes = classeService.findByBranche(brancheId, ecole);
	    	return classes;
	    }


	    @GET
	    @Path("/get-visible-by-branche")
	    @Operation(description = "Obtenir les classes de meme branche", summary = "")
		@Tag(name = "Classe")
	    public List<Classe> getVisibleByBranche(@QueryParam("branche") long brancheId,@QueryParam("ecole") long ecole ) {
	    	List<Classe> classes = classeService.findVisibleByBranche(brancheId, ecole);
	    	return classes;
	    }

	    @POST
	    @Path("/save")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.TEXT_PLAIN)
	    @Tag(name = "Classe")
	    public Response create(Classe classe) {
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
	    public Response createAndDisplay(Classe classe) {
	    	try {
	    		System.out.println("Saving and display ...");
	    		classeService.save(classe);
	    	} catch(Exception e) {
	    		 e.printStackTrace();
	    		 return Response.serverError().entity(new Classe()).build();
	    	 }
	    	return Response.ok().entity(classeService.findById(classe.getId())).build();
	    }

	    @POST
	    @Path("/update")
	    @Consumes(MediaType.APPLICATION_JSON)
	    @Produces(MediaType.APPLICATION_JSON)
	    @Tag(name = "Classe")
	    public Classe update(Classe classe) {

	    	Classe c = classeService.update(classe);
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
	    public Response updateAndDisplay(Classe classe) {

	    	Classe c = classeService.updateAndDisplay(classe);
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
