package com.vieecoles.services.souscription;

import com.vieecoles.dto.EleveDto;
import com.vieecoles.dto.personnelDto;
import com.vieecoles.dto.sous_attent_personnDto;
import com.vieecoles.entities.*;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.entities.operations.personnel;
import com.vieecoles.entities.operations.sous_attent_personn;
import com.vieecoles.projection.personnelSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class SouscPersonnelService implements PanacheRepositoryBase<sous_attent_personn, Long> {
    @Inject
    EntityManager em;

@Transactional
   public String   CreerSousCriperson(sous_attent_personnDto souscriPersonn) {
    sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
    mysouscripPersonn1= getSouscripByEmail(souscriPersonn.getSous_attent_personn_email()) ;
    if (mysouscripPersonn1!=null) {
        return  "EXISTE_DEJA !";
    } else {
        niveau_etude  myNiveauEtude = new niveau_etude() ;
        domaine_formation myDomaineFormation = new domaine_formation() ;
        sous_attent_personn  mysouscripPersonn = new sous_attent_personn() ;

        myNiveauEtude= niveau_etude.findById(souscriPersonn.getNiveau_etudeIdentifiant()) ;
        myDomaineFormation = domaine_formation.findById(souscriPersonn.getIdentifiantdomaine_formation());

        mysouscripPersonn.setNiveau_etude(myNiveauEtude);
        mysouscripPersonn.setDomaine_formation(myDomaineFormation);
        mysouscripPersonn.setSous_attent_personn_date_naissance(souscriPersonn.getSous_attent_personn_date_naissance());
        mysouscripPersonn.setSous_attent_personn_diplome_recent(souscriPersonn.getSous_attent_personn_diplome_recent());
        mysouscripPersonn.setSous_attent_personn_email(souscriPersonn.getSous_attent_personn_email());
        mysouscripPersonn.setSous_attent_personn_lien_cv(souscriPersonn.getSous_attent_personn_lien_cv());
        mysouscripPersonn.setSous_attent_personn_nom(souscriPersonn.getSous_attent_personn_nom());
        mysouscripPersonn.setSous_attent_personn_prenom(souscriPersonn.getSous_attent_personn_prenom());
        mysouscripPersonn.setSous_attent_personn_sexe(souscriPersonn.getSous_attent_personn_sexe());
        mysouscripPersonn.setSous_attent_personn_nbre_annee_experience(souscriPersonn.getSous_attent_personn_nbre_annee_experience());
        mysouscripPersonn.persist();
        return  "Compte créé avec succes !";
    }

          }


         /* public  List<personnelSelect> getPersonnels(){
              TypedQuery<personnelSelect> q =   em.createQuery("select new com.vieecoles.projection.personnelSelect(o.personnelid, o.personnelcode, o.personnelnom, o.personnelprenom, o.personneldatenaissance, o.personnel_lieunaissance, t.type_personnellibelle, s.personnel_statulibelle, f.fonctionlibelle)  from personnel o  join  o.personnel_status s join   o.type_personnel t join  o.fonction f",personnelSelect.class) ;
              List<personnelSelect> personnelSelect = q.getResultList();
              return personnelSelect ;
          }*/

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
