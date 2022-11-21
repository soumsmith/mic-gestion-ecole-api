package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dao.entities.operations.message_personnel;
import com.vieecoles.dao.entities.operations.panier_personnel;
import com.vieecoles.dto.message_personnelDto;
import com.vieecoles.dto.panier_personnelDto;
import com.vieecoles.services.souscription.MessagePersonnelService;
import com.vieecoles.services.souscription.PanierPersonnelService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/panier-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PanierRessource {
    @Inject
    PanierPersonnelService panierPersonnelService ;

    @GET
    @Path("ecole/{id_ecole}")
    public List<panier_personnel> listpanierByEcole(@PathParam("id_ecole") Long id_ecole) {
        return panierPersonnelService.getAllpanierByEcole(id_ecole) ;
    }

    @POST
     public String  createPanierPersonnel(panier_personnelDto panierDto) {
        System.out.println("panierDto "+panierDto.toString());
        return panierPersonnelService.creeerPanier(panierDto);
    }

    @DELETE
    @Path("/{id}")

    public void delete(@PathParam("id") Long id) {
        panierPersonnelService.deletePanier(id);
    }



}
