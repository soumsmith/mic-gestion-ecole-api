package com.vieecoles.ressource.operations.personnel;

import com.vieecoles.entities.operations.emploi_du_temps_professeur;
import com.vieecoles.projection.emploi_du_temps_professeurSelect;
import com.vieecoles.services.eleves.EleveService;
import com.vieecoles.services.personnels.emploi_du_temps_professeurService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/emploi-du-temps-professeur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class emploiDuTempsRessource {
    @Inject
    EleveService EleveService ;
    @Inject
    EntityManager em;

    @Inject
    emploi_du_temps_professeurService emploi_du_temps_professeurService ;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<emploi_du_temps_professeurSelect> getAllPersonnelMatiereClasse() {
        return emploi_du_temps_professeurService.getEmploiDuTempsProfesseur() ;
    }

    @GET
    @Path("/{identifiantPersonnel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<emploi_du_temps_professeurSelect> getMatiereClasseByPersonnel(@PathParam("identifiantPersonnel") Long  identifiant) {
        return emploi_du_temps_professeurService.getEmploiDutempsByProfesseur(identifiant) ;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public Long   CreerEmploiDuTemps(emploi_du_temps_professeur emploi_du_temps_professeur) {
        return emploi_du_temps_professeurService.CreerEmploiDutempsProfesseur(emploi_du_temps_professeur) ;
    }


    @DELETE
    @Path("/{identifiantPersonnelMatiereClasse}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletePersonnelMatiereClasse(@PathParam("identifiantPersonnelMatiereClasse") Long identifiantPersonnelMatiereClasse) {
        emploi_du_temps_professeurService.deleteEmploiDuTempsProfesseur(identifiantPersonnelMatiereClasse);
    }



}
