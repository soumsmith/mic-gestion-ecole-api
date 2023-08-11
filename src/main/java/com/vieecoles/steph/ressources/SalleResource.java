package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.Salle;
import com.vieecoles.steph.services.SalleService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/salle")
public class SalleResource {

	@Inject
	SalleService salleService;

	@GET
    @Path("/list")
    @Tag(name = "Salle")
    public Response list() {
        return Response.ok().entity(salleService.list()).build();
    }
	
	@GET
    @Path("/list-by-ecole")
    @Tag(name = "Salle")
    public Response listByEcole(@QueryParam("ecole") Long ecoleId) {
        return Response.ok().entity(salleService.getSallesByEcole(ecoleId)).build();
    }

	@POST
    @Path("/saveAndDisplay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Salle")
    public Response createAndDisplay(Salle salle) {
    	try {
    		System.out.println("Saving and display ...");
    		salleService.save(salle);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(new Salle()).build();
    	 }
    	return Response.ok().entity(salleService.findById(salle.getId())).build();
    }

	@POST
    @Path("/update-display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Salle")
    public Response updateAndDisplay(Salle salle) {

		Salle sc = salleService.updateAndDisplay(salle);
		if(sc==null) {
			throw new NotFoundException();
		}
    	return Response.ok().entity(sc).build();
    }

	@DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Salle")
    public Response delete(@PathParam("id") String id) {
    	try {
    		salleService.delete(id);
			return Response.ok("Suppression salle id = "+id).build();
		}catch(RuntimeException re){
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}


    }

	@GET
    @Path("/get-salles-dispo-heures")
    @Tag(name = "Salle")
    public Response getWithSallesDisponibles(@QueryParam("annee") long anneeId, @QueryParam("classe") long classeId, @QueryParam("jour") int jourId, @QueryParam("heureDeb") String heureDeb, @QueryParam("heureFin") String heureFin) {
        return Response.ok().entity(salleService.getWithSallesDisponibles(anneeId, classeId, jourId, heureDeb, heureFin)).build();
    }
}
