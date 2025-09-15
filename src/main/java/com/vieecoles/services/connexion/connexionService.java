package com.vieecoles.services.connexion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vieecoles.dto.AffecterProfilUtilisateurDto;
import com.vieecoles.dto.CandidatConnexionDto;
import com.vieecoles.dto.parametreConnexion;
import com.vieecoles.dto.personnelConnexionDto;
import com.vieecoles.dto.utilisateur_has_personnelDto;
import com.vieecoles.entities.profil;
import com.vieecoles.entities.utilisateur;
import com.vieecoles.entities.utilisateur_has_personnel;
import com.vieecoles.entities.operations.ecole;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;


@ApplicationScoped
public class connexionService implements PanacheRepositoryBase<utilisateur_has_personnel, Long> {
    @Inject
    EntityManager em;



    public  ecole  findByIDecole(Long idpobj){
        return ecole.find("ecoleid",idpobj).singleResult();
    }
    @Transactional
    public String affecterProfilFondateur(Long personnelid,LocalDate Datefin, Long idEcole, Long ProfilId  ){
        utilisateur_has_personnelDto utilisatPersonDto = new utilisateur_has_personnelDto() ;
        utilisateur_has_personnel myutilisateurPerson =  new utilisateur_has_personnel() ;
        String messageRetour = null ;
        String emailUtilisateur= getEmailSouscripteur(personnelid);
        System.out.print("emailUtilisateurParPersonnel "+emailUtilisateur);
        List<String> profilNonCreer = new ArrayList<>();
        utilisateur myutilisateur2 = new utilisateur() ;
        myutilisateur2 = verifiEmailUtilisateur(emailUtilisateur) ;
        System.out.print("myutilisateurObjectParEmail "+myutilisateur2.toString());
        Long idUser = getIdUser(personnelid) ;

        myutilisateurPerson= veriCompteUtilisateurEcole(personnelid ,idEcole, ProfilId);

        if(myutilisateurPerson!=null) {
            messageRetour = "Ce utilisateur à déjà un compte pour cette ecole";
            if(!messageRetour.equals("Compte créé avec succès!")){
                profil myProfil= new profil();
                myProfil= profil.findById(ProfilId) ;
                profilNonCreer.add(myProfil.getProfil_libelle()) ;
            }
        } else {
          //  myutilisateur1= checkPassword(emailUtilisateur,motDepasse ) ;
           // System.out.println("myutilisateur1 "+myutilisateur1);
            if(myutilisateur2!=null) {
                utilisatPersonDto.setProfilid(ProfilId);
                utilisatPersonDto.setPersonnel_personnelid(personnelid);
                utilisatPersonDto.setEcole_ecoleid(idEcole);
                utilisatPersonDto.setUtilisateurid(idUser);
                utilisatPersonDto.setUtilisateur_has_person_date_debut(LocalDate.now());
                utilisatPersonDto.setUtilisateur_has_person_date_fin(Datefin);
                affeterUtilisateurProfil(utilisatPersonDto) ;
                messageRetour = "Compte créé avec succès!";
                if(!messageRetour.equals("Compte créé avec succès!")){
                    profil myProfil= new profil();
                    myProfil= profil.findById(ProfilId) ;
                    profilNonCreer.add(myProfil.getProfil_libelle()) ;
                }

            }  else {
                messageRetour = "Aucun compte n'existe pour ces paramètres de connexion!";
            }

        }


        return  messageRetour ;
    }






