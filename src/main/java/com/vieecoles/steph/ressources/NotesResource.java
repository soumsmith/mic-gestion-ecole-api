package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.dto.moyennes.EleveDto;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.services.NoteService;

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

@Path("/notes")
public class NotesResource {

	@Inject
	NoteService noteService;

	// Logger logger = Logger.getLogger(NotesResource.class.getName());

	@GET
	@Path("/list")
	@Tag(name = "Notes")
	public Response list() {
		return Response.ok().entity(noteService.getList()).build();
	}

	@GET
	@Path("/list-note-classe")
	@Tag(name = "Notes")
	public Response listByClasse(@QueryParam("classeId") String classeId, @QueryParam("anneeId") String anneeId,
			@QueryParam("periodeId") String periodeId) {
		System.out.println(String.format("%s %s %s", classeId, anneeId, periodeId));
		return Response.ok().entity(noteService.process(classeId, anneeId, periodeId)).build();
	}

	@GET
	@Path("/{id}")
	@Operation(description = "Obtenir la classe par son id", summary = "")
	@Tag(name = "Notes")
	public Notes get(@PathParam("id") long id) {
		Notes note = noteService.findById(id);
		if (note == null) {
			// logger.info("Note non trouvee avec id = "+id);
			return new Notes();
		} else {
			return note;
		}
	}

	@GET
	@Path("/get-eleve-dto/{id}")
	@Operation(description = "Obtenir eleve par son id", summary = "")
	@Tag(name = "Notes")
	public Response getEleve(@PathParam("id") long id) {
		EleveDto eleve = noteService.getEleveByClasseEleve(id);
		if (eleve == null) {
			// logger.info("Note non trouvee avec id = "+id);
			eleve = new EleveDto();
		}
		return Response.ok(eleve).build();
	}

	@GET
	@Path("/list-about-evaluation/{evaluation}")
	@Operation(description = "Obtenir les notes des eleves d une classe ", summary = "")
	@Tag(name = "Notes")
	public List<Notes> getAboutEval(@PathParam("evaluation") String code) {

		// logger.info("reçu!!!"+code);
		return noteService.getNotesClasse(code);
	}

	@GET
	@Path("/list-classe-notes/{classe}/{annee}/{periode}")
	@Operation(description = "Obtenir les notes et moyenne des eleves d une classe par periode ", summary = "")
	@Tag(name = "Notes")
	public Response getNotesByClasseAndPeriode(@PathParam("classe") String classe, @PathParam("annee") String annee,
			@PathParam("periode") String periode) {
		List<MoyenneEleveDto> medtos = new ArrayList<MoyenneEleveDto>();

		try {
			medtos = noteService.moyennesAndNotesHandle(classe, annee, periode);
		} catch (RuntimeException r) {
			r.printStackTrace();
			return Response.serverError().entity(r).build();
		}
//    	return  Response.ok(medtos).build();
		return Response.ok(noteService.buildListMoyenneEleve(medtos)).build();
	}

	@GET
	@Path("/list-notes-eleve-by-periode/{matricule}/{classe}/{annee}/{periode}")
	@Operation(description = "Obtenir les notes d'un eleve d une classe par periode ", summary = "")
	@Tag(name = "Notes")
	public Response getNotesByClasseAndPeriode(@PathParam("matricule") String matricule,
			@PathParam("classe") Long classe, @PathParam("annee") Long annee, @PathParam("periode") Long periode) {
		return Response
				.ok(noteService.getListNotesByEleveAndClasseAndAnneeAndPeriode(matricule, classe, annee, periode))
				.build();
	}

	@GET
	@Path("/list-notes-eleve-by-periode-dto/{matricule}/{classe}/{annee}/{periode}")
	@Operation(description = "Obtenir les notes d'un eleve d une classe par periode via le dto", summary = "")
	@Tag(name = "Notes")
	public Response getNotesDtoByClasseAndPeriode(@PathParam("matricule") String matricule,
			@PathParam("classe") Long classe, @PathParam("annee") Long annee, @PathParam("periode") Long periode) {
		return Response.ok(noteService.getNotesEleveByPeriode(matricule, classe, annee, periode)).build();
	}

	@GET
	@Path("/list-notes-eleve-by-periode/{matricule}/{classe}/{annee}/{periode}/{matiere}")
	@Operation(description = "Obtenir les notes d'un eleve d une classe par periode ", summary = "")
	@Tag(name = "Notes")
	public Response getNotesByClasseAndPeriode(@PathParam("matricule") String matricule,
			@PathParam("classe") Long classe, @PathParam("annee") Long annee, @PathParam("periode") Long periode,
			@PathParam("matiere") Long matiere) {
		return Response.ok(noteService.getListNotesByEleveAndClasseAndAnneeAndPeriodeAndMatiere(matricule, classe,
				annee, periode, matiere)).build();
	}

