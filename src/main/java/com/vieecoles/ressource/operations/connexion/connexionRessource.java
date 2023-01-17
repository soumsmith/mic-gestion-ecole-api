package com.vieecoles.ressource.operations.connexion;

import com.vieecoles.dao.entities.operations.Inscriptions;
import com.vieecoles.dao.entities.operations.eleve;
import com.vieecoles.dao.entities.profil;
import com.vieecoles.dao.entities.utilisateur;
import com.vieecoles.dto.*;
import com.vieecoles.services.profilService;
import com.vieecoles.services.connexion.connexionService;
import com.vieecoles.services.eleves.EleveService;
import com.vieecoles.services.eleves.InscriptionService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Mes Operations", description = "mes Operations")
@Path("/connexion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class connexionRessource {
    @Inject
    connexionService myconnexionService ;
    @Inject
    profilService  profilservice ;
    @Inject
    EntityManager em;
    @Inject
    InscriptionService inscriptionService ;

    @Inject


    @GET
    @Path("/eleveDto")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<EleveDto> list2() {
        return null ;
    }

    @POST
    @Path("/{emailUtilisateur}/{motDePasse}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String creerCompteUtlisateur(
            @PathParam("emailUtilisateur") String emailUtilisateur ,
            @PathParam("motDePasse") String motDePasse, utilisateur_has_personnelDto myUtilisaDto)
    {
        return  myconnexionService.CreerCompteUtilisateur(emailUtilisateur,motDePasse,myUtilisaDto) ;
    }

    @POST
    @Path("affecter-profil-utilisateur")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String affecterProfilUtilisateur( AffecterProfilUtilisateurDto myUtilisaDto)
    {
        System.out.println("myUtilisaDtoSize"+ myUtilisaDto.getListProfil().size());
        return  myconnexionService.affecterProfilUtilisateur(myUtilisaDto) ;
    }
    @GET
    @Path("email-par-personnel/{idPersonnel}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)

    public String getEmailUtilisateurByIdPersonn( @PathParam("idPersonnel") Long idPersonnel )
    {
        return  myconnexionService.getEmailSouscripteur(idPersonnel);
    }



    @GET
    @Path("/{emailUtilisateur}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public utilisateur checkEmailUtilisateur( @PathParam("emailUtilisateur") String emailUtilisateur )
    {
        return  myconnexionService.verifiEmailUtilisateur(emailUtilisateur);
    }

    @POST
    @Path("/se-connecter")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String seConnecter( connexionDto myConnexionDto )
    {
        String email ,motPasse;
        Long profilId,EcoleId ;
        email= myConnexionDto.getEmail().trim();
        motPasse= myConnexionDto.getMotdePasse().trim() ;
        profilId= myConnexionDto.getProfilid() ;
        EcoleId= myConnexionDto.getEcoleid();
        System.out.println("Info-compte "+email +" "+motPasse+" "+ profilId+" "+ EcoleId);
        return  myconnexionService.seConnecter(email,motPasse,profilId,EcoleId) ;
    }

    @POST
    @Path("/se-connecter-admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String seConnecterAdmin( connexionDto myConnexionDto )
    {
        String email ,motPasse;
        Long profilId ;
        profil myProfil= new profil() ;
        myProfil = profilservice.getIdProfilAdmin("Admin");
        email= myConnexionDto.getEmail().trim();
        motPasse= myConnexionDto.getMotdePasse().trim() ;
        profilId= myProfil.getProfilid() ;
       
        System.out.println("Info-compte "+email +" "+motPasse+" "+ profilId);
        return  myconnexionService.seConnecterAdmin(email,motPasse,profilId) ;
    }


    @GET
    @Path("checkPassword/{emailUtilisateur}/{motDepasse}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String checkPassword(@PathParam("emailUtilisateur") String emailUtilisateur ,@PathParam("motDepasse") String motDepasse )
    {
        String messageRetour=null;
        utilisateur myUtilis = new utilisateur() ;
        myUtilis = myconnexionService.checkPassword(emailUtilisateur,motDepasse) ;
        if(myUtilis!=null){
            messageRetour= "Mot de passe correct!";
        } else {
            messageRetour= "Mot de passe incorrect!";
        }
        return  messageRetour;
    }

    @GET
    @Path("infos-personnel-connecte/{emailUtilisateur}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public personnelConnexionDto infoPersonnConnect(@PathParam("emailUtilisateur") String emailUtilisateur )
    {
        Long idUtilisateur ;
        idUtilisateur = myconnexionService.getIdUtilisateur(emailUtilisateur) ;
        personnelConnexionDto myPersonn= new personnelConnexionDto();
        System.out.println("idUtilisateurxxx "+ idUtilisateur);

        if(idUtilisateur!=0L) {
            myPersonn =  myconnexionService.infosUtilisateurConnecte(emailUtilisateur) ;
        }
      return   myPersonn ;
    }


    @GET
    @Path("infos-personnel-connecte-candidat/{emailUtilisateur}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public CandidatConnexionDto infoPersonnConnectCandidat(@PathParam("emailUtilisateur") String emailUtilisateur )
    {
        Long idUtilisateur ;
        idUtilisateur = myconnexionService.getIdUtilisateur(emailUtilisateur) ;
        CandidatConnexionDto myPersonn= new CandidatConnexionDto();
        System.out.println("idUtilisateurxxx "+ idUtilisateur);

        if(idUtilisateur!=0L) {
            myPersonn =  myconnexionService.infosCandidatConnecte(emailUtilisateur) ;
        }
        return   myPersonn ;
    }


    @PUT
    @Path("/modifier-motDePasse")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String modifierMotPasse(modifierMotPassDto motPassDto )
    {
        String emailUtilisateur ,motDepasse , nouveauMotDepasse ;
        emailUtilisateur =  motPassDto.getLogin() ;
        motDepasse= motPassDto.getMotdePasse();
        nouveauMotDepasse =  motPassDto.getConfirmMotPass() ;
        return  myconnexionService.modifierMotPasse(emailUtilisateur,motDepasse,nouveauMotDepasse) ;
    }

    @PUT
    @Path("/desactiver-activer-profil-utilisateur")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deSactiverActiverProfilUtilsateur(@QueryParam("personnelId") Long  personnelId ,@QueryParam("ecoleId") Long ecoleId ,@QueryParam("profilId") Long profilId ,
                                             @QueryParam("active") int  active)
    {
        return  myconnexionService.deSactiverProfilUtilisateur(personnelId,ecoleId,profilId ,active) ;
    }

    @PUT
    @Path("/desactiver-activer-profil-ecole")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String desactiverActiverProfilEcole(@QueryParam("ecoleId") Long ecoleId ,@QueryParam("profilId") Long profilId , @QueryParam("active") int  active )
    {
        return  myconnexionService.deSactiverProfilEcole(ecoleId,profilId,active) ;
    }




    @PUT
    @Path("/modifier-infos-compte")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String modifierInfosCompte(utilisateur_has_personnelDto myUtilisDto)
    {
        return  myconnexionService.modifierInfosCompte(myUtilisDto.getUtilisateur_has_personid(),myUtilisDto.getEcole_ecoleid(),myUtilisDto.getPersonnel_personnelid(),myUtilisDto.getProfilid(),myUtilisDto.getUtilisateur_has_person_date_debut(),myUtilisDto.getUtilisateur_has_person_date_fin()) ;
    }


    @PUT
    @Path("/descativer-compte")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String desactiverCompte(utilisateur_has_personnelDto myUtilisDto)
    {
        return  myconnexionService.modifierInfosCompte(myUtilisDto.getUtilisateur_has_personid(),myUtilisDto.getEcole_ecoleid(),myUtilisDto.getPersonnel_personnelid(),myUtilisDto.getProfilid(),myUtilisDto.getUtilisateur_has_person_date_debut(),myUtilisDto.getUtilisateur_has_person_date_fin()) ;
    }

}
