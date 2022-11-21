package com.vieecoles.services.souscription;

import com.vieecoles.dao.entities.domaine_formation;
import com.vieecoles.dao.entities.fonction;
import com.vieecoles.dao.entities.niveau_etude;
import com.vieecoles.dao.entities.operations.*;
import com.vieecoles.dto.panier_personnelDto;
import com.vieecoles.dto.sous_attent_personnDto;
import com.vieecoles.dto.souscriptionValidationDto;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@ApplicationScoped
public class PanierPersonnelService implements PanacheRepositoryBase<sous_attent_personn, Long> {
    @Inject
    EntityManager em;

    @Transactional
  public String  creeerPanier(panier_personnelDto panier_person) {
      ecole myecole= new ecole() ;
      String messageRetour ;
      sous_attent_personn myperson= new sous_attent_personn() ;
      panier_personnel panier= new panier_personnel() ;
      myecole =ecole.findById(panier_person.getIdentifiant_ecole()) ;
      myperson = sous_attent_personn.findById(panier_person.getIdpanier_personnel_id()) ;
      panier.setPanier_personnel_date_creation(LocalDate.now());
      panier.setEcole(myecole);
      panier.setSous_attent_personn(myperson);
      panier.persist();
      messageRetour="ENREGISTREMENT EFFECTUE AVEC SUCCES" ;
      return messageRetour ;
  }

    public  List<panier_personnel> getAllpanierByEcole(Long id_ecole){
        try {
            return (List<panier_personnel>) em.createQuery("select o from  panier_personnel  o where o.ecole.ecoleid=:id_ecole ")
                    .setParameter("id_ecole",id_ecole)
                    .getResultList() ;
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
