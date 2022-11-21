package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dao.entities.Annee_scolaire;
import com.vieecoles.dao.entities.operations.message_personnel;
import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.services.annee_scolaireService;
import com.vieecoles.services.souscription.MessagePersonnelService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/message-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class messageRessource {
    @Inject
    MessagePersonnelService messagePersonnelService ;

    @GET
    @Path("ecole/{id_ecole}")
    public List<message_personnel> listMessageByEcole(@PathParam("id_ecole") Long id_ecole) {
        return messagePersonnelService.getAllMessageByEcole(id_ecole) ;
    }
    @GET
    @Path("administrateur/{id_administrateur}")
    public List<message_personnel> listMessageByAdministrateur(@PathParam("id_administrateur") Long id_administrateur) {
        return messagePersonnelService.getAllMessageByAdministrateur(id_administrateur) ;
    }

    @POST
     public String  createMessagePersonnel(message_personnelDto messageDto) {
        return messagePersonnelService.creeerMessage(messageDto) ;
    }
    @DELETE
    @Path("/{id}")

    public void delete(@PathParam("id") Long id) {
        messagePersonnelService.deleteMessage(id);
    }



}
