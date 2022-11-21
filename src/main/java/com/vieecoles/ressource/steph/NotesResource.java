package com.vieecoles.ressource.steph;

import com.vieecoles.dto.MoyenneEleveDto;
import com.vieecoles.dao.entities.Notes;

import com.vieecoles.services.stephServi.NoteService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/notes")
public class NotesResource {

    @Inject
    NoteService noteService ;


    @GET
    @Path("/list")
    @Tag(name = "Notes")
    public Response list() {
        return Response.ok().entity(noteService.getList()).build();
    }


    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir la classe par son id", summary = "")
	@Tag(name = "Notes")
    public Notes get(@PathParam("id") long id) {
    	Notes note = noteService.findById(id);
    	if(note == null) {
    		System.out.println("Note non trouvee avec id = "+id);
    		return new Notes();
    	} else {
    		return note;
    	}
    }

    @GET
    @Path("/list-about-evaluation/{evaluation}")
    @Operation(description = "Obtenir les notes des eleves d une classe ", summary = "")
	@Tag(name = "Notes")
    public List<Notes> getAboutEval(@PathParam("evaluation") String code) {

    	System.out.println("reçu!!!"+code);
    	return noteService.getNotesClasse(code);
    }

    @GET
    @Path("/list-classe-notes/{classe}/{annee}/{periode}")
    @Operation(description = "Obtenir les notes des eleves d une classe par periode ", summary = "")
	@Tag(name = "Notes")
    public List<MoyenneEleveDto> getNotesByClasseAndPeriode(@PathParam("classe") String classe,@PathParam("annee") String annee, @PathParam("periode") String periode) {
    	return noteService.moyennesAndNotesHandle(classe, annee, periode);
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Classe")
    public Response create(Notes notes) {
    	try {
    		System.out.println("Saving ...");
    		noteService.create(notes);
    	} catch(Exception e) {
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
    	String evalCode="";
    	try {
    		noteService.handleNotes(notes);
    		if(notes!=null && notes.size()!=0) {
    			evalCode = notes.get(0).getEvaluation().getCode();
    		}
    	} catch(Exception e) {
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
    		System.out.println("Saving and display ...");
    		noteService.create(notes);
    	} catch(Exception e) {
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
		if(n==null) {
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
    	}catch(Exception ex) {
    		System.out.println("Erreur create many - Notes");
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
		if(n==null) {
			throw new NotFoundException();
		}
//		System.out.println(new Gson().toJson(c));
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
			return Response.ok("Suppression de Notes id = "+id).build();
		}catch(RuntimeException re){
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}


    }

}
