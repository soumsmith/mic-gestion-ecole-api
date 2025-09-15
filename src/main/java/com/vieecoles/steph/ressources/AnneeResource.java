package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.services.AnneePeriodeService;
import com.vieecoles.steph.services.AnneeService;
import com.vieecoles.steph.services.EcoleService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.annotations.Param;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/annee")
public class AnneeResource {
	@Inject
	AnneeService anneeService;

	@Inject
	AnneePeriodeService anneePeriodeService;

	@Inject
	EcoleService ecoleService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des années", summary = "")
	@Tag(name = "Année scolaire")
	public Response list() {
		return Response.ok().entity(anneeService.getList()).build();
	}

	@GET
	@Path("/list-to-central")
	@Operation(description = "Obtenir la liste des années en central", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentral() {
		return Response.ok().entity(anneeService.getListByCentral()).build();
	}

	@GET
	@Path("/list-to-central-niveau-enseignement")
	@Operation(description = "Obtenir la liste des années en central pour un niveau d'enseignement", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentralNiveau(@QueryParam("niveau") Long niveau) {
		System.out.println("Coucou ");
		return Response.ok().entity(anneeService.getListByCentralByNiveauEnseignement(niveau)).build();
	}

	@GET
	@Path("/list-to-central-niveau-enseignement-projection")
	@Operation(description = "Obtenir la liste (de type projection) des années en central pour un niveau d'enseignement", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentralNiveauProjection(@QueryParam("niveau") Long niveau) {
		return Response.ok().entity(anneeService.getBasicListInCentralByNiveauEnseignementProjection(niveau)).build();
	}

	@GET
	@Path("/list-to-ecole")
	@Operation(description = "Obtenir la liste des années pour une école", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentral(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(anneeService.getListByEcole(ecoleId)).build();
	}

	@GET
	@Path("/list-to-ecole-dto")
	@Operation(description = "Obtenir la liste des années pour une école dto et l année centrale", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentralDto(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(anneeService.getListByEcoleDto(ecoleId)).build();
	}

	@GET
	@Path("/list-ouverte-to-ecole-dto")
	@Operation(description = "Obtenir l année ouverte pour une école dto et l année centrale", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentralOuvertDto(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(anneeService.getOpenAnneeByEcoleDto(ecoleId)).build();
	}

	@GET
	@Path("/list-opened-or-closed-to-ecole")
	@Operation(description = "Obtenir la liste des années centrales au moins ouvertes pour une école", summary = "")
	@Tag(name = "Année scolaire")
	public Response listEcole(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(anneeService.getListOpenOrCloseByEcole(ecoleId)).build();
	}

	@GET
	@Path("/list-opened-or-closed-to-ecole-vie-ecole")
	@Operation(description = "Obtenir la liste des années centrales au moins ouvertes pour une école de vie-ecole", summary = "")
	@Tag(name = "Année scolaire")
	public Response listEcolevieEcole(@QueryParam("ecole") String ecoleCode) {
		Ecole ecole = Ecole.find("identifiantVieEcole =?1",ecoleCode).firstResult();
		if (ecole == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			Long idEcole=ecole.getId();
			return Response.ok().entity(anneeService.getListOpenOrCloseByEcole(idEcole)).build();
		}

	}

	@GET
	@Path("/get-by-id/{id}")
	@Operation(description = "Obtenir une année scolaire via son id", summary = "")
	@Tag(name = "Année scolaire")
	public Response getById(@PathParam("id") Long id) {
		return Response.ok().entity(anneeService.getById(id)).build();
	}

	@GET
	@Path("/info-annee/{ecoleId}")
	@Operation(description = "Informations sur l année et les periode par rapport à la date courante", summary = "")
	@Tag(name = "Année scolaire")
	public Response getAnneeInfos(@PathParam("ecoleId") Long ecoleId) {
		return Response.ok().entity(anneeService.getAnneeInfo(ecoleId)).build();
	}

	@GET
	@Path("/delete/{id}")
	@Operation(description = "supprimer une année scolaire via son id", summary = "")
	@Tag(name = "Année scolaire")
	public Response delete(@PathParam("id") Long id) {
		try {
			anneeService.delete(id);
			return Response.ok().build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/delete-with-periodes/{id}")
	@Operation(description = "Obtenir une année scolaire via son id", summary = "")
	@Tag(name = "Année scolaire")
	public Response handleDelete(@PathParam("id") Long id) {
		try {
			anneeService.handleDelete(id);
			return Response.ok().build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/get-main-annee-by-ecole/{ecoleId}")
	@Operation(description = "Obtenir année scolaire à utiliser pour toute action dans le systeme si année ouverte", summary = "")
	@Tag(name = "Année scolaire")
	public Response getMainAnnee(@PathParam("ecoleId") Long ecoleId) {
		try {
			Ecole ecole = ecoleService.getById(ecoleId);
			return Response.ok(anneeService.findMainAnneeByEcole(ecole)).build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/get-main-annee-projection-by-ecole/{ecoleId}")
	@Operation(description = "Obtenir année scolaire à utiliser pour toute action dans le systeme si année ouverte", summary = "")
	@Tag(name = "Année scolaire")
	public Response getMainAnneeProjection(@PathParam("ecoleId") Long ecoleId) {
		try {
			Ecole ecole = ecoleService.getById(ecoleId);
			return Response.ok(anneeService.findMainAnneeWithProjectionByEcole(ecole)).build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/init-annee-for-ecole/{ecoleId}")
	@Operation(description = "Initialiser les années scolaires pour une nouvelle école créee", summary = "")
	@Tag(name = "Année scolaire")
	public Response initAnnee(@PathParam("ecoleId") Long ecoleId) {
		try {
			anneeService.initAnneeEcole(ecoleId);
			return Response.ok("Initialisation complete!").build();
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().build();
		}
	}

//	@GET
//	@Path("/test-annee-periode/")
//	@Operation(description = "Initialiser les années scolaires pour une nouvelle école créee", summary = "")
//	@Tag(name = "Année scolaire")
//	public Response initAnneePeriode(@QueryParam("anneeId") Long anneeId, @QueryParam("niveauId") Long niveauId) {
//		try {
//			return Response.ok(anneePeriodeService.listByAnneeAndNiveauEnseignementToCentral(anneeId, niveauId)).build();
//		} catch (RuntimeException r) {
//			r.printStackTrace();
//			return Response.serverError().build();
//		}
//	}

	@POST
	@Path("/save-update")
	@Tag(name = "Année scolaire")
	public Response saveOrDisplayAndDisplay(AnneeScolaire anneeScolaire) {
		Gson g = new Gson();
		try {
//			System.out.println(g.toJson(anneeScolaire));
			AnneeScolaire annee = anneeService.handleOpenAnnee(anneeScolaire);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}

	@POST
	@Path("/save-update-ecole")
	@Tag(name = "Année scolaire")
	public Response updateAndDisplayToEcole(AnneeScolaire anneeScolaire) {
		Gson g = new Gson();
		try {
			System.out.println(g.toJson(anneeScolaire));
			AnneeScolaire annee = anneeService.handleOpenAnneeToEcole(anneeScolaire);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}

	@POST
	@Path("/sharing")
	@Tag(name = "Année scolaire")
	public Response sharingAnneeProcess(AnneeScolaire anneeScolaire) {
		try {
			AnneeScolaire annee = anneeService.sharing(anneeScolaire);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}

	@POST
	@Path("/cloture-process")
	@Tag(name = "Année scolaire")
	public Response clotureAnneeProcess(AnneeScolaire anneeScolaire) {
		try {
			AnneeScolaire annee = anneeService.clotureAnnee(anneeScolaire);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}

	@POST
	@Path("/open")
	@Tag(name = "Année scolaire")
	public Response openAnneeProcess(AnneeScolaire anneeScolaire) {
		try {
			AnneeScolaire annee = anneeService.openAnnee(anneeScolaire);
			return Response.ok(annee).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e).build();
		}
	}

}