    public String affecterProfilUtilisateur( AffecterProfilUtilisateurDto myUtilisaDto ){

        utilisateur_has_personnelDto utilisatPersonDto = new utilisateur_has_personnelDto() ;
        utilisateur_has_personnel myutilisateurPerson =  new utilisateur_has_personnel() ;
        String messageRetour = null ;
        String emailUtilisateur= getEmailSouscripteur(myUtilisaDto.getPersonnel_personnelid());
        System.out.print("emailUtilisateurParPersonnel "+emailUtilisateur);
        List<String> profilNonCreer = new ArrayList<>();

        utilisateur myutilisateur2 = new utilisateur() ;
        myutilisateur2 = verifiEmailUtilisateur(emailUtilisateur) ;
        System.out.print("myutilisateurObjectParEmail "+myutilisateur2.toString());
        Long idUser = getIdUser(myUtilisaDto.getPersonnel_personnelid()) ;

        for (int i = 0; i < myUtilisaDto.getListProfil().size(); i++){
            System.out.println("Profil***--"+myUtilisaDto.getListProfil().get(i).getProfilid());
            myutilisateurPerson= veriCompteUtilisateurEcole(myUtilisaDto.getPersonnel_personnelid() ,myUtilisaDto.getEcole_ecoleid(), myUtilisaDto.getListProfil().get(i).getProfilid());

            if(myutilisateurPerson!=null) {
                messageRetour = "Ce utilisateur à déjà un compte pour cette ecole";
                if(!messageRetour.equals("Compte créé avec succès!")){
                    profil myProfil= new profil();
                    myProfil= profil.findById(myUtilisaDto.getListProfil().get(i).getProfilid()) ;
                    profilNonCreer.add(myProfil.getProfil_libelle()) ;
                }
            } else {
              //  myutilisateur1= checkPassword(emailUtilisateur,motDepasse ) ;
               // System.out.println("myutilisateur1 "+myutilisateur1);
                if(myutilisateur2!=null) {
                    utilisatPersonDto.setProfilid(myUtilisaDto.getListProfil().get(i).getProfilid());
                    utilisatPersonDto.setPersonnel_personnelid(myUtilisaDto.getPersonnel_personnelid());
                    utilisatPersonDto.setEcole_ecoleid(myUtilisaDto.getEcole_ecoleid());
                    utilisatPersonDto.setUtilisateurid(idUser);
                    utilisatPersonDto.setUtilisateur_has_person_date_debut(LocalDate.now());
                    utilisatPersonDto.setUtilisateur_has_person_date_fin(myUtilisaDto.getUtilisateur_has_person_date_fin());
                    affeterUtilisateurProfil(utilisatPersonDto) ;
                    messageRetour = "Compte créé avec succès!";
                    if(!messageRetour.equals("Compte créé avec succès!")){
                        profil myProfil= new profil();
                        myProfil= profil.findById(myUtilisaDto.getListProfil().get(i)) ;
                        profilNonCreer.add(myProfil.getProfil_libelle()) ;
                    }

                }  else {
                    messageRetour = "Aucun compte n'existe pour ces paramètres de connexion!";
                }

            }

        }

        if(profilNonCreer.size()>0){
            String mess="Les profils suivants ont été déjà affectés!";
            messageRetour = String.join(", ", profilNonCreer);
            messageRetour= mess+" "+ messageRetour ;
        }

        return  messageRetour ;
    }

@Transactional
  public personnelConnexionDto infosUtilisateurConnecte(String email,Long idEcole){

    Long idUtilisateur ,IdPersonnel ;
      personnelConnexionDto  myPersoDto = new personnelConnexionDto() ;
//      System.out.println("ENTREEEEEE ");

      idUtilisateur =   getIdUtilisateur(email);
//      System.out.println("idUtilisateur "+idUtilisateur);

          IdPersonnel = getIDpersonnel(idUtilisateur,idEcole) ;

//          System.out.println("IdPersonnel "+IdPersonnel);
          myPersoDto = getInfoPersonn(IdPersonnel) ;

//          System.out.println("myPersoDto "+myPersoDto);

    return myPersoDto;

}

public personnelConnexionDto infosUtilisateurConnecteV2(String email,Long idEcole, Long profil){

    Long idUtilisateur ,IdPersonnel ;
      personnelConnexionDto  myPersoDto = new personnelConnexionDto() ;
//      System.out.println("ENTREEEEEE ");

      idUtilisateur =   getIdUtilisateur(email);
//      System.out.println("idUtilisateur "+idUtilisateur);

          IdPersonnel = getIDpersonnel(idUtilisateur,idEcole) ;

//          System.out.println("IdPersonnel "+IdPersonnel);
          myPersoDto = getInfoPersonnel(IdPersonnel,profil) ;

//          System.out.println("myPersoDto "+myPersoDto);

    return myPersoDto;

}

