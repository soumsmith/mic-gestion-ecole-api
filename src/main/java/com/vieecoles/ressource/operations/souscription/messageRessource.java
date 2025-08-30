package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.entities.operations.message_personnel;
import com.vieecoles.projection.MessageSelect;
import com.vieecoles.services.souscription.MessagePersonnelService;
import com.vieecoles.services.souscription.PanierPersonnelService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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
    @Path("boite-envoie/{id_user}")
    public List<MessageSelect> listMessageEnvoye(@PathParam("id_user") Long id_user) {
        return messagePersonnelService.messageEmetteur(id_user) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("boite-reception/{id_user}")
    public List<MessageSelect> listMessageRecu(@PathParam("id_user") Long id_user) {
        return messagePersonnelService.messageRecu(id_user) ;
    }






    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{identifiantPanier}")
     public String  createMessagePersonnel(@PathParam("identifiantPanier") Long identifiantPanier,message_personnelDto messageDto) {
       String message = null;

      //  message=messagePersonnelService.creeerMessage(messageDto) ;
        Long idRecepteur= messagePersonnelService.getGlobalIdUser(identifiantPanier) ;
        System.out.println("idRecepteur "+idRecepteur);
        messageDto.setIdDestinataire(idRecepteur);
        message=messagePersonnelService.buildMessage(messageDto) ;
        panierPersonnelService.validerPanier(identifiantPanier);
        return  message;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/envoyer-message")
    public String  sendMessage(message_personnelDto messageDto) {
        String message;

        message=messagePersonnelService.buildMessage(messageDto) ;

        return  message;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/admin-envoyer-message/{idPersonnel}")
    public String  sendAdminMessage(@PathParam("idPersonnel") Long idPersonnel ,message_personnelDto messageDto) {
        String message;
        Long idUser= messagePersonnelService.getIdUtilisateurByPersonnel(idPersonnel) ;
        messageDto.setIdDestinataire(idUser);
        message=messagePersonnelService.buildMessage(messageDto) ;
        return  message;
    }


    @DELETE
    @Path("/{id}")

    public void delete(@PathParam("id") Long id) {
        messagePersonnelService.deleteMessage(id);
    }



}
