package com.vieecoles.ressource.operations.personnel;

import com.vieecoles.entities.operations.personnel_matiere_classe;
import com.vieecoles.projection.personnel_matiere_classeSelect;
import com.vieecoles.services.eleves.EleveService;
import com.vieecoles.services.personnels.PersonnelMatiereClasseService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Tag(name = "matire-classe-personnel", description = "matire-classe-personnel ressources")
@Path("/matire-classe-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class personnelMatiereClasseRessource {
    @Inject
    EleveService EleveService ;
    @Inject
    EntityManager em;

    @Inject
    PersonnelMatiereClasseService personnelMatiereClasseService ;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("ecole-annee/{tenantId}/{anneeId}")
    public List<personnel_matiere_classe> getAllPersonnelMatiereClasse(@PathParam("tenantId") String tenantId  ,@PathParam("anneeId") Long anneeId ) {
      //  return personnelMatiereClasseService.getAllListMatiereClasseProfesseur() ;
        return personnelMatiereClasseService.ListeParEcoleAnnee(tenantId,anneeId) ;
    }

    @GET
    @Path("/{identifiantPersonnel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<personnel_matiere_classeSelect> getMatiereClasseByPersonnel(@PathParam("identifiantPersonnel") Long  identifiant) {
        return personnelMatiereClasseService.getMatiereClasseByProfesseur(identifiant) ;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{personnelId}/{classeId}/{MatiereId}/{anneeId}/{tenantId}")
    public Long   CreerPersonnelMatiereClasse(@PathParam("personnelId") Long personnelId,@PathParam("classeId") Long classeId,@PathParam("MatiereId")Long MatiereId,@PathParam("anneeId")Long anneeId,@PathParam("tenantId")String tenantId) {
        return personnelMatiereClasseService.CreerPersonnelMatiereClasse(personnelId,classeId,MatiereId,anneeId,tenantId) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("verifier-existe/{personnelId}/{classeId}/{MatiereId}/{anneeId}/{tenantId}")
    public String   verifierExistence(@PathParam("personnelId") Long personnelId,@PathParam("classeId") Long classeId,@PathParam("MatiereId")Long MatiereId,@PathParam("anneeId")Long anneeId,@PathParam("tenantId")String tenantId) {
        return personnelMatiereClasseService.checkpersonnel_matiere_classe(personnelId,classeId,MatiereId,anneeId,tenantId) ;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{entityId}/{classeId}/{MatiereId}")
    public Long   ModifierersonnelMatiereClasse(@PathParam("entityId") Long entityId,@PathParam("classeId") Long classeId,@PathParam("MatiereId")Long MatiereId) {
        return personnelMatiereClasseService.ModifierPersonnelMatiereClasse(entityId,classeId,MatiereId) ;
    }

    @DELETE
    @Path("/{identifiantPersonnelMatiereClasse}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletePersonnelMatiereClasse(@PathParam("identifiantPersonnelMatiereClasse") Long identifiantPersonnelMatiereClasse) {
        personnelMatiereClasseService.deleteLineMatiereClasseByProfesseur(identifiantPersonnelMatiereClasse);
    }



}
