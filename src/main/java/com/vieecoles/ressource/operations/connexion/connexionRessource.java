package com.vieecoles.ressource.operations.connexion;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.dto.AffecterProfilUtilisateurDto;
import com.vieecoles.dto.CandidatConnexionDto;
import com.vieecoles.dto.EleveDto;
import com.vieecoles.dto.connexionDto;
import com.vieecoles.dto.modifierMotPassDto;
import com.vieecoles.dto.parametreConnexion;
import com.vieecoles.dto.parametreInfo;
import com.vieecoles.dto.personnelConnexionDto;
import com.vieecoles.dto.utilisateur_has_personnelDto;
import com.vieecoles.entities.profil;
import com.vieecoles.entities.utilisateur;
import com.vieecoles.services.profilService;
import com.vieecoles.services.connexion.connexionService;
import com.vieecoles.services.eleves.InscriptionService;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

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

    @GET
    @Path("/eleveDto")
    @Produces(MediaType.APPLICATION_JSON)
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
    public String getEmailUtilisateurByIdPersonn( @PathParam("idPersonnel") Long idPersonnel )
    {
        return  myconnexionService.getEmailSouscripteur(idPersonnel);
    }



    @GET
    @Path("/{emailUtilisateur}")
    @Produces(MediaType.APPLICATION_JSON)
    public utilisateur checkEmailUtilisateur( @PathParam("emailUtilisateur") String emailUtilisateur )
    {
        return  myconnexionService.verifiEmailUtilisateur(emailUtilisateur);
    }

    @GET
    @Path("check-pseudo/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public String checkLogin( @PathParam("login") String login )
    {  String mess;
        mess=  myconnexionService.pseudo(login);
        if(mess!=null){
            mess="Ce login est déjà utilisé!";
        } else {
            mess="Login disponible!";
        }
        System.out.println("mess "+mess);
        return  mess  ;
    }

    @POST
    @Path("/se-connecter")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String seConnecter( connexionDto myConnexionDto )
    {
        String login ,motPasse;
        Long profilId,EcoleId ;
        login= myConnexionDto.getLogin().trim();
        motPasse= myConnexionDto.getMotdePasse().trim() ;
        profilId= myConnexionDto.getProfilid() ;
        EcoleId= myConnexionDto.getEcoleid();
//        System.out.println("Info-compte "+login +" "+motPasse+" "+ profilId+" "+ EcoleId);
        return  myconnexionService.seConnecter(login,motPasse,profilId,EcoleId) ;
    }

    @POST
    @Path("/se-connecter-admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String seConnecterAdmin( connexionDto myConnexionDto )
    {
        String login ,motPasse;
        Long profilId ;
        profil myProfil= new profil() ;
        myProfil = profilservice.getIdProfilAdmin("Admin");
        login= myConnexionDto.getLogin().trim();
        motPasse= myConnexionDto.getMotdePasse().trim() ;
        profilId= myProfil.getProfilid() ;

//        System.out.println("Info-compte "+login +" "+motPasse+" "+ profilId);
        return  myconnexionService.seConnecterAdmin(login,motPasse,profilId) ;
    }


    @GET
    @Path("/checkPassword")
    @Produces(MediaType.TEXT_PLAIN)
    //@Consumes(MediaType.APPLICATION_JSON)
    public String checkPassword(@QueryParam("login") String login ,@QueryParam("motDepasse") String motDepasse )
    {
        String messageRetour=null;
        utilisateur myUtilis = new utilisateur() ;

        myUtilis = myconnexionService.checkPassword(login,motDepasse) ;

        if(myUtilis!=null){
            messageRetour= "Mot de passe correct!";
        } else {
            messageRetour= "Mot de passe incorrect!";
        }
        return  messageRetour;
    }

    @GET
    @Path("infos-personnel-connecte/{login}/{idEcole}")
    @Produces(MediaType.APPLICATION_JSON)
    public personnelConnexionDto infoPersonnConnect(@PathParam("login") String login ,@PathParam("idEcole") Long idEcole)
    {
        Long idUtilisateur ;
        idUtilisateur = myconnexionService.getIdUtilisateur(login) ;
        personnelConnexionDto myPersonn= new personnelConnexionDto();
        System.out.println("idUtilisateurxxx "+ idUtilisateur);

        if(idUtilisateur!=0L) {
            myPersonn =  myconnexionService.infosUtilisateurConnecte(login,idEcole) ;
        }
      return myPersonn ;
    }

    @GET
    @Path("infos-personnel-connecte-v2/{login}/{idEcole}/{profil}")
    @Produces(MediaType.APPLICATION_JSON)
    public personnelConnexionDto infoPersonnConnectV2(@PathParam("login") String login ,@PathParam("idEcole") Long idEcole,@PathParam("profil") Long profilId)
    {
        Long idUtilisateur ;
        idUtilisateur = myconnexionService.getIdUtilisateur(login) ;
        personnelConnexionDto myPersonn= new personnelConnexionDto();
        System.out.println("idUtilisateurxxx "+ idUtilisateur);

        if(idUtilisateur!=0L) {
            myPersonn =  myconnexionService.infosUtilisateurConnecteV2(login,idEcole,profilId) ;
        }
      return   myPersonn ;
    }


    @GET
    @Path("infos-personnel-connecte-candidat/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public CandidatConnexionDto infoPersonnConnectCandidat(@PathParam("login") String login )
    {
        Long idUtilisateur ;
        idUtilisateur = myconnexionService.getIdUtilisateur(login) ;
        CandidatConnexionDto myPersonn= new CandidatConnexionDto();


        if(idUtilisateur!=0L) {
            myPersonn =  myconnexionService.infosCandidatConnecte(login) ;
        }
        return   myPersonn ;
    }

    @GET
    @Path("parametreLogin/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public parametreInfo infoParam(@PathParam("login") String login )
    {
        parametreInfo parm = new parametreInfo() ;

        parametreConnexion   myPersonn = new parametreConnexion() ;
        myPersonn=  myconnexionService.getInfoParametreConn(login) ;

        if(myPersonn!=null) {
            parm.setMessage("utilisateur trouve");
            parm.setParametre(myPersonn);
        } else {
            parm.setMessage("utilisateur  non trouve");
        }

        return   parm ;
    }

    @GET
    @Path("id-utilisateur-connecte/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public Long idPersonnConnect(@PathParam("login") String login )
    {
        Long idUtilisateur ;
        idUtilisateur = myconnexionService.getIdUtilisateur(login) ;

        return   idUtilisateur ;
    }

    @GET
    @Path("id-utilisateur-connecte-v2/")
    @Produces(MediaType.APPLICATION_JSON)
    public Long idPersonnConnectQueryParam(@QueryParam("login") String login )
    {
        Long idUtilisateur ;
        idUtilisateur = myconnexionService.getIdUtilisateur(login) ;

        return   idUtilisateur ;
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