    @Transactional
    public CandidatConnexionDto infosCandidatConnecte(String login){
        Long idUtilisateur ,IdPersonnel ;
        CandidatConnexionDto  myPersoDto = new CandidatConnexionDto() ;
//        System.out.println("ENTREEEEEE ");
        idUtilisateur =   getIdUtilisateur(login);
//        System.out.println("idUtilisateur "+idUtilisateur);
        IdPersonnel = getIDpersonnelSouscrip(login) ;
//        System.out.println("IdPersonnel "+IdPersonnel);
        myPersoDto = getInfoPersonnCandidat(IdPersonnel) ;
//        System.out.println("myPersoDto "+myPersoDto);
        return myPersoDto;
    }


    public long getIdUser(Long idPersonnel){
        Long idSouscrip = getIdSouscripteur(idPersonnel) ;
        Long idUser = getIdUtilisateurBySouscripteur(idSouscrip) ;
        return  idUser ;
    }


    @Transactional
    public long  getIdSouscripteur(Long idPersonnel){
        Long idSouscripteur = null;
        try {
            idSouscripteur= (Long) em.createQuery("select o.sous_attent_personn.sous_attent_personnid from personnel  o  where  o.personnelid =:idPersonnel ")
                    .setParameter("idPersonnel",idPersonnel)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            idSouscripteur = 0L;
        }
        return idSouscripteur ;
    }

    @Transactional
    public String  getEmailSouscripteur(Long idPersonnel){
        String emailSouscripteur = null;
        try {
            emailSouscripteur= (String) em.createQuery("select o.sous_attent_personn.sous_attent_personn_email from personnel  o  where  o.personnelid =:idPersonnel ")
                    .setParameter("idPersonnel",idPersonnel)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            emailSouscripteur = null;
        }
        return emailSouscripteur ;
    }



    @Transactional
    public long  getIdUtilisateurBySouscripteur(Long idSouscripteur){
        Long idUtilisateur = null;
        try {
            idUtilisateur= (Long) em.createQuery("select o.utilisateurid from utilisateur  o  where  o.sous_attent_personn_sous_attent_personnid =:idSouscripteur ")
                    .setParameter("idSouscripteur",idSouscripteur)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            idUtilisateur = 0L;
        }
        return idUtilisateur ;
    }




@Transactional
  public long  getIdUtilisateur(String login){
        Long IdUtilisateur = null;
      try {
          IdUtilisateur= (Long) em.createQuery("select o.utilisateurid from utilisateur  o  where  o.utilisateu_login =:login ")
                  .setParameter("login",login)
                  .getSingleResult();
      } catch (Exception e) {
    	  e.printStackTrace();
          IdUtilisateur = 0L;
      }
     return IdUtilisateur ;
       }

    @Transactional
    public long  getIDpersonnel(Long  idUtilisateur,Long idEcole){
        Long IdPersonnel = null;
        try {
            IdPersonnel= (Long) em.createQuery("select distinct  o.personnel_personnelid from utilisateur_has_personnel  o  where  o.utilisateur.utilisateurid =:idUtilisateur and o.ecole_ecoleid=:idEcole ")
                    .setParameter("idUtilisateur",idUtilisateur)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            IdPersonnel = null;
        }
        return IdPersonnel ;
    }

    @Transactional
    public long  getIDpersonnelSouscrip(String  login){
        Long IdPersonnel = null;
        try {
            IdPersonnel= (Long) em.createQuery("select distinct  o.sous_attent_personnid from sous_attent_personn  o , utilisateur  u where  o.sous_attent_personnid= u.sous_attent_personn_sous_attent_personnid  and u.utilisateu_login  =:login ")
                    .setParameter("login",login)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            IdPersonnel = null;
        }
        return IdPersonnel ;
    }



