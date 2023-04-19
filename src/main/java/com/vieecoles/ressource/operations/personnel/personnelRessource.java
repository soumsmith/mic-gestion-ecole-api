package com.vieecoles.ressource.operations.personnel;

import com.vieecoles.dto.personnelDto;
import com.vieecoles.entities.operations.personnel;
import com.vieecoles.projection.InfosConnexionSelect;
import com.vieecoles.services.eleves.EleveService;
import com.vieecoles.services.personnels.PersonnelService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class personnelRessource {
    @Inject
    EleveService EleveService ;
    @Inject
    EntityManager em;

    @Inject
    PersonnelService personnelService ;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("par-fonction/{Libellefonction}/{idtenant}")
    public List<personnel> getAllPersonnelParFonction(@PathParam("Libellefonction") String   fonction ,@PathParam("idtenant") String   tenant ) {
        return personnelService.getPersonnels2(fonction,tenant) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{idtenant}")
    public List<personnel> getAllPersonnel(@PathParam("idtenant") String   tenant ) {
        return personnelService.getPersonnels(tenant) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/infos-connexion-personnels/{idtenant}")
    public List<InfosConnexionSelect> getInfosConnexionPer(@PathParam("idtenant") Long   tenant) {
        return personnelService.getConnexionInfosByEcole(tenant);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/infos-connexion-personnels")
    public List<InfosConnexionSelect> getInfosConnexionPer() {
        return personnelService.getConnexionInfos() ;
    }





    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Long   CreerPersonnel(personnel mpersonnel) {
        mpersonnel.persist();
        return mpersonnel.getPersonnelid() ;

   // return    personnelService.CreerPersonnel(personnelDto) ;

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public personnel UpdatePersonnel(personnelDto personnelDto) {
     return    personnelService.modifierPersonnel(personnelDto) ;
    }

    @DELETE
    @Path("/{identifiantPersonnel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public void deletePersonnel(@PathParam("identifiantPersonnel") Long identifiantPersonnel) {
       personnelService.deletePersonnel(identifiantPersonnel);
    }



}
