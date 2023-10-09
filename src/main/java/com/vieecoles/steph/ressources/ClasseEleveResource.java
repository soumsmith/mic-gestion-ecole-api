package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.ClasseEleve;
import com.vieecoles.steph.entities.Inscription;
import com.vieecoles.steph.services.ClasseEleveService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
		if (ce == null) {
			System.out.println("classe eleve non trouvee avec id = " + id);
			return new ClasseEleve();
		} else {
			return ce;
		}
	}

	@GET
	@Path("/retrieve-by-classe/{classeId}/{anneeId}")
	@Operation(description = "Obtenir la classeEleve par annee et par classe", summary = "")
	@Tag(name = "Classe-Eleve")
	public List<ClasseEleve> getByClasseAnnee(@PathParam("classeId") long classeId,
			@PathParam("anneeId") Long anneeId) {
		return classeEleveService.getByClasseAnnee(classeId, anneeId);

	}

	@GET
	@Path("/retrieve-by-branche-annee/{brancheId}/{anneeId}/")
	@Operation(description = "Obtenir les eleve affecte dans des classes par branche", summary = "")
	@Tag(name = "Classe-Eleve")
	public Response getByBrancheAnnee(@PathParam("brancheId") long brancheId,
			@PathParam("anneeId") Long anneeId, @QueryParam("ecole") Long ecoleId) {
		System.out.println("anne: "+anneeId+" ecole : "+ecoleId+" branche : "+brancheId);
		return Response.ok(classeEleveService.getByBrancheAndAnnee(brancheId, anneeId, ecoleId)).build();

	}
	
	@GET
	@Path("/get-count-by-classe/{classeId}/{anneeId}")
	@Operation(description = "Obtenir le nombre d Eleve par classe et annee", summary = "")
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Classe-Eleve")
	public int getCountByClasseAnnee(@PathParam("classeId") long classeId,
			@PathParam("anneeId") Long anneeId) {
		return  classeEleveService.getCountByClasseAnnee(classeId, anneeId);

	}

	@POST
	@Path("/handle-save/{classe}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Classe-Eleve")
	public Response handleCreate(@PathParam("classe") long classeId, List<Inscription> inscriptions) {
		List<ClasseEleve> classeEleves = new ArrayList<ClasseEleve>();
		try {
			System.out.println("building classe-eleve object to persist ...");
			classeEleves = classeEleveService.handleCreate(classeId, inscriptions);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erreur de creation").build();
		}
		return Response.ok(classeEleves).build();
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
	@Tag(name = "Classe-Eleve")
	public Response createAndDisplay(ClasseEleve ce) {
		try {
			System.out.println("Saving and display ...");
			classeEleveService.create(ce);
		} catch (Exception e) {
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
		if (ce == null) {
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
		if (ce == null) {
			throw new NotFoundException();
		}
		return Response.ok().entity(ce).build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Classe-Eleve")
	public Response delete(@PathParam("id") long id) {
		try {
			classeEleveService.delete(id);
			return Response.ok("Suppression ClasseEleve id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity(re.getMessage()).build();
		}
	}
}
