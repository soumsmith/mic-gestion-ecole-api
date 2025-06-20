package com.vieecoles.steph.ressources;

import com.vieecoles.steph.dto.MoyenneEleveDto;
import com.vieecoles.steph.entities.Notes;
import com.vieecoles.steph.services.NoteService;
import com.vieecoles.steph.services.NotesLoaderService;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


@Path("/notes-loader")
public class NotesLoaderResource {

    @Inject
    NotesLoaderService noteLoaderService ;

  //  Logger logger = Logger.getLogger(NotesResource.class.getName());


//    @GET
//    @Path("/list")
//    @Tag(name = "Notes")
//    public Response list() {
//        return Response.ok().entity(noteLoaderService.getList()).build();
//    }

    @GET
    @Path("/list-by-evaluation")
    @Tag(name = "NoteLoader")
    public Response listByEval(@QueryParam("evalId") Long evalId) {
        return Response.ok().entity(noteLoaderService.findByEvaluation(evalId)).build();
    }


//    @GET
//    @Path("/{id}")
//    @Operation(description = "Obtenir la classe par son id", summary = "")
//	@Tag(name = "Notes")
//    public Notes get(@PathParam("id") long id) {
//    	Notes note = noteLoaderService.findById(id);
//    	if(note == null) {
//    	//	logger.info("Note non trouvee avec id = "+id);
//    		return new Notes();
//    	} else {
//    		return note;
//    	}
//    }


}
