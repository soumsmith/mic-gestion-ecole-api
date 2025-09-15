package com.vieecoles.steph.ressources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.Activite;
import com.vieecoles.steph.services.ActiviteService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/activite")
@Produces(MediaType.APPLICATION_JSON)
public class ActiviteRessource {

	@Inject
	ActiviteService activiteService;

	@GET
	@Path("/list")
	@Tag(name = "Activite")
	public Response list() {
		return Response.ok().entity(activiteService.getList()).build();
	}

	@GET
	@Path("/list-by-prof-and-day")
	@Tag(name = "Activite")
	public Response listByProfAndDay(@QueryParam("profId") Long profId, @QueryParam("anneeId") Long anneeId, @QueryParam("day") int day) {
		return Response.ok().entity(activiteService.getList()).build();
	}

	@GET
	@Path("/list-by-ecole/{ecoleId}")
	@Tag(name = "Activite")
	public Response getListByEcole(@PathParam("ecoleId") Long ecoleId) {
		return Response.ok().entity(activiteService.getListByEcole(ecoleId)).build();
	}

	@GET
	@Path("/list-by-classe-jour")
	@Tag(name = "Activite")
	public Response getByClasseAndJour(@QueryParam("annee") long anneeId, @QueryParam("classe") long classeId,
			@QueryParam("jour") int jourId) {
		return Response.ok().entity(activiteService.getListByClasseAndJour(classeId, jourId)).build();
	}

	@GET
	@Path("/list-by-classe")
	@Tag(name = "Activite")
	public Response getByClasse(@QueryParam("classe") long classeId) {
		return Response.ok().entity(activiteService.getListByClasse(classeId)).build();
	}

	@GET
	@Path("/is-plage-horaire-valid")
	@Tag(name = "Activite")
	public Response isPlageHoraireValid(@QueryParam("annee") long anneeId, @QueryParam("classe") long classeId,
			@QueryParam("jour") int jourId, @QueryParam("heureDeb") String heureDeb,
			@QueryParam("heureFin") String heureFin) {
		return Response.ok().entity(activiteService.isPlageHoraireValid(anneeId, classeId, jourId, heureDeb, heureFin))
				.build();
	}

	@GET
	@Path("/{id}")
	@Operation(description = "Obtenir la classe par son id", summary = "")
	@Tag(name = "Activite")
	public Activite get(@PathParam("id") String id) {
		Activite atv = activiteService.findById(id);
		if (atv == null) {
			System.out.println("activite non trouvee avec id = " + id);
			return new Activite();
		} else {
			return atv;
		}
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Activite")
	public Response create(Activite activite) {
		try {
			System.out.println("Saving ...");
			activiteService.save(activite);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erreur de creation").build();
		}
		return Response.ok("Enregistrement reussi").build();
	}

	@POST
	@Path("/saveAndDisplay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Activite")
	public Response createAndDisplay(Activite activite) {
		try {
			System.out.println("Saving and display ...");
			activiteService.save(activite);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(new Activite()).build();
		}
		System.out.println("activite ::: "+activite.getId());
		return Response.ok().entity(activiteService.findById(activite.getId())).build();
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Activite")
	public Activite update(Activite activite) {

		Activite atv = activiteService.update(activite);
		if (atv == null) {
			throw new NotFoundException();
		}
		return atv;
	}

	@POST
	@Path("/update-display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Activite")
	public Response updateAndDisplay(Activite activite) {

		Activite atv = activiteService.updateAndDisplay(activite);
		if (atv == null) {
			throw new NotFoundException();
		}
		return Response.ok().entity(atv).build();
	}

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Activite")
	public Response test() {
		System.out.println("----------> RESSOURCE REACHED");
//    	try {
//			Thread.sleep(11000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		return Response.ok("Hey").build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Activite")
	public Response delete(@PathParam("id") String id) {
		try {
			activiteService.delete(id);
			return Response.ok("Suppression activite id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}

	}
}
