package com.vieecoles.services.souscription;

import com.vieecoles.dao.entities.Civilite;
import com.vieecoles.dao.entities.domaine_formation;
import com.vieecoles.dao.entities.fonction;
import com.vieecoles.dao.entities.niveau_etude;
import com.vieecoles.dao.entities.operations.Inscriptions;
import com.vieecoles.dao.entities.operations.ecole;
import com.vieecoles.dao.entities.operations.personnel;
import com.vieecoles.dto.*;
import com.vieecoles.dao.entities.operations.sous_attent_personn;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class SouscPersonnelService implements PanacheRepositoryBase<sous_attent_personn, Long> {
    @Inject
    EntityManager em;

@Transactional
   public String   CreerSousCriperson(sous_attent_personnDto souscriPersonn) throws IOException, SQLException {
    sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
    mysouscripPersonn1= getSouscripByEmail(souscriPersonn.getSous_attent_personn_email()) ;
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

        return  "Demande créée avec succes !";
    }

          }

   public  List<sous_attent_personn> findAllSouscriptionAvaliderDto(String status ){
        TypedQuery<sous_attent_personn> q = (TypedQuery<sous_attent_personn>) em.createQuery( "SELECT  o from sous_attent_personn o join o.domaine_formation  d join o.niveau_etude n join o.fonction where o.sous_attent_personn_statut=:status  ");
        List<sous_attent_personn> listSouscriptionAvaliderDto = q.setParameter("status" ,status)
                .setParameter("status" ,status).
                getResultList();
        return  listSouscriptionAvaliderDto;
    }

    public  List<personnel> findAllPersonneParEcole(Long idEcole ){
        TypedQuery<personnel> q = (TypedQuery<personnel>) em.createQuery( "SELECT  o from personnel o join o.domaine_formation_domaine_formationid  d join o.niveau_etude n join o.fonction where o.ecole.ecoleid=:idEcole  ");
        List<personnel> listSouscriptionAvaliderDto = q.setParameter("idEcole" ,idEcole).
                getResultList();
        return  listSouscriptionAvaliderDto;
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

        mysouscripPersonn.setNiveau_etude(myNiveauEtude);
        mysouscripPersonn.setDomaine_formation(myDomaineFormation);
        mysouscripPersonn.setSous_attent_personn_date_naissance(souscPersonn.getSous_attent_personn_date_naissance());
        mysouscripPersonn.setSous_attent_personn_diplome_recent(souscPersonn.getSous_attent_personn_diplome_recent());
        mysouscripPersonn.setSous_attent_personn_email(souscPersonn.getSous_attent_personn_email());
        mysouscripPersonn.setSous_attent_personn_lien_cv(souscPersonn.getSous_attent_personn_lien_cv());
        mysouscripPersonn.setSous_attent_personn_nom(souscPersonn.getSous_attent_personn_nom());
        mysouscripPersonn.setSous_attent_personn_prenom(souscPersonn.getSous_attent_personn_prenom());
        mysouscripPersonn.setSous_attent_personn_sexe(souscPersonn.getSous_attent_personn_sexe());
        mysouscripPersonn.setSous_attent_personn_nbre_annee_experience(souscPersonn.getSous_attent_personn_nbre_annee_experience());
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



}