    @Transactional
    public personnelConnexionDto  getInfoPersonn(Long  idPersonnel){
        personnelConnexionDto  myPersoDto = new personnelConnexionDto() ;
        try {
            myPersoDto= (personnelConnexionDto) em.createQuery("select  new com.vieecoles.dto.personnelConnexionDto(o.personnelid,o.personnelnom,o.personnelprenom ,pl.profil_libelle) from personnel o, profil pl ,utilisateur_has_personnel  ut where o.personnelid=ut.personnel_personnelid and pl.profilid = ut.profil.profilid and  o.personnelid=:idPersonnel",personnelConnexionDto.class)
                    .setParameter("idPersonnel",idPersonnel)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
        	myPersoDto = null;
        }
        return myPersoDto ;
    }

    public personnelConnexionDto  getInfoPersonnel(Long  idPersonnel, Long profilId){
        personnelConnexionDto  myPersoDto = new personnelConnexionDto() ;
        try {
            myPersoDto= (personnelConnexionDto) em.createQuery("select  new com.vieecoles.dto.personnelConnexionDto(o.personnelid,o.personnelnom,o.personnelprenom ,pl.profil_libelle) from personnel o, profil pl ,utilisateur_has_personnel  ut where o.personnelid=ut.personnel_personnelid and pl.profilid = ut.profil.profilid and  o.personnelid=:idPersonnel and ut.profil.profilid=:profilId",personnelConnexionDto.class)
                    .setParameter("idPersonnel",idPersonnel)
                    .setParameter("profilId",profilId)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
        	myPersoDto = null;
        }
        return myPersoDto ;
    }

    @Transactional
    public parametreConnexion  getInfoParametreConn(String  email){
        parametreConnexion  myPersoDto = new parametreConnexion() ;
        try {
//        	System.out.println("email :  "+email);
            myPersoDto= (parametreConnexion) em.createQuery("select  new com.vieecoles.dto.parametreConnexion(o.utilisateu_login ,o.utilisateur_mot_de_passe) from utilisateur o where o.utilisateu_email=:email ",parametreConnexion.class)
                    .setParameter("email",email)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            myPersoDto = null;
        }
        return myPersoDto ;
    }


    @Transactional
    public CandidatConnexionDto getInfoPersonnCandidat(Long  idPersonnel){
        CandidatConnexionDto  myPersoDto = new CandidatConnexionDto() ;
        try {
            myPersoDto= (CandidatConnexionDto) em.createQuery("select  new com.vieecoles.dto.CandidatConnexionDto(o.sous_attent_personnid,o.sous_attent_personn_nom,o.sous_attent_personn_prenom,o.sous_attent_personn_email,'1',f.fonctionlibelle) from sous_attent_personn  o join o.fonction f where  o.sous_attent_personnid =:idPersonnel",CandidatConnexionDto.class)
                    .setParameter("idPersonnel",idPersonnel)
                    .getSingleResult();
        } catch (Exception e) {
            myPersoDto = null;
        }
        return myPersoDto ;
    }



