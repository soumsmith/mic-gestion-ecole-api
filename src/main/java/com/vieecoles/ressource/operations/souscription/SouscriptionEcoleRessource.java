package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dto.ecoleDto2;
import com.vieecoles.dto.sous_attent_ecoleDto;
import com.vieecoles.dto.souscriptionEcoleDto;
import com.vieecoles.dto.souscriptionValidationDto;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.sousc_atten_etabliss;
import com.vieecoles.services.personnels.PersonnelService;
import com.vieecoles.services.souscription.SousceecoleService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Tag(name = "Souscription-Ecole", description = "mes Souscriptions-Ecole")
@Path("/souscription-ecole")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SouscriptionEcoleRessource {
    @Inject
    SousceecoleService souscPersonnelService ;
    @Inject
    EntityManager em;

    @Inject
    PersonnelService personnelService ;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<sousc_atten_etabliss> getAllSouscription() {
        return souscPersonnelService.getAllSouscriptionPersonnels() ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("email/{emailEtablissement}")
    public sousc_atten_etabliss getSouscriptionByEmail(@PathParam("emailEtablissement") String emailEtablissement) {
        return souscPersonnelService.getSouscripByEmail2(emailEtablissement) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("demande-ecoles-fondateur/{idFondat}")
    public List<ecoleDto2> getEcoleFondateur(@PathParam("idFondat") Long idFondat) {
        return souscPersonnelService.getAllEcoleBySouscripFondateur(idFondat) ;
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("allSouscriptionEcoles/{status}")
    public List<souscriptionEcoleDto> getAllSouscriptionEcoleS(@PathParam("status") String status) {
        Inscriptions.status status1= Inscriptions.status.valueOf(status);
        return souscPersonnelService.listTousLesSouscrEcole(status1) ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("allSouscriptionEcoles-fondateur/{idsouscrip}")
    public List<souscriptionEcoleDto> getAllSouscriptionEcoleSParFondateur(@PathParam("idsouscrip") Long idsouscrip) {
       // Inscriptions.status status1= Inscriptions.status.valueOf(status);
        return souscPersonnelService.listTousLesSouscrEcoleParFondateur(idsouscrip);
    }


    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/valider-ecoles/")
        public Response validerSouscription(souscriptionValidationDto sousValid) {
        souscPersonnelService.creerEtValiderEcole(sousValid) ;
        //souscPersonnelService.validerSouscriptionEcole(sousValid);
        return   Response.ok(String.format("Inscription  %s mis à jour",sousValid.getStatuts())).build();
    }



    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    
    @Path("creer-ecole-by-souscription/{idsouscrip}")
    public void souscriptionEtablissementCreer(@PathParam("idsouscrip")Long idsouscrip){
      souscPersonnelService.creerEcoleBySouscrip(idsouscrip) ;
    }

    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("souscription-etablissement")
    public String souscriptionEtablissement(@QueryParam("fonctionId") Long fonctionId ,@QueryParam("nom")String nom,@QueryParam("prenom") String prenom,@QueryParam("contact1") String contact1 ,@QueryParam("contact2")String contact2 ,@QueryParam("email")String email ,@QueryParam("passWord")String passWord ,List<sous_attent_ecoleDto> listsouscr ){
     return   souscPersonnelService.creerSouscripEtablissement(fonctionId,nom,prenom ,contact1,contact2,email,passWord,listsouscr) ;
    }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String   CreerSouscription(sous_attent_ecoleDto mySouscrip) {
    return     souscPersonnelService.CreerSousCrietabliss(mySouscrip) ;

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public sousc_atten_etabliss UpdateSouscripEcole(sous_attent_ecoleDto mySouscrip) {
     return    souscPersonnelService.modifierSousEcolel(mySouscrip) ;
    }

    @DELETE
    @Path("/{identifiantSouscrip}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public void deletePersonnel(@PathParam("identifiantSouscrip") Long identifiantSouscrip) {
        souscPersonnelService.deleteSousCriptionPersonnel(identifiantSouscrip);
    }



}
