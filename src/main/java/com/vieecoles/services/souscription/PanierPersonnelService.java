package com.vieecoles.services.souscription;

import com.vieecoles.dto.panier_personnelDto;
import com.vieecoles.dto.souscriptionEcoleDto;
import com.vieecoles.entities.operations.*;
import com.vieecoles.projection.emploi_du_temps_professeurSelect;
import com.vieecoles.projection.panier_personnelSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class PanierPersonnelService implements PanacheRepositoryBase<panier_personnel, Long> {
    @Inject
    EntityManager em;
    @Transactional
    public void validerPanier(Long identiPanier){
        panier_personnel myPanier = new panier_personnel();
        myPanier= panier_personnel.findById(identiPanier) ;
        myPanier.setPanier_personnel_statut("VALIDEE");
    }

   @Transactional
  public String  creeerPanier(panier_personnelDto panier_person) {
        panier_personnelSelect myPanier= new panier_personnelSelect() ;
        String messageRetour ;

         System.out.println("IdEcoleZZZZ"+panier_person.getIdentifiant_ecole());
        System.out.println("idPersonnelZZZZZ"+panier_person.getIdentifiant_personnel());
        System.out.println("myPanier1"+panier_person.toString());

     //  myPanier = checkPanierPers(panier_person.getIdentifiant_ecole(), panier_person.getIdentifiant_personnel());
       Long panierId = checkPanierPers2(panier_person.getIdentifiant_ecole(), panier_person.getIdentifiant_personnel());

        System.out.println("myPanier2Id "+ panierId );
        if(panierId !=0L){
            messageRetour="DEJA DANS LE PANIER" ;
                   } else {
            ecole myecole= new ecole() ;
            sous_attent_personn myperson= new sous_attent_personn() ;
            panier_personnel panier= new panier_personnel() ;
            myecole = ecole.findById(panier_person.getIdentifiant_ecole()) ;
            System.out.println("id_ecoleoooo"+ myecole.toString());
            myperson = sous_attent_personn.findById(panier_person.getIdentifiant_personnel()) ;
            panier.setPanier_personnel_date_creation(LocalDate.now());
            panier.setEcole(myecole);
            panier.setPanier_personnel_statut(String.valueOf(Inscriptions.status.EN_ATTENTE));
            panier.setSous_attent_personn(myperson);
            panier.persist();
            messageRetour="ENREGISTREMENT EFFECTUE AVEC SUCCES" ;
        }

      return messageRetour ;
  }

    public  List<panier_personnelSelect> listpanierByEcole(Long ecoleId,String status){

        List<panier_personnelSelect> mesPaniers ;
        try {
            mesPaniers= (List<panier_personnelSelect>) em.createQuery("SELECT new com.vieecoles.projection.panier_personnelSelect(o.idpanier_personnel_id ,o.panier_personnel_date_creation ,o.ecole.ecoleclibelle ,o.sous_attent_personn.sous_attent_personn_nom ,o.sous_attent_personn.sous_attent_personn_prenom ,o.sous_attent_personn.sous_attent_personnid,o.sous_attent_personn.sous_attent_personn_diplome_recent," +
                            "o.sous_attent_personn.sous_attent_personn_contact,o.sous_attent_personn.sous_attent_personn_date_naissance,o.sous_attent_personn.sous_attent_personn_lien_piece," +
                            "o.sous_attent_personn.sous_attent_personn_lien_autorisation,o.sous_attent_personn.domaine_formation.domaine_formation_libelle,o.sous_attent_personn.fonction.fonctionlibelle,o.sous_attent_personn.sous_attent_personn_email,o.sous_attent_personn.sous_attent_personn_nbre_annee_experience) from panier_personnel o join" +
                            " o.ecole e join o.sous_attent_personn p where o.ecole.ecoleid=:ecoleId and o.panier_personnel_statut=:statusPanier ")
                    .setParameter("ecoleId",ecoleId)
                    .setParameter("statusPanier",status)
                    .getResultList();
        } catch (Exception e) {
            mesPaniers=  null;
        }
        System.out.println("mesPaniersxx"+ mesPaniers);
        return mesPaniers ;
    }






   // @Transactional
    public  panier_personnelSelect checkPanierPers(Long ecoleId, Long idPersonnel){
        panier_personnelSelect mesPaniers = new panier_personnelSelect()  ;
        try {
            mesPaniers= (panier_personnelSelect) em.createQuery("SELECT new com.vieecoles.projection.panier_personnelSelect(o.idpanier_personnel_id ,o.panier_personnel_date_creation ,o.ecole.ecoleclibelle ,o.sous_attent_personn.sous_attent_personn_nom ,o.sous_attent_personn.sous_attent_personn_prenom ,o.sous_attent_personn.sous_attent_personnid,o.sous_attent_personn.sous_attent_personn_diplome_recent," +
                            "o.sous_attent_personn.sous_attent_personn_contact,o.sous_attent_personn.sous_attent_personn_date_naissance,o.sous_attent_personn.sous_attent_personn_lien_piece," +
                            "o.sous_attent_personn.sous_attent_personn_lien_autorisation) from panier_personnel o join" +
                            " o.ecole e join o.sous_attent_personn p where o.ecole.ecoleid=:ecoleId and o.sous_attent_personn.sous_attent_personnid=:idPersonnel ")
                   .setParameter("ecoleId",ecoleId)
                    .setParameter("idPersonnel",idPersonnel)
                    .getSingleResult();
                } catch (Exception e) {
            mesPaniers=  null;
        }
        System.out.println("mesPaniersxx"+ mesPaniers);
        return mesPaniers ;
    }

    public  Long checkPanierPers2(Long ecoleId, Long idPersonnel){
       Long  panierId ;
        try {
        return     panierId= (Long) em.createQuery("SELECT o.idpanier_personnel_id from panier_personnel o join" +
                            " o.ecole e join o.sous_attent_personn p where o.ecole.ecoleid=:ecoleId and o.sous_attent_personn.sous_attent_personnid=:idPersonnel ")
                    .setParameter("ecoleId",ecoleId)
                    .setParameter("idPersonnel",idPersonnel)
                    .getSingleResult();
        } catch (Exception e) {
            return 0L;
        }

    }


    @Transactional
    public  List<panier_personnel> getAllpanier(Long ecoleId){
        List<panier_personnel> mesPaniers ;
        try {
           // System.out.println("entree1 "+id_ecole);
            TypedQuery<panier_personnel> q = (TypedQuery<panier_personnel>) em.createQuery(" select o.idpanier_personnel_id ,  o.ecole.ecoleclibelle ,o.sous_attent_personn.sous_attent_personn_nom ,o.sous_attent_personn.sous_attent_personn_prenom , o.sous_attent_personn.fonction ,o.sous_attent_personn.sous_attent_personn_contact  from panier_personnel o" +
                    " where o.ecole.ecoleid =:ecoleId" );
            mesPaniers =q.setParameter("ecoleId",ecoleId).getResultList() ;
            return mesPaniers;
        } catch (Exception e) {
            return  null;
        }
    }


    @Transactional
    public void    deletePanier(Long identifiant) {
        panier_personnel panier = new panier_personnel() ;
        panier = panier_personnel.findById(identifiant) ;

        try{
            panier.delete();
        }catch (Exception e) {

        }




    }




}
