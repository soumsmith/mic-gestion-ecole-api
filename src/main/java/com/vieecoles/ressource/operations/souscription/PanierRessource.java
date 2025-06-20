package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.dto.panier_personnelDto;
import com.vieecoles.entities.operations.message_personnel;
import com.vieecoles.entities.operations.panier_personnel;
import com.vieecoles.projection.panier_personnelSelect;
import com.vieecoles.services.souscription.MessagePersonnelService;
import com.vieecoles.services.souscription.PanierPersonnelService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/panier-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PanierRessource {
    @Inject
    PanierPersonnelService panierPersonnelService ;



    @GET
    @Path("ecole/{id_ecole}/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public List<panier_personnelSelect> listpanier(@PathParam("id_ecole") Long id_ecole ,@PathParam("status") String status) {
        return panierPersonnelService.listpanierByEcole(id_ecole,status);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("check/{id_ecole}/{idPersonnel}")
     public  panier_personnelSelect checkPanier(@PathParam("id_ecole") Long id_ecole, @PathParam("idPersonnel") Long idPersonnel)
    {
        panier_personnelSelect myPani = new panier_personnelSelect();
        myPani = panierPersonnelService.checkPanierPers(id_ecole,idPersonnel) ;
        System.out.println("myPani "+myPani);
        return myPani ;
    }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
     public String  createPanierPersonnel(panier_personnelDto panierDto) {
        System.out.println("panierDto "+panierDto.toString());
        return panierPersonnelService.creeerPanier(panierDto);
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("validerPanier/{identifiantPanier}")
    public void  ValiderPanier(@PathParam("identifiantPanier") Long identifiantPanier) {
        panierPersonnelService.validerPanier(identifiantPanier);
    }

    @DELETE
    @Path("/{id}")

    public void delete(@PathParam("id") Long id) {
        panierPersonnelService.deletePanier(id);
    }



}
