package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.services.AnneeService;
import com.vieecoles.steph.services.EcoleService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/annee")
public class AnneeResource {
	@Inject
	AnneeService anneeService;
	
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
	@Path("/list-to-ecole")
	@Operation(description = "Obtenir la liste des années pour une école", summary = "")
	@Tag(name = "Année scolaire")
	public Response listCentral(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(anneeService.getListByEcole(ecoleId)).build();
	}
	
	@GET
	@Path("/list-opened-or-closed-to-ecole")
	@Operation(description = "Obtenir la liste des années centrales au moins ouvertes pour une école", summary = "")
	@Tag(name = "Année scolaire")
	public Response listEcole(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(anneeService.getListOpenOrCloseByEcole(ecoleId)).build();
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
