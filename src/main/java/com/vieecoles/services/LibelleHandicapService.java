package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

import com.vieecoles.entities.Libellehandicap;
import com.vieecoles.entities.objet;

import java.util.List;


@ApplicationScoped
public class LibelleHandicapService implements PanacheRepositoryBase<Libellehandicap, Long> {
    @Inject
    EntityManager em;

    public  List<Libellehandicap> findHandicap(){
        return  em.createQuery("select o from Libellehandicap o ").getResultList() ;
    }
    public  Libellehandicap findByIdLibelle(Long idtyp){

        return    Libellehandicap.findById(idtyp);

    }






   public Libellehandicap create(Libellehandicap obj) {
 try {
     obj.persist();
     return  obj ;
 } catch (Exception e) {
       return  null;
   }
   }

   public  int updateObjet(Libellehandicap obj){



       if(obj == null) {
           throw new NotFoundException();
       }

       int q = em.createQuery("update Libellehandicap e set e.libelleHandicapLibelle=: libelle, e.libelleHandicode=: codeHandica " +
                       "where  e.libelleHandicapid=: idHandi" )
               .setParameter("libelle",obj.getLibelleHandicapLibelle())
               .setParameter("codeHandica",obj.getLibelleHandicode())
               .setParameter("idHandi",obj.getLibelleHandicapid())
               .executeUpdate();
       return  q;
   }

    public void  deletobjet(long objetID){
        Libellehandicap obj1 = Libellehandicap.findById(objetID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<Libellehandicap> search(String Libelle){

       return   em.createQuery("select o from Libellehandicap o where  o.libelleHandicapLibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();


   }

    public  long count(){
        return  objet.count();
    }


}