    public String CreerCompteUtilisateur(String emailUtilisateur , String motDepasse, utilisateur_has_personnelDto myUtilisaDto ){
         utilisateur myutilisateur1 = new utilisateur() ;
         utilisateur_has_personnelDto utilisatPersonDto = new utilisateur_has_personnelDto() ;
         utilisateur_has_personnel myutilisateurPerson =  new utilisateur_has_personnel() ;
         String messageRetour = null ;
         List<String> profilNonCreer = new ArrayList<>();

         utilisateur myutilisateur2 = new utilisateur() ;
         myutilisateur2 = verifiEmailUtilisateur(emailUtilisateur) ;
//         System.out.print("UserBy Email "+myutilisateur2.toString());

         if(myutilisateur2!=null) {
             messageRetour = "Cette adresse email est déjà associée à un compte!";
         } else {

             for (int i = 0; i < myUtilisaDto.getListProfil().size(); i++){
                 myutilisateurPerson= veriCompteUtilisateurEcole(myUtilisaDto.getPersonnel_personnelid() ,myUtilisaDto.getEcole_ecoleid(), myUtilisaDto.getListProfil().get(i));

                 if(myutilisateurPerson!=null) {
                     messageRetour = "Ce utilisateur à déjà un compte pour cette ecole";
                     if(!messageRetour.equals("Compte créé avec succès!")){
                         profil myProfil= new profil();
                         myProfil= profil.findById(myUtilisaDto.getListProfil().get(i)) ;
                         profilNonCreer.add(myProfil.getProfil_libelle()) ;
                     }
                 } else {
                     myutilisateur1= creerUtilisateur(motDepasse ,emailUtilisateur,myUtilisaDto.getLogin()) ;

                     utilisatPersonDto.setProfilid(myUtilisaDto.getListProfil().get(i));
                     utilisatPersonDto.setPersonnel_personnelid(myUtilisaDto.getPersonnel_personnelid());
                     utilisatPersonDto.setEcole_ecoleid(myUtilisaDto.getEcole_ecoleid());
                     utilisatPersonDto.setUtilisateurid(myutilisateur1.getUtilisateurid());
                     utilisatPersonDto.setUtilisateur_has_person_date_debut(myUtilisaDto.getUtilisateur_has_person_date_debut());
                     utilisatPersonDto.setUtilisateur_has_person_date_fin(myUtilisaDto.getUtilisateur_has_person_date_fin());
                     affeterUtilisateurProfil(utilisatPersonDto) ;
                     messageRetour = "Compte créé avec succès!";
                     if(!messageRetour.equals("Compte créé avec succès!")){
                         profil myProfil= new profil();
                         myProfil= profil.findById(myUtilisaDto.getListProfil().get(i)) ;
                         profilNonCreer.add(myProfil.getProfil_libelle()) ;
                     }


                 }

             }

             if(profilNonCreer.size()>0){
                 String mess="Les profils suivants ont été déjà affectés!";
                 messageRetour = String.join(", ", profilNonCreer);
                 messageRetour= mess+" "+ messageRetour ;
             }
         }

        return  messageRetour ;
     }
     public String seConnecterAdmin(String email,String motdePasse,Long Profilid ){
        String messageRetour = null;
         utilisateur myutilisateur2 = new utilisateur() ;
         myutilisateur2 = verifiEmailUtilisateur(email) ;

                            if(myutilisateur2!=null){
                                utilisateur_has_personnel myUtiliPers= new utilisateur_has_personnel() ;
                                myUtiliPers = getUtilisateurPersonnAdmin(email,Profilid) ;
                                LocalDate dateFin ;
                                LocalDate dateDuJour= LocalDate.now() ;
                                String profilUtilsateur ;

                                if(myUtiliPers!=null) {
                                              dateFin =  myUtiliPers.getUtilisateur_has_person_date_fin() ;
                                            int val = dateFin.compareTo(dateDuJour) ;
                                            int active = myUtiliPers.getUtilisateur_has_person_active() ;
//                                            System.out.print("valDiff "+val);

                                            if(val<0){
                                                messageRetour="Ce compte a expiré!";
                                            }
                                            else if (active==0) {
                                                messageRetour="Ce profil a été désactivé pour ce compte pour cette ecole!";
                                            } else  {
                                                utilisateur myUtilis= new utilisateur() ;
                                                myUtilis= checkPassword(email,motdePasse);
                                                if(myUtilis != null){
                                                    profilUtilsateur= myUtiliPers.getProfil().getProfil_libelle() ;
                                                    messageRetour=profilUtilsateur ;
                                                } else {
                                                    messageRetour="Login ou mot de passe incorrect!";
                                                }

                                            }
                                } else {
                                    messageRetour="Profil  incorrect!";
                                }
                            } else {
                                messageRetour="cette adresse n'est associée à aucun compte!";
                            }
       return  messageRetour ;
     }
     public String seConnecter(String login,String motdePasse,Long Profilid,Long Ecoleid ){
        String messageRetour = null;
         utilisateur myutilisateur2 = new utilisateur() ;
         myutilisateur2 = checkUserLogin(login) ;

                            if(myutilisateur2!=null){
                                utilisateur_has_personnel myUtiliPers= new utilisateur_has_personnel() ;
                                myUtiliPers = getUtilisateurPersonn(myutilisateur2.getUtilisateu_email(),Profilid,Ecoleid) ;
                                LocalDate dateFin ;
                                LocalDate dateDuJour= LocalDate.now() ;
                                String profilUtilsateur ;

                                if(myUtiliPers!=null) {
                                              dateFin =  myUtiliPers.getUtilisateur_has_person_date_fin() ;
                                            int val = dateFin.compareTo(dateDuJour) ;
                                            int active = myUtiliPers.getUtilisateur_has_person_active() ;
//                                            System.out.print("valDiff "+val);

                                            if(val<0){
                                                messageRetour="Ce compte a expiré!";
                                            }
                                            else if (active==0) {
                                                messageRetour="Ce profil a été désactivé pour ce compte pour cette ecole!";
                                            } else  {
                                                utilisateur myUtilis= new utilisateur() ;
                                                //gestion de l'exception pour se connecter

                                                try {
                                                    utilisateur  myLogin = new utilisateur();
                                                    myLogin= (utilisateur) em.createQuery("select o from utilisateur o where o.utilisateu_login =:login and o.utilisateur_mot_de_passe=:motPasse")
                                                            .setParameter("login",login.trim())
                                                            .setParameter("motPasse",motdePasse.trim())
                                                            .getSingleResult();
                                                    if(!(myLogin ==null)) {
                                                        profilUtilsateur= myUtiliPers.getProfil().getProfil_libelle() ;
                                                        messageRetour=profilUtilsateur ;
                                                    }
                                                } catch (NoResultException e) {
                                                    messageRetour="Login ou mot de passe incorrect!";
                                                }



                                            }
                                } else {
                                    messageRetour="Profil ou code école incorrect!";
                                }
                            } else {
                                messageRetour="cette adresse n'est associée à aucun compte!";
                            }

       return  messageRetour ;
     }

