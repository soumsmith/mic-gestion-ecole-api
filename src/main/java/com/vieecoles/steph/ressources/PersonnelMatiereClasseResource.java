package com.vieecoles.ressource.steph.ressources;

import com.vieecoles.entities.PersonnelMatiereClasse;
import com.vieecoles.services.PersonnelMatiereClasseService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("personnel-matiere-classe")
public class PersonnelMatiereClasseResource {
	@Inject
	PersonnelMatiereClasseService persMatClasService;

	@GET
    @Path("/list")
    @Tag(name = "PersonnelMatiereClasse")
    public Response list() {
        return Response.ok().entity(persMatClasService.getList()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir la classe par son id", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public PersonnelMatiereClasse get(@PathParam("id") long id) {
    	PersonnelMatiereClasse classe = persMatClasService.findById(id);
    	if(classe == null) {
    		System.out.println("classe non trouvee avec id = "+id);
    		return new PersonnelMatiereClasse();
    	} else {
    		return classe;
    	}
    }

    @GET
    @Path("/get-by-branche")
    @Operation(description = "Obtenir les classes de meme branche", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> getByBranche(@QueryParam("branche") long brancheId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByBranche(brancheId, 1);
    	return personnels;
    }


    @GET
    @Path("/get-by-matiere")
    @Operation(description = "Obtenir les classes de meme matiere", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> getByMatiere(@QueryParam("matiere") long matiereId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByMatiere(matiereId, 1);
    	return personnels;
    }

    @GET
    @Path("/get-by-prof")
    @Operation(description = "Obtenir les classes de meme prof", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> getByProf(@QueryParam("prof") long profId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByMatiere(profId, 1);
    	return personnels;
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "PersonnelMatiereClasse")
    public Response create(PersonnelMatiereClasse personnel) {
    	try {
    		System.out.println("Saving ...");
    		persMatClasService.save(personnel);
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
    @Tag(name = "PersonnelMatiereClasse")
    public Response createAndDisplay(PersonnelMatiereClasse personnel) {
    	try {
    		System.out.println("Saving and display ...");
    		persMatClasService.save(personnel);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(new PersonnelMatiereClasse()).build();
    	 }
    	return Response.ok().entity(persMatClasService.findById(personnel.getId())).build();
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "PersonnelMatiereClasse")
    public PersonnelMatiereClasse update(PersonnelMatiereClasse personnel) {

    	PersonnelMatiereClasse c = persMatClasService.update(personnel);
		if(c==null) {
			throw new NotFoundException();
		}
    	return c;
    }

    @POST
    @Path("/update-display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "PersonnelMatiereClasse")
    public Response updateAndDisplay(PersonnelMatiereClasse personnel) {

    	PersonnelMatiereClasse c = persMatClasService.updateAndDisplay(personnel);
		if(c==null) {
			throw new NotFoundException();
		}
//		System.out.println(new Gson().toJson(c));
    	return Response.ok().entity(c).build();
    }

}
