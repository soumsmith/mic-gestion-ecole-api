package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.Annee_Scolaire;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class annee_scolaireService implements PanacheRepositoryBase<Annee_Scolaire, Long> {
    @Inject
    EntityManager em;

   public List<Annee_Scolaire> getListNIveau(){
       return  Annee_Scolaire.listAll();
   }


   public Annee_Scolaire findById(Long niveauId){
       return Annee_Scolaire.findById(niveauId);
   }
    public  List<Annee_Scolaire> findAnneScolaireByEcole(String tenant){
       try {
           return    em.createQuery("select o from Annee_Scolaire o  ")
                              .getResultList();
       } catch (Exception e) {
           return  null ;
       }

}


    public Annee_Scolaire findAnneScolaireVisibleByEcole(String tenant){
        try {
            return (Annee_Scolaire) em.createQuery("select o from Annee_Scolaire  o where o.annee_scolaire_visible=:param")
                    .setParameter("param", "1")
                    .getSingleResult();
        } catch (Exception e) {
            return  null ;
        }

    }

   public Response createniveau(Annee_Scolaire niv) {
       niv.persist();
       return Response.created(URI.create("/niveau/" + niv.getAnnee_scolaire_code())).build();
   }

   public Annee_Scolaire updateNiveau(long niveauId, Annee_Scolaire annee_scolaire){
       Annee_Scolaire entity = Annee_Scolaire.findById(niveauId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setAnnee_scolaire_code(annee_scolaire.getAnnee_scolaire_code());
       entity.setAnnee_scolaire_libelle(annee_scolaire.getAnnee_scolaire_libelle());
       return  entity;
   }

    public void  deleteNiveau(long niveauId){
        Annee_Scolaire entity = Annee_Scolaire.findById(niveauId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<Annee_Scolaire> search(String niveauLibelle){
       return  Annee_Scolaire.find("niveaulibelle",niveauLibelle).list() ;
   }

    public  long count(){
        return  Annee_Scolaire.count();
    }


}