     public  utilisateur_has_personnel getUtilisateurPersonn(String email ,Long Profilid,Long Ecoleid){
         utilisateur myutilisateur2 = new utilisateur() ;
         myutilisateur2 = verifiEmailUtilisateur(email) ;

         try {
             return (utilisateur_has_personnel) em.createQuery("select o from utilisateur_has_personnel  o  where  o.ecole_ecoleid =:codeEcole and o.utilisateur.utilisateurid =:utilisateurId and o.profil.profilid=:profilId")
                     .setParameter("utilisateurId",myutilisateur2.getUtilisateurid())
                     .setParameter("codeEcole",Ecoleid)
                     .setParameter("profilId",Profilid)
                     .getSingleResult();
         } catch (Exception e) {
             return  null;
         }

     }

     public  utilisateur_has_personnel getUtilisateurPersonnAdmin(String email ,Long Profilid){
        utilisateur myutilisateur2 = new utilisateur() ;
        myutilisateur2 = verifiEmailUtilisateur(email) ;

        try {
            return (utilisateur_has_personnel) em.createQuery("select o from utilisateur_has_personnel  o  where   o.utilisateur.utilisateurid =:utilisateurId and o.profil.profilid=:profilId")
                    .setParameter("utilisateurId",myutilisateur2.getUtilisateurid())
                    .setParameter("profilId",Profilid)
                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }

    }



   /*  public  List<utilisateur_has_personnel> getListcompteUtilisateur(){

         TypedQuery<InscriptionAvaliderDto> q = em.createQuery( "SELECT new com.vieecoles.projection.compteUtilisateurSelect() from utilisateur_has_personnel o join o.profil p  where e.ecoleid=:idecole " +
                         " and  o.inscriptions_status=: Status  and o.inscriptions_processus=: processus",
                 InscriptionAvaliderDto.class);
         List<InscriptionAvaliderDto> listInscriptionAvaliderDto = q.setParameter("idecole" ,idecole)
                 .setParameter("Status" ,Status).setParameter("processus",processus).
                 getResultList();
         return  listInscriptionAvaliderDto;
     }*/