	/**
	 * Cette méthode donne les notes et moyennes des élèves d'une classe pour une
	 * matière
	 * 
	 * @param classe
	 * @param matiere
	 * @param annee
	 * @param periode
	 * @return
	 */

	@GET
	@Path("/list-classe-matiere-notes/{classe}/{matiere}/{annee}/{periode}")
	@Operation(description = "Obtenir les notes des eleves d une classe par periode ", summary = "")
	@Tag(name = "Notes")
	public List<MoyenneEleveDto> getNotesByClasseAndMatiereAndPeriode(@PathParam("classe") String classe,
			@PathParam("matiere") String matiere, @PathParam("annee") String annee,
			@PathParam("periode") String periode) {
		return noteService.moyennesAndMatiereAndNotesHandle(classe, matiere, annee, periode);
	}

	/**
	 * Obtenir les notes et la moyenne d'un eleve pour une matiere.
	 * 
	 * @param matricule
	 * @param matiere
	 * @param annee
	 * @param periode
	 * @return
	 */
	@GET
	@Path("/list-matricule-matiere-notes/{matricule}/{matiere}/{annee}/{periode}")
	@Operation(description = "Obtenir les notes des eleves d une classe par periode ", summary = "")
	@Tag(name = "Notes")
	public List<MoyenneEleveDto> getNotesByMatriculeAndMatiereAndPeriode(@PathParam("matricule") String matricule,
			@PathParam("matiere") String matiere, @PathParam("annee") String annee,
			@PathParam("periode") String periode) {
		List<MoyenneEleveDto> list = new ArrayList<>();
		list.add(noteService.moyennesAndMatiereAndNotesByMatriculeHandle(matricule, matiere, annee, periode));
		return list;
	}

	@GET
	@Path("/list-matricule-notes-moyennes/{matricule}/{annee}/{periode}")
	@Operation(description = "Obtenir les notes des eleves d une classe par periode ", summary = "")
	@Tag(name = "Notes")
	public List<MoyenneEleveDto> getNotesByMatriculeAndPeriode(@PathParam("matricule") String matricule,
			@PathParam("annee") String annee, @PathParam("periode") String periode) {
		List<MoyenneEleveDto> list = new ArrayList<>();
		list.add(noteService.moyennesAndNotesByMatriculeHandle(matricule, annee, periode));
		return list;
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Classe")
	public Response create(Notes notes) {
		try {
			// logger.info("Saving ...");
			noteService.create(notes);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erreur de creation").build();
		}
		return Response.ok("Enregistrement reussi").build();
	}

	@POST
	@Path("/handle-notes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Classe")
	public Response handleNotes(List<Notes> notes) {
		String evalCode = "";
		try {
			noteService.handleNotes(notes);
			if (notes != null && notes.size() != 0) {
				evalCode = notes.get(0).getEvaluation().getCode();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(new String("Erreur de creation")).build();
		}
		return Response.ok(noteService.getNotesClasse(evalCode)).build();
	}

	@POST
	@Path("/saveAndDisplay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Notes")
	public Response createAndDisplay(Notes notes) {
		try {
			// logger.info("Saving and display ...");
			noteService.create(notes);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(new Notes()).build();
		}
		return Response.ok().entity(noteService.findById(notes.getId())).build();
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Notes")
	public Notes update(Notes notes) {

		Notes n = noteService.update(notes);
		if (n == null) {
			throw new NotFoundException();
		}
		return n;
	}

	@POST
	@Path("/create-many")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Notes")
	public Response createMany(List<Notes> notes) {
		try {
			noteService.createMany(notes);
		} catch (Exception ex) {
			// logger.info("Erreur create many - Notes");
			ex.printStackTrace();
			return Response.serverError().entity("Erreur create many - Notes").build();
		}

		return Response.ok("Many create Note - ok").build();

	}

	@POST
	@Path("/update-display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Tag(name = "Notes")
	public Response updateAndDisplay(Notes notes) {

		Notes n = noteService.updateAndDisplay(notes);
		if (n == null) {
			throw new NotFoundException();
		}
//		logger.info(new Gson().toJson(c));
		return Response.ok().entity(n).build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Tag(name = "Notes")
	public Response delete(@PathParam("id") String id) {
		try {
			noteService.delete(id);
			return Response.ok("Suppression de Notes id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}

	}

}
