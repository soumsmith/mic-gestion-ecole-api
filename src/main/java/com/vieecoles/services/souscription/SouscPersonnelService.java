package com.vieecoles.services.souscription;

import com.vieecoles.dto.*;
import com.vieecoles.entities.domaine_formation;
import com.vieecoles.entities.fonction;
import com.vieecoles.entities.matiere;
import com.vieecoles.entities.niveau_etude;
import com.vieecoles.entities.utilisateur;
import com.vieecoles.entities.operations.Inscriptions;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.personnel;
import com.vieecoles.entities.operations.sous_attent_personn;
import com.vieecoles.entities.operations.sousc_atten_etabliss;
import com.vieecoles.services.domaineFormationService;
import com.vieecoles.services.domaineService;
import com.vieecoles.services.profilService;
import com.vieecoles.services.connexion.connexionService;

import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Personnel;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.services.PersonnelMatiereClasseService;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class SouscPersonnelService implements PanacheRepositoryBase<sous_attent_personn, Long> {
    @Inject
    EntityManager em;

    @Inject
    connexionService conServ ;
    @Inject
    profilService profilServ ;

    @Inject
    domaineFormationService domServ ;
  @Inject
  PersonnelMatiereClasseService persMatClasService;




@Transactional
   public String   CreerSousCriperson(sous_attent_personnDto souscriPersonn) throws IOException, SQLException {
    sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
    mysouscripPersonn1= getSouscripByEmail(souscriPersonn.getSous_attent_personn_email()) ;
    //System.out.println("souscripteur trouvé "+mysouscripPersonn1.toString());
    if (mysouscripPersonn1!=null) {
        return  "EXISTE_DEJA !";
    } else {

        niveau_etude myNiveauEtude = new niveau_etude() ;
        domaine_formation myDomaineFormation = new domaine_formation() ;
        sous_attent_personn  mysouscripPersonn = new sous_attent_personn() ;
        fonction myFonction = new fonction() ;
        myNiveauEtude= niveau_etude.findById(souscriPersonn.getNiveau_etudeIdentifiant()) ;
        myDomaineFormation = domaine_formation.findById(souscriPersonn.getIdentifiantdomaine_formation());
        myFonction = fonction.findById(souscriPersonn.getFonctionidentifiant());
        mysouscripPersonn.setFonction(myFonction);
        mysouscripPersonn.setType_autorisation_idtype_autorisationid(souscriPersonn.getType_autorisation_idtype_autorisationid());
        mysouscripPersonn.setNiveau_etude(myNiveauEtude);
        mysouscripPersonn.setDomaine_formation(myDomaineFormation);
        mysouscripPersonn.setSous_attent_personn_date_naissance(souscriPersonn.getSous_attent_personn_date_naissance());
        mysouscripPersonn.setSous_attent_personn_diplome_recent(souscriPersonn.getSous_attent_personn_diplome_recent());
        mysouscripPersonn.setSous_attent_personn_email(souscriPersonn.getSous_attent_personn_email());
        mysouscripPersonn.setSous_attent_personn_lien_cv(souscriPersonn.getSous_attent_personn_lien_cv());
        mysouscripPersonn.setSous_attent_personn_nom(souscriPersonn.getSous_attent_personn_nom());
        mysouscripPersonn.setSous_attent_personn_prenom(souscriPersonn.getSous_attent_personn_prenom());
        mysouscripPersonn.setSous_attent_personn_sexe(souscriPersonn.getSous_attent_personn_sexe());
        mysouscripPersonn.setSous_attent_personn_contact(souscriPersonn.getSous_attent_personn_contact());
        mysouscripPersonn.setSous_attent_personn_lien_autorisation(souscriPersonn.getSous_attent_personn_lien_autorisation());
        mysouscripPersonn.setSous_attent_personn_lien_piece(souscriPersonn.getSous_attent_personn_lien_piece());
        mysouscripPersonn.setSous_attent_personn_statut("EN ATTENTE");
        mysouscripPersonn.setSous_attent_personn_date_creation(LocalDateTime.now());
        mysouscripPersonn.setSous_attent_personn_nbre_annee_experience(souscriPersonn.getSous_attent_personn_nbre_annee_experience());
        mysouscripPersonn.persist();
        System.out.println("Demande créée avec succes !");
        return  "Demande créée avec succes !";

    }

          }
  @Transactional
  public sous_attent_personn   CreerSousCripersonVieEcole(sous_attent_personnDto souscriPersonn) throws IOException, SQLException {
    sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
    mysouscripPersonn1= getSouscripByEmail(souscriPersonn.getSous_attent_personn_email()) ;
    //System.out.println("souscripteur trouvé "+mysouscripPersonn1.toString());
    if (mysouscripPersonn1!=null) {
      return  mysouscripPersonn1;
    } else {

      niveau_etude myNiveauEtude = new niveau_etude() ;
      domaine_formation myDomaineFormation = new domaine_formation() ;
      sous_attent_personn  mysouscripPersonn = new sous_attent_personn() ;
      fonction myFonction = new fonction() ;
      myNiveauEtude= niveau_etude.findById(souscriPersonn.getNiveau_etudeIdentifiant()) ;
      myDomaineFormation = domaine_formation.findById(souscriPersonn.getIdentifiantdomaine_formation());
      myFonction = fonction.findById(souscriPersonn.getFonctionidentifiant());
      mysouscripPersonn.setFonction(myFonction);
      mysouscripPersonn.setType_autorisation_idtype_autorisationid(souscriPersonn.getType_autorisation_idtype_autorisationid());
      mysouscripPersonn.setNiveau_etude(myNiveauEtude);
      mysouscripPersonn.setDomaine_formation(myDomaineFormation);
      mysouscripPersonn.setSous_attent_personn_date_naissance(souscriPersonn.getSous_attent_personn_date_naissance());
      mysouscripPersonn.setSous_attent_personn_diplome_recent(souscriPersonn.getSous_attent_personn_diplome_recent());
      mysouscripPersonn.setSous_attent_personn_email(souscriPersonn.getSous_attent_personn_email());
      mysouscripPersonn.setSous_attent_personn_lien_cv(souscriPersonn.getSous_attent_personn_lien_cv());
      mysouscripPersonn.setSous_attent_personn_nom(souscriPersonn.getSous_attent_personn_nom());
      mysouscripPersonn.setSous_attent_personn_prenom(souscriPersonn.getSous_attent_personn_prenom());
      mysouscripPersonn.setSous_attent_personn_sexe(souscriPersonn.getSous_attent_personn_sexe());
      mysouscripPersonn.setSous_attent_personn_contact(souscriPersonn.getSous_attent_personn_contact());
      mysouscripPersonn.setSous_attent_personn_lien_autorisation(souscriPersonn.getSous_attent_personn_lien_autorisation());
      mysouscripPersonn.setSous_attent_personn_lien_piece(souscriPersonn.getSous_attent_personn_lien_piece());
      mysouscripPersonn.setSous_attent_personn_statut("VALIDEE");
      mysouscripPersonn.setSous_attent_personn_date_creation(LocalDateTime.now());
      mysouscripPersonn.setSous_attent_personn_nbre_annee_experience(souscriPersonn.getSous_attent_personn_nbre_annee_experience());
      mysouscripPersonn.persist();
      System.out.println("Demande créée avec succes !");
      return  mysouscripPersonn;

    }

  }
  @Transactional
  public PersonnelMatiereClasse prepareProfMatiereClasseDto(String email, Ecole ecole, Classe classe , matiere mat ,AnneeScolaire anneeScolaire){
    sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;

    Personnel personnel1 = new Personnel() ;
    mysouscripPersonn1= getSouscripByEmail(email) ;
    EcoleHasMatiere ecoleHasMatiere= new EcoleHasMatiere();
    PersonnelMatiereClasse personnelMatiereClasse = new PersonnelMatiereClasse() ;



    //System.out.println("souscripteur trouvé "+mysouscripPersonn1.toString());
    if (mysouscripPersonn1!=null) {
      personnel1= getPersonnelBySouscription(ecole.getId(),mysouscripPersonn1.getSous_attent_personnid());
      ecoleHasMatiere= getidEcoleHasMatiere(ecole.getId(),mat.getMatiereid());

      personnelMatiereClasse.setMatiere(ecoleHasMatiere);
      personnelMatiereClasse.setClasse(classe);
      personnelMatiereClasse.setAnnee(anneeScolaire);
      personnelMatiereClasse.setPersonnel(personnel1);


    }
  return personnelMatiereClasse ;
  }

          public  String CreerSouscripCompteUtilisateur(sous_attent_personnDto souscriPersonn) throws SQLException, IOException {
           String messageRetour= null;
              messageRetour = CreerSousCriperson(souscriPersonn) ;

              if (messageRetour.equals("Demande créée avec succes !")) {
                  sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
                  mysouscripPersonn1= getSouscripByEmail(souscriPersonn.getSous_attent_personn_email()) ;
                  Long idsouscripteur = mysouscripPersonn1.getSous_attent_personnid() ;
                  CreerCompteUtilsateurDto creerCompte = new CreerCompteUtilsateurDto() ;
                  creerCompte.setUtilisateur_mot_de_passe(souscriPersonn.getSous_attent_personn_password());
                  creerCompte.setUtilisateu_login(souscriPersonn.getSous_attent_personn_login());
                  creerCompte.setSous_attent_personn_sous_attent_personnid(idsouscripteur);
                  creerCompte.setUtilisateu_email(souscriPersonn.getSous_attent_personn_email());
                  messageRetour = creerCompteUtilisateur(creerCompte) ;

              } else if (messageRetour.equals("EXISTE_DEJA !")){
                  messageRetour ="EXISTE_DEJA !" ;
              }

    return  messageRetour ;
          }

  public  sous_attent_personn creerProfesseurVieEcole(sous_attent_personnDto souscriPersonn,Long idEcole) throws SQLException, IOException {
    sous_attent_personn messageRetour = new sous_attent_personn();
    messageRetour = CreerSousCripersonVieEcole(souscriPersonn) ;
    String messRespon=null ;
utilisateur user = new utilisateur() ;
  user = getAcountUser(souscriPersonn.getSous_attent_personn_login()) ;
    if (messageRetour!=null && user==null) {
      sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
      mysouscripPersonn1= getSouscripByEmail(souscriPersonn.getSous_attent_personn_email()) ;
      Long idsouscripteur = mysouscripPersonn1.getSous_attent_personnid() ;
      CreerCompteUtilsateurDto creerCompte = new CreerCompteUtilsateurDto() ;
      creerCompte.setUtilisateur_mot_de_passe(souscriPersonn.getSous_attent_personn_password());
      creerCompte.setUtilisateu_login(souscriPersonn.getSous_attent_personn_login());
      creerCompte.setSous_attent_personn_sous_attent_personnid(idsouscripteur);
      creerCompte.setUtilisateu_email(souscriPersonn.getSous_attent_personn_email());
       creerCompteUtilisateur(creerCompte) ;


    }

    return  messageRetour ;
  }


       @Transactional
          public String  creerCompteUtilisateur(CreerCompteUtilsateurDto creerCompte){

           utilisateur myNewInser = new utilisateur() ;
           myNewInser.setUtilisateu_email(creerCompte.getUtilisateu_email());
           myNewInser.setSous_attent_personn_sous_attent_personnid(creerCompte.getSous_attent_personn_sous_attent_personnid());
           myNewInser.setUtilisateur_mot_de_passe(creerCompte.getUtilisateur_mot_de_passe());
           myNewInser.setUtilisateu_login(creerCompte.getUtilisateu_login());
           myNewInser.persist();
           return  "Demande créée avec succes !";

          }

   public  List<sous_attent_personn> findAllSouscriptionAvaliderDto(String status ){
        TypedQuery<sous_attent_personn> q = (TypedQuery<sous_attent_personn>) em.createQuery( "SELECT  o from sous_attent_personn o join o.domaine_formation  d join o.niveau_etude n join o.fonction where o.sous_attent_personn_statut=:status  ");
        List<sous_attent_personn> listSouscriptionAvaliderDto = q.setParameter("status" ,status)
                .setParameter("status" ,status).
                getResultList();
        return  listSouscriptionAvaliderDto;
    }

    public  List<sous_attent_personn> findAllSouscriptionAvaliderDtoFondateur(String status,String fonction ){
        TypedQuery<sous_attent_personn> q = (TypedQuery<sous_attent_personn>) em.createQuery( "SELECT  o from sous_attent_personn o join o.fonction f where o.sous_attent_personn_statut=:status and f.fonctionlibelle=:fonction  ");
        List<sous_attent_personn> listSouscriptionAvaliderDto = q.setParameter("status" ,status)
                .setParameter("status" ,status)
                .setParameter("fonction" ,fonction).
                getResultList();
        return  listSouscriptionAvaliderDto;
    }



/*     public  List<sous_attent_personn> findAllSouscriptionAvaliderDto(String status ){
        TypedQuery<sous_attent_personn> q = (TypedQuery<sous_attent_personn>) em.createQuery( "SELECT  o from sous_attent_personn o  where o.sous_attent_personn_statut=:status  ");
        List<sous_attent_personn> listSouscriptionAvaliderDto = q.setParameter("status" ,status)
                .setParameter("status" ,status).
                getResultList();
        return  listSouscriptionAvaliderDto;
    } */


    public  List<ClassesTenuesDto> getClasseProf(Long idPersonn , Long idAnn) {

        List<ClassesTenuesDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<ClassesTenuesDto> q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.ClassesTenuesDto(c.classelibelle,pm.personnel.personnelid ,an.annee_scolaireid ) FROM Personnel_matiere_classe pm , classe c , ecole e  ,personnel p , niveau_etude n ,Annee_Scolaire an " +
                        "where pm.classe.classeid = c.classeid and c.ecole_ecoleid = e.ecoleid  and pm.personnel.personnelid = p.personnelid" +
                        " and p.niveau_etude.niveau_etudeid  = n.niveau_etudeid and pm.annee_scolaire.annee_scolaireid= an.annee_scolaireid and " +
                        " p.personnelid =:idPersonn and an.annee_scolaireid =:idAnn "
                , ClassesTenuesDto.class);
        return      classeNiveauDtoList = q.setParameter("idPersonn", idPersonn)
                .setParameter("idAnn", idAnn)
                .getResultList() ;


    }

    public  List<personnel> findAllPersonneParEcole(Long idEcole  ){
       // int firstResult = (pageNumber - 1) * pageSize;
        List<personnel> listSouscriptionAvaliderDto = null;
        try {
            TypedQuery<personnel> q = (TypedQuery<personnel>) em.createQuery( "SELECT  o from personnel o join o.domaine_formation_domaine_formationid  d join o.niveau_etude n join o.fonction f where o.ecole.ecoleid=:idEcole and f.fonctionlibelle <>:fonctionLibelle ");
             listSouscriptionAvaliderDto = q.setParameter("idEcole" ,idEcole).
                    setParameter("fonctionLibelle" ,"FONDATEUR")
                    //.setFirstResult(firstResult)
                  // .setMaxResults(pageSize)
                    .getResultList();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return  listSouscriptionAvaliderDto;


    }

    public  sous_attent_personn findPersonnelById(Long idPersonnel ){
        TypedQuery<sous_attent_personn> q = (TypedQuery<sous_attent_personn>) em.createQuery( "SELECT  o from sous_attent_personn o  where o.sous_attent_personnid=:idPersonnel  ");
        sous_attent_personn listSouscriptionAvaliderDto = q.setParameter("idPersonnel" ,idPersonnel)
               .getSingleResult();

        return  listSouscriptionAvaliderDto;
    }

   /*  public  sous_attent_personn findPersonnelById(Long idPersonnel ){
        TypedQuery<sous_attent_personn> q = (TypedQuery<sous_attent_personn>) em.createQuery( "SELECT  o from sous_attent_personn o join o.domaine_formation  d join o.niveau_etude n join o.fonction where o.sous_attent_personnid=:idPersonnel  ");
        sous_attent_personn listSouscriptionAvaliderDto = q.setParameter("idPersonnel" ,idPersonnel)
               .getSingleResult();

        return  listSouscriptionAvaliderDto;
    } */

     public String  valideCreerCompteFondateur(souscriptionValidationFondatDto mysouscription) {
        String MessageRetour =null ;

        List<Long> listEcole = getListEcoleBySouscrip2(mysouscription.getIdsouscrip());
        // valider soucripValider
        validerSouscriptionFond(mysouscription) ;
        for(int i = 0 ; i < listEcole.size() ; i++){
            personnel PersonnCreer = new personnel() ;
            //Mise à jour Etablissement connecte
            miseAjourEtabliConnecte(listEcole.get(i)) ;
            //recruter fondateur
            Long idEcole = getEcoleByIDSousc(listEcole.get(i)) ;
            PersonnCreer =recruterUnFondateur(idEcole, mysouscription.getIdsouscrip()) ;
            System.out.print("PersonnCreer "+PersonnCreer.getPersonnelid());
            //creer compte fondateur
        System.out.print("DateFin "+mysouscription.getDatefin());
        //System.out.print("IdEcole "+mysouscription.getIdEcole());
        System.out.print("ProfilId "+mysouscription.getProfilId());
        conServ.affecterProfilFondateur(PersonnCreer.getPersonnelid(), mysouscription.getDatefin(), idEcole, mysouscription.getProfilId());
        }


        MessageRetour="opération effectuée avec succès!";

      return MessageRetour ;
     }

     @Transactional
     public List<Long> getListEcoleBySouscrip(Long idSouscripteur){
               TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  o.idSOUS_ATTENT_ETABLISSEMENT from sousc_atten_etabliss o where o.sous_attent_personn_sous_attent_personnid =:idSouscripteur and o.sousc_atten_etabliss_statut=:status and o.connecte=0");
        List<Long> listEcole = q.setParameter("idSouscripteur" ,idSouscripteur)
                               .setParameter("status" ,Inscriptions.status.EN_ATTENTE).
                getResultList();
        return  listEcole;
       }

    @Transactional
    public List<Long> getListNewEcoleBySouscrip(Long idSouscripteur){
        TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT  o.idSOUS_ATTENT_ETABLISSEMENT from sousc_atten_etabliss o where o.sous_attent_personn_sous_attent_personnid =:idSouscripteur and o.sousc_atten_etabliss_statut=:status and o.connecte=0");
        List<Long> listEcole = q.setParameter("idSouscripteur" ,idSouscripteur)
                .setParameter("status" ,Inscriptions.status.VALIDEE).
                getResultList();
        return  listEcole;
    }

       @Transactional
       public  List<Long> getListEcoleBySouscrip2(Long idSouscripteur){
        List<Long> listEcole ;
        listEcole=  em.createQuery( "SELECT  o.idSOUS_ATTENT_ETABLISSEMENT from sousc_atten_etabliss o where o.sous_attent_personn_sous_attent_personnid =:idSouscripteur and o.sousc_atten_etabliss_statut=:status and o.connecte=0")
                                .setParameter("idSouscripteur" ,idSouscripteur)
                                 .setParameter("status" ,Inscriptions.status.VALIDEE).
                  getResultList();
          return  listEcole;
         }


    @Transactional
    public  void  miseAjourEtabliConnecte(Long idEcole){
      sousc_atten_etabliss sousc = new sousc_atten_etabliss();
        sousc = sousc_atten_etabliss.findById(idEcole) ;
        sousc.setConnecte(1);
    }

    @Transactional
    public  Long getidUser(Long idSouscripteur){
       Long idUser ;
        idUser= (Long) em.createQuery( "SELECT  o.utilisateurid from utilisateur o where o.sous_attent_personn_sous_attent_personnid =:idSouscripteur ")
                .setParameter("idSouscripteur" ,idSouscripteur).getSingleResult();
        return  idUser;
    }

         @Transactional
       public  Long getEcoleByIDSousc(Long idDemand){
        Long  myEcoleid ;
        myEcoleid =    (Long) em.createQuery( "SELECT  o.ecoleid from ecole o where o.sousc_atten_etabliss_idSOUS_ATTENT_ETABLISSEMENT =:idDemand ")
        .setParameter("idDemand" ,idDemand)
                                .
                 getSingleResult() ;
          return  myEcoleid;
         }

     @Transactional
     public personnel  recruterUnFondateur(Long idEcole ,Long sous_attentId){
       personnel PersonnCreer = new personnel() ;
     sous_attent_personn sous_attent = new sous_attent_personn() ;
     sous_attent = sous_attent_personn.findById(sous_attentId) ;
      personnel person = new personnel() ;
         personnel person2 = new personnel() ;
         ecole myecole= new ecole() ;
     myecole= ecole.findById(idEcole) ;
         niveau_etude myNive= new niveau_etude() ;
         domaine_formation myDom = new domaine_formation();

        myDom = getDomFormation();
        myNive= getNivauEtude() ;


         person= verifExistancePersonnel(sous_attent.getSous_attent_personnid() ,idEcole) ;
         sous_attent_personn  mysous= new sous_attent_personn() ;
         String messageRetour  ;
         if (person==null){
             person2.setPersonnelnom(sous_attent.getSous_attent_personn_nom());
             person2.setPersonnelprenom(sous_attent.getSous_attent_personn_prenom());
             person2.setPersonnel_contact(sous_attent.getSous_attent_personn_contact());
             person2.setPersonneldatenaissance(sous_attent.getSous_attent_personn_date_naissance());
             person2.setSous_attent_personn(sous_attent);
             person2.setFonction(sous_attent.getFonction());
             person2.setEcole(myecole);
             //Civilite mycivilite= Civilite.valueOf(sous_attent.getSous_attent_personn_sexe());
             //person2.setCivilite(mycivilite);

             person2.setDomaine_formation_domaine_formationid(myDom);
             person2.setNiveau_etude(myNive);
             person2.persist();
             PersonnCreer= person2;

         } else {
            PersonnCreer = null;
         }

         return  PersonnCreer ;
     }
    @Transactional
     public domaine_formation getDomFormation(){
        domaine_formation myDom= new domaine_formation();

      return  myDom= (domaine_formation) em.createQuery("select e from domaine_formation e   where e.domaine_formation_libelle =:libelleFonf"
        ,domaine_formation.class )
 .setParameter("libelleFonf","Education")
 .getSingleResult();

     }

     @Transactional
     public niveau_etude getNivauEtude(){
        niveau_etude myDom= new niveau_etude();

      return  myDom= (niveau_etude) em.createQuery("select e from niveau_etude e   where e.niveau_etude_libelle =:libelleFonf"
        ,niveau_etude.class )
 .setParameter("libelleFonf","Niveau Fondateur")
 .getSingleResult();

     }

@Transactional
    public String  recruterUnAgent(Long idEcole ,Long sous_attentId){
    sous_attent_personn sous_attent = new sous_attent_personn() ;
    sous_attent = sous_attent_personn.findById(sous_attentId) ;
     personnel person = new personnel() ;
        personnel person2 = new personnel() ;
        ecole myecole= new ecole() ;
    myecole= ecole.findById(idEcole) ;
        niveau_etude myNive= new niveau_etude() ;
        domaine_formation myDom = new domaine_formation();
        person= verifExistancePersonnel(sous_attent.getSous_attent_personnid() ,idEcole) ;
        sous_attent_personn  mysous= new sous_attent_personn() ;
        String messageRetour  ;
        if (person==null){
            person2.setPersonnelnom(sous_attent.getSous_attent_personn_nom());
            person2.setPersonnelprenom(sous_attent.getSous_attent_personn_prenom());
            person2.setPersonnel_contact(sous_attent.getSous_attent_personn_contact());
            person2.setPersonneldatenaissance(sous_attent.getSous_attent_personn_date_naissance());
            person2.setSous_attent_personn(sous_attent);
            person2.setFonction(sous_attent.getFonction());
            person2.setEcole(myecole);
            //Civilite mycivilite= Civilite.valueOf(sous_attent.getSous_attent_personn_sexe());
            //person2.setCivilite(mycivilite);
            person2.setDomaine_formation_domaine_formationid(sous_attent.getDomaine_formation());
            person2.setNiveau_etude(sous_attent.getNiveau_etude());
            person2.setPersonnel_sexe(sous_attent.getSous_attent_personn_sexe());
            person2.persist();
            messageRetour ="ENREGISTREMENT EFFECTUE AVEC SUCCES";
        } else {
            messageRetour = "CE PERSONNEL EXISTE DEJA" ;
        }

        return  messageRetour ;
    }

  @Transactional
  public String  recruterUnAgentVieecole(Long idEcole ,sous_attent_personn sous_attent,Long idAnnee){

    personnel person = new personnel() ;
    personnel person2 = new personnel() ;
    ecole myecole= new ecole() ;
    myecole= ecole.findById(idEcole) ;
    niveau_etude myNive= new niveau_etude() ;
    domaine_formation myDom = new domaine_formation();
    person= verifExistancePersonnel(sous_attent.getSous_attent_personnid() ,idEcole) ;
    sous_attent_personn  mysous= new sous_attent_personn() ;
    String messageRetour  ;
    if (person==null){
      person2.setPersonnelnom(sous_attent.getSous_attent_personn_nom());
      person2.setPersonnelprenom(sous_attent.getSous_attent_personn_prenom());
      person2.setPersonnel_contact(sous_attent.getSous_attent_personn_contact());
      person2.setPersonneldatenaissance(sous_attent.getSous_attent_personn_date_naissance());
      person2.setSous_attent_personn(sous_attent);
      person2.setFonction(sous_attent.getFonction());
      person2.setEcole(myecole);
      person2.setAnneeId(idAnnee);
      //Civilite mycivilite= Civilite.valueOf(sous_attent.getSous_attent_personn_sexe());
      //person2.setCivilite(mycivilite);
      person2.setDomaine_formation_domaine_formationid(sous_attent.getDomaine_formation());
      person2.setNiveau_etude(sous_attent.getNiveau_etude());
      person2.setPersonnel_sexe(sous_attent.getSous_attent_personn_sexe());
      person2.persist();
      messageRetour ="ENREGISTREMENT EFFECTUE AVEC SUCCES";
    } else {
      messageRetour = "CE PERSONNEL EXISTE DEJA" ;
    }

    return  messageRetour ;
  }
@Transactional
    public personnel verifExistancePersonnel(Long idPerson , Long idEcole){
    personnel mysous= new personnel() ;
    try {
        mysous= (personnel) em.createQuery(" select o from personnel o join o.sous_attent_personn s join o.ecole e where e.ecoleid  =:idEcole and s.sous_attent_personnid=: idPerson  "
                        ,personnel.class )
                .setParameter("idEcole",idEcole)
                .setParameter("idPerson",idPerson)
                .getSingleResult();
    } catch (Exception e) {
        mysous= null ;
    }
       return  mysous ;
    }
  @Transactional
  public personnel verifPersonnel(Long idPerson ){
    personnel mysous = new personnel();
    try {
      mysous = (personnel) em.createQuery(
              "select o from personnel o join o.sous_attent_personn s  where s.sous_attent_personnid = :idPerson",
              personnel.class)
          .setParameter("idPerson", idPerson)
          .setMaxResults(1)  // Limite à 1 résultat
          .getSingleResult();
    } catch (Exception e) {
      mysous = null;
    }
    return mysous;
  }
    @Transactional
    public void validerSouscriptionFond(souscriptionValidationFondatDto mysouscription){
        sous_attent_personn  mysous= new sous_attent_personn() ;
        mysous= (sous_attent_personn) em.createQuery(" select e from sous_attent_personn e   where e.sous_attent_personnid =:souscripId "
                       ,sous_attent_personn.class )
                .setParameter("souscripId",mysouscription.getIdsouscrip())
                .getSingleResult();
        mysous.setSous_attent_personn_date_traitement(LocalDateTime.now());
        mysous.setSous_attent_personn_motifrefus(mysouscription.getMessageRefus());
        mysous.setSous_attent_personn_statut(mysouscription.getStatuts());
    }




@Transactional
    public void validerSouscription(souscriptionValidationDto mysouscription){
        sous_attent_personn  mysous= new sous_attent_personn() ;
        mysous= (sous_attent_personn) em.createQuery(" select e from sous_attent_personn e   where e.sous_attent_personnid =:souscripId "
                       ,sous_attent_personn.class )
                .setParameter("souscripId",mysouscription.getIdsouscrip())
                .getSingleResult();
        mysous.setSous_attent_personn_date_traitement(LocalDateTime.now());
        mysous.setSous_attent_personn_motifrefus(mysouscription.getMessageRefus());
        mysous.setSous_attent_personn_statut(mysouscription.getStatuts());
    }





    public  List<sous_attent_personn> getAllSouscriptionPersonnels(){
    return sous_attent_personn.listAll() ;
    }

    public  sous_attent_personn getSouscripByEmail(String email){
     try {
         return (sous_attent_personn) em.createQuery("select o from sous_attent_personn o where o.sous_attent_personn_email =:email")
                 .setParameter("email",email)
                 .getSingleResult();
     } catch (Exception e) {
         return  null;
     }
    }
  public  utilisateur getAcountUser(String login){
    try {
      return (utilisateur) em.createQuery("select o from utilisateur o where o.utilisateu_login =:login")
          .setParameter("login",login)
          .getSingleResult();
    } catch (Exception e) {
      return  null;
    }
  }
  public  Personnel getPersonnelBySouscription(Long idEcole,Long idPerson){
    try {
      return (Personnel) em.createQuery("select o from Personnel o where o.souscriptionAttenteId=: idPerson and o.ecole.id=:idEcole" )
          .setParameter("idPerson",idPerson)
          .setParameter("idEcole",idEcole)
          .getSingleResult();
    } catch (Exception e) {
      return  null;
    }
  }
  public EcoleHasMatiere getidEcoleHasMatiere(Long idEcole, Long idMatiere){
    try {
      return (EcoleHasMatiere) em.createQuery("select o from EcoleHasMatiere o where o.ecole.id=:idEcole and o.matiere.id=:idMatiere")
          .setParameter("idEcole",idEcole)
          .setParameter("idMatiere",idMatiere)
          .getSingleResult();
    } catch (Exception e) {
      return  null;
    }
  }



    public  sous_attent_personn getSouscPersonnelByID(Long identifiant){
        return   sous_attent_personn.findById(identifiant) ;
    }


    @Transactional
    public sous_attent_personn   modifierSousCriptionPersonnel(sous_attent_personnDto souscPersonn) {
        niveau_etude  myNiveauEtude = new niveau_etude() ;
        domaine_formation myDomaineFormation = new domaine_formation() ;
        sous_attent_personn  mysouscripPersonn = new sous_attent_personn() ;
        myNiveauEtude= niveau_etude.findById(souscPersonn.getNiveau_etudeIdentifiant()) ;
        myDomaineFormation = domaine_formation.findById(souscPersonn.getIdentifiantdomaine_formation());
        mysouscripPersonn= sous_attent_personn.findById(souscPersonn.getSous_attent_personnid()) ;
        mysouscripPersonn.setNiveau_etude(myNiveauEtude);
        fonction myFonction= new fonction() ;
        myFonction = fonction.findById(souscPersonn.getFonctionidentifiant());
        mysouscripPersonn.setSous_attent_personn_contact(souscPersonn.getSous_attent_personn_contact());
        mysouscripPersonn.setFonction(myFonction);
        mysouscripPersonn.setDomaine_formation(myDomaineFormation);
        mysouscripPersonn.setSous_attent_personn_date_naissance(souscPersonn.getSous_attent_personn_date_naissance());
        mysouscripPersonn.setSous_attent_personn_diplome_recent(souscPersonn.getSous_attent_personn_diplome_recent());
        mysouscripPersonn.setSous_attent_personn_email(souscPersonn.getSous_attent_personn_email());
        mysouscripPersonn.setSous_attent_personn_lien_cv(souscPersonn.getSous_attent_personn_lien_cv());
        mysouscripPersonn.setSous_attent_personn_nom(souscPersonn.getSous_attent_personn_nom());
        mysouscripPersonn.setSous_attent_personn_prenom(souscPersonn.getSous_attent_personn_prenom());
        mysouscripPersonn.setSous_attent_personn_sexe(souscPersonn.getSous_attent_personn_sexe());
        mysouscripPersonn.setSous_attent_personn_nbre_annee_experience(souscPersonn.getSous_attent_personn_nbre_annee_experience());
      personnel personnel = verifPersonnel(souscPersonn.getSous_attent_personnid());
      if(personnel!=null){
        personnel.setPersonnel_contact(souscPersonn.getSous_attent_personn_contact());
        personnel.setFonction(myFonction);
        personnel.setDomaine_formation_domaine_formationid(myDomaineFormation);
        personnel.setPersonneldatenaissance(souscPersonn.getSous_attent_personn_date_naissance());
        personnel.setNiveau_etude(myNiveauEtude);
        personnel.setPersonnelnom(souscPersonn.getSous_attent_personn_nom());
        personnel.setPersonnelprenom(souscPersonn.getSous_attent_personn_prenom());
        personnel.setPersonnel_sexe(souscPersonn.getSous_attent_personn_sexe());
      }
        return  mysouscripPersonn ;
    }

    @Transactional
    public void    deleteSousCriptionPersonnel(Long identifiant) {
        sous_attent_personn myPersonel = new sous_attent_personn() ;
        myPersonel = sous_attent_personn.findById(identifiant) ;

        try{
            myPersonel.delete();
        }catch (Exception e) {

        }

    }



    public  String getSouscrCode(String tenant) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);

        Long  maxrecor= (Long) em.createQuery("select max(o.sous_attent_personnid)  from sous_attent_personn o ")
               .getSingleResult();

        System.out.println("maxrecor "+maxrecor);

        String eleveCode= "GAIN"+yearInString+ String.valueOf(maxrecor) ;
        System.out.println("InscriptionCode "+eleveCode);

        return eleveCode ;
    }

    public Response convertFileInp(String path) throws IOException {
        StringBuilder content = new StringBuilder();
        // Create file object and specify file path
        File file = new File(path);
        byte[] arr = new byte[(int)file.length()];

        final InputStream in = new ByteArrayInputStream(arr);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int data = in.read();

        while (data >= 0) {

            out.write((char) data);

            data = in.read();

        }
        out.flush();

        Response.ResponseBuilder builder = Response.ok(out.toByteArray());


        builder.header("Content-Disposition", "attachment; filename=" + "Soum");
        Response response = null;

        response = builder.build();






        return  response;

    }


}