     @Transactional
     public utilisateur   creerUtilisateur(String motPasse , String email ,String login ){
         utilisateur myutilisateur1 = new utilisateur() ;
         myutilisateur1= verifiEmailUtilisateur(email) ;

         if(myutilisateur1 == null ) {
             utilisateur myutilisateur = new utilisateur() ;
             myutilisateur.setUtilisateur_mot_de_passe(motPasse);
             myutilisateur.setUtilisateu_email(email);
             myutilisateur.setUtilisateu_login(login);
             myutilisateur.persist();
             return  myutilisateur ;
         } else {
             return  myutilisateur1 ;
         }
       }

    @Transactional
    public utilisateur_has_personnel   affeterUtilisateurProfil (utilisateur_has_personnelDto utiPersonDto)
    {

        utilisateur_has_personnel myUtilisaPerso =new utilisateur_has_personnel() ;
        utilisateur myutilisateur1 ;
        profil myProfil = new profil() ;
        myutilisateur1= utilisateur.findById(utiPersonDto.getUtilisateurid()) ;
        myProfil = profil.findById(utiPersonDto.getProfilid()) ;
        myUtilisaPerso.setProfil(myProfil);
        myUtilisaPerso.setEcole_ecoleid(utiPersonDto.getEcole_ecoleid());
        myUtilisaPerso.setPersonnel_personnelid(utiPersonDto.getPersonnel_personnelid());
        myUtilisaPerso.setUtilisateur(myutilisateur1);
        myUtilisaPerso.setUtilisateur_has_person_date_debut(utiPersonDto.getUtilisateur_has_person_date_debut());
        myUtilisaPerso.setUtilisateur_has_person_date_fin(utiPersonDto.getUtilisateur_has_person_date_fin());
        myUtilisaPerso.setUtilisateur_has_person_active(1);
        myUtilisaPerso.setUtilisateur_has_person_date_creation(LocalDate.now());
        myUtilisaPerso.persist();
       return  myUtilisaPerso ;
    }
     @Transactional
    public String deSactiverProfilUtilisateur(Long personnelId, Long  ecoleId, Long profilId ,int active){
        utilisateur_has_personnel myUtilis =new utilisateur_has_personnel() ;
      String messageRetour = null ;
        myUtilis=  veriCompteUtilisateurEcole(personnelId,ecoleId,profilId) ;

        if( myUtilis!=null ){
            myUtilis.setUtilisateur_has_person_active(active);
            messageRetour ="Compte désactiver avec succès!" ;
        } else {
            messageRetour ="Compte inexistant avec ces paramètres!" ;
        }

        return  messageRetour ;
    }

    @Transactional
    public String deSactiverProfilEcole( Long  ecoleId, Long profilId, int active){
        utilisateur_has_personnel myUtilis =new utilisateur_has_personnel() ;
        String messageRetour = null ;
         int q = em.createQuery("update utilisateur_has_personnel e set  e.utilisateur_has_person_active=:activier  where e.ecole_ecoleid=:ecoleId and e.profil.profilid=:profilId ")

                .setParameter("activier",active)
                .setParameter("ecoleId",ecoleId)
                .setParameter("profilId",profilId).executeUpdate() ;

        if(q>0){
            messageRetour="Profil désactivé avec succès !";
        } else {
            messageRetour="Aucun compte trouvé pour ce profil!";
        }

        return  messageRetour;


    }



