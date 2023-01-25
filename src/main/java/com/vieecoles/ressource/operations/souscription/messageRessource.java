package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.entities.operations.message_personnel;
import com.vieecoles.projection.MessageSelect;
import com.vieecoles.services.souscription.MessagePersonnelService;
import com.vieecoles.services.souscription.PanierPersonnelService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/message-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class messageRessource {
    @Inject
    MessagePersonnelService messagePersonnelService ;
    @Inject
    PanierPersonnelService panierPersonnelService ;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("ecole/{id_ecole}")
    public List<MessageSelect> listMessageByEcole(@PathParam("id_ecole") Long id_ecole) {
        return messagePersonnelService.messageByEcole(id_ecole) ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("administrateur/{id_administrateur}")
    public List<message_personnel> listMessageByAdministrateur(@PathParam("id_administrateur") Long id_administrateur) {
        return messagePersonnelService.getAllMessageByAdministrateur(id_administrateur) ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personnel/{id_personnel}")
    public List<message_personnel> listMessageByPersonnel(@PathParam("id_personnel") Long id_personnel) {
        return messagePersonnelService.getAllMessageByPersonnel(id_personnel);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personnel-connectes/{id_personnel}")
    public List<MessageSelect> listMessageByPersonnelconnectes(@PathParam("id_personnel") Long id_personnel) {
        return messagePersonnelService.messageByUser(id_personnel) ;
    }



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{identifiantPanier}")
     public String  createMessagePersonnel(@PathParam("identifiantPanier") Long identifiantPanier,message_personnelDto messageDto) {
       String message;

        message=messagePersonnelService.creeerMessage(messageDto) ;
        panierPersonnelService.validerPanier(identifiantPanier);
        return  message;
    }
    @DELETE
    @Path("/{id}")

    public void delete(@PathParam("id") Long id) {
        messagePersonnelService.deleteMessage(id);
    }



}
