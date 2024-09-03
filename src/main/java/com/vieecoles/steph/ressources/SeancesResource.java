package com.vieecoles.steph.ressources;

import com.google.gson.Gson;
import com.vieecoles.steph.entities.Activite;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.entities.Seances;
import com.vieecoles.steph.services.SeanceService;
import com.vieecoles.steph.util.DateUtils;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/seances")
public class SeancesResource {

	@Inject
	SeanceService seanceService;

	@GET
	@Path("/list")
	@Tag(name = "Seances")
	public Response list() {
		return Response.ok().entity(seanceService.getList()).build();
	}

	@GET
	@Path("/generate-seances")
	@Tag(name = "Seances")
	public Response generateSeances(@QueryParam("date") String date, @QueryParam("classe") String classeId,
			@QueryParam("ecole") Long ecoleId, @QueryParam("annee") Long anneeId) {
		List<Message> messages = new ArrayList<Message>();
		messages.add(new Message("Generé", "Mon titre", "Mon detail"));
		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);
		try {
			messages = seanceService.generateSeances(ourDate, classeId, ecoleId, anneeId);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(messages).build();
		}
		return Response.ok().entity(messages).build();
	}

	@GET
	@Path("/is-plage-horaire-valid")
	@Tag(name = "Seances")
	public Response isPlageHoraireValid(@QueryParam("annee") long anneeId, @QueryParam("classe") long classeId,
			@QueryParam("jour") int jourId, @QueryParam("date") String dateSeance,
			@QueryParam("heureDeb") String heureDeb, @QueryParam("heureFin") String heureFin) {
		LocalDate dateFormat = LocalDate.parse(dateSeance);

//		System.out.println(dateSeance);
//		System.out.println(dateFormat);

		return Response.ok().entity(seanceService.isPlageHoraireValid(anneeId, classeId, jourId,
				DateUtils.asDate(dateFormat), heureDeb, heureFin)).build();
	}

	@GET
	@Path("/get-list-date-classe-statut")
	@Tag(name = "Seances")
	public Response getListByClasseAndDateAndStatut(@QueryParam("date") String date,
			@QueryParam("classe") long classeId, @QueryParam("statut") String statut) {
//		System.out.println(date);

		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);
		System.out.println(ourDate);
		return Response.ok().entity(seanceService.getListByClasseAndDateAndStatut(classeId, ourDate, statut)).build();
	}

	@GET
	@Path("/get-list-date-classe")
	@Tag(name = "Seances")
	public Response getListByDateAndClasse(@QueryParam("date") String date, @QueryParam("classe") long classeId) {
		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);
		return Response.ok().entity(seanceService.getListByDateAndClasse(1, ourDate, classeId)).build();
	}

	@GET
	@Path("/get-list-date")
	@Tag(name = "Seances")
	public Response getListByDate(@QueryParam("date") String date) {
		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);

		return Response.ok().entity(seanceService.getListByDate(1, ourDate)).build();
	}

	@GET
	@Path("/get-list-date-prof")
	@Tag(name = "Seances")
	@Operation(description = "Obtenir les séances d'un professseur à une date donné (format date yyyy-mm-dd)")
	public Response getListByDateAndProf(@QueryParam("date") String date, @QueryParam("prof") long profId) {
		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);

		return Response.ok().entity(seanceService.getListByDateAndProf(1, ourDate, profId)).build();
	}

	@GET
	@Path("/get-distinct-list-date")
	@Tag(name = "Seances")
	public Response getDistinctListByDate(@QueryParam("date") String date, @QueryParam("ecole") String ecole) {
		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);
		List<Seances> seances = seanceService.getDistinctListByDate(ourDate, Long.parseLong(ecole));
		Gson gson = new Gson();
		System.out.println(gson.toJson(seances));
		return Response.ok().entity(seances).build();
	}

	@GET
	@Path("/get-list-statut")
	@Tag(name = "Seances")
	public Response getListByStatut(@QueryParam("annee") String annee, @QueryParam("statut") String statut,
			@QueryParam("ecole") Long ecole) {
		List<Seances> seances = seanceService.getListByStatut(annee, statut, ecole);
		return Response.ok().entity(seances).build();
	}

	@GET
	@Path("/get-distinct-list-date-classe")
	@Tag(name = "Seances")
	public Response getDistinctListByDateAndClasse(@QueryParam("date") String date,
			@QueryParam("classe") long classeId) {
		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);
		List<Seances> seances = seanceService.getDistinctListByDateAndClasse(ourDate, classeId);
		Gson gson = new Gson();
		System.out.println(gson.toJson(seances));
		return Response.ok().entity(seances).build();
	}

	@GET
	@Path("/count-salles-used")
	@Tag(name = "Seances")
	@Consumes(MediaType.TEXT_PLAIN)
	public Response countSallesUtiliseInSeanceByEcoleAndDate(@QueryParam("date") String date,
			@QueryParam("ecole") long ecoleId) {
		// Pattern date is yyyy-mm-dd. ex: 2023-08-10
		LocalDate ld = DateUtils.getDateWithString(date);
		Date ourDate = DateUtils.asDate(ld);
		System.out.println(ourDate);
		Long seances = seanceService.countSallesUtiliseInSeanceByEcoleAndDate(ecoleId, ourDate);
		Gson gson = new Gson();
		System.out.println(gson.toJson(seances));
		return Response.ok().entity(seances).build();
	}

	@POST
	@Path("/saveAndDisplay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Seances")
	public Response createAndDisplay(Seances seance) {
		try {
			System.out.println("Saving and display ...");
			seanceService.save(seance);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(new Activite()).build();
		}
		return Response.ok().entity(seanceService.findById(seance.getId())).build();
	}

	@POST
	@Path("/update-display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Seances")
	public Response updateAndDisplay(Seances seance) {
		System.out.println("update and display ...");
		Seances sc = seanceService.updateAndDisplay(seance);
		if (sc == null) {
			throw new NotFoundException();
		}
		return Response.ok().entity(sc).build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Seances")
	public Response delete(@PathParam("id") String id) {
		try {
			seanceService.delete(id);
			return Response.ok("Suppression seance id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}

	}

}