    @Transactional
 public String modifierInfosCompte(Long utilisateurPersonnId,Long ecoleId,Long  personnelId , Long profilId,LocalDate dateDebut,LocalDate dateFin){
        String messageRetour= null ;
       utilisateur_has_personnel myUtilPer= new utilisateur_has_personnel() ;
        utilisateur_has_personnel myUtilPer2 = new utilisateur_has_personnel() ;
       myUtilPer =  veriCompteUtilisateurEcole(personnelId,ecoleId,profilId);
       if(myUtilPer!=null){
           messageRetour="Cet utilisateur a déjà ce profil!";
       } else {
           profil myProfil= new profil() ;
           myProfil = profil.findById(profilId) ;
           myUtilPer2 = utilisateur_has_personnel.findById(utilisateurPersonnId) ;
           myUtilPer2.setProfil(myProfil);
           myUtilPer2.setUtilisateur_has_person_modif(LocalDate.now());
           myUtilPer2.setUtilisateur_has_person_date_debut(dateDebut);
           myUtilPer2.setUtilisateur_has_person_date_fin(dateFin);
           messageRetour="Profil modifié avec succès!";
       }

        return messageRetour ;
 }



@Transactional
    public String modifierMotPasse(String login,String motdePasse ,String nouveauMotPasse){
        utilisateur myUtilisateur = new utilisateur() ;
        utilisateur myUtilisateur2 = new utilisateur() ;
        String messageRetour= null ;
        myUtilisateur = checkPassword(login ,motdePasse ) ;

        if(myUtilisateur!= null) {
            myUtilisateur2 = utilisateur.findById(myUtilisateur.getUtilisateurid()) ;
            System.out.print("myUtilisateur2MotPasse "+myUtilisateur2);
            myUtilisateur2.setUtilisateur_mot_de_passe(nouveauMotPasse);
            myUtilisateur2.setUtilisateu_login(myUtilisateur.getUtilisateu_login());
            System.out.print("myUtilisateur211MotPasse "+myUtilisateur2);
            messageRetour ="mot de passe modifié!" ;
        } else {
            messageRetour ="Les paramètres de connexion sont incorrects!" ;
        }
    return  messageRetour;

    }


//@Transactional
    public utilisateur checkPassword(String login,String motdePasse){
        try {
            return (utilisateur) em.createQuery("select o from utilisateur o where o.utilisateu_login =:login and o.utilisateur_mot_de_passe=:motPasse")
                    .setParameter("login",login.trim())
                    .setParameter("motPasse",motdePasse.trim())
                    .getSingleResult();
        } catch (NoResultException e) {
        //	e.printStackTrace();
            return  null;
        }
    }




     public utilisateur verifiEmailUtilisateur(String emailUtilisateur){
         try {
             return (utilisateur) em.createQuery("select o from utilisateur o where o.utilisateu_email =:email")
                     .setParameter("email",emailUtilisateur)
                     .getSingleResult();
         } catch (Exception e) {
        	 e.printStackTrace();
             return  null;
         }

     }

     public utilisateur checkUserLogin(String login){
         try {
             return (utilisateur) em.createQuery("select o from utilisateur o where o.utilisateu_login =:login")
                     .setParameter("login",login)
                     .getSingleResult();
         } catch (Exception e) {
        	 e.printStackTrace();
             return  null;
         }

     }

    public String pseudo(String login){
        try {
            return (String) em.createQuery("select o.utilisateu_login from utilisateur o where o.utilisateu_login =:login")
                    .setParameter("login",login)
                    .getSingleResult();
        } catch (Exception e) {
        	e.printStackTrace();
            return  null;
        }

    }
     public  utilisateur_has_personnel veriCompteUtilisateurEcole(Long personnelId , Long codeEcole,Long profilId){

         try {
             return (utilisateur_has_personnel) em.createQuery("select o from utilisateur_has_personnel  o  where  o.ecole_ecoleid =:codeEcole and o.personnel_personnelid =:personnelId and o.profil.profilid=:profilId")
                     .setParameter("personnelId",personnelId)
                     .setParameter("codeEcole",codeEcole)
                     .setParameter("profilId",profilId)
                      .getSingleResult();
         } catch (Exception e) {
        	 e.printStackTrace();
             return  null;
         }



     }




    public void  deletclasse(long ecoleID){
        ecole obj1 = ecole.findById(ecoleID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<ecole> search(String Libelle){

       return   em.createQuery("select o from ecole o where  o.ecoleclibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();

   }

    public  long count(){
        return  ecole.count();
    }


}
