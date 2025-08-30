package com.vieecoles.ressource.operations.personnel;

import com.vieecoles.entities.operations.personnel_matiere;
import com.vieecoles.projection.personnel_matiereSelect;
import com.vieecoles.services.eleves.EleveService;
import com.vieecoles.services.personnels.PersonnelMatiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/matiere-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class personnelMatiereRessource {
    @Inject
    EleveService EleveService ;
    @Inject
    EntityManager em;

    @Inject
    PersonnelMatiereService personnelMatiereService ;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<personnel_matiereSelect> getAllPersonnelMatiere() {
        return personnelMatiereService.getAllListMatiereClasseProfesseur() ;
    }

    @GET
    @Path("/{identifiantPersonnel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<personnel_matiereSelect> getMatiereClasseByPersonnel(@PathParam("identifiantPersonnel") Long  identifiant) {
        return personnelMatiereService.getMatiereByProfesseur(identifiant) ;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Long   CreerPersonnelMatiereClasse(personnel_matiere personnelmatiere) {
        return personnelMatiereService.CreerPersonnelMatiere(personnelmatiere) ;
    }


    @DELETE
    @Path("/{identifiantPersonnelMatiereClasse}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletePersonnelMatiereClasse(@PathParam("identifiantPersonnelMatiereClasse") Long identifiantPersonnelMatiere) {
        personnelMatiereService.deleteLineMatiereByProfesseur(identifiantPersonnelMatiere);
    }



}
