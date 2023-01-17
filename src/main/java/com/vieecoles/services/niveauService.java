package com.vieecoles.services;

import com.vieecoles.dao.entities.Niveau;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
@ApplicationScoped
public class niveauService implements PanacheRepositoryBase<Niveau, Long> {
    @Inject
    EntityManager em;

   public List<Niveau> getListNIveau(){
       return  Niveau.listAll();
   }


   public Niveau findById(Long niveauId){
       return Niveau.findById(niveauId);
   }
    public  List<Niveau> findNiveauByEcole(String tenant){
       try {
           return    em.createQuery("select o from niveau o ")
                           .getResultList();
       } catch (Exception e) {
           return  null ;
       }





}


   public Response createniveau(Niveau niv) {
       niv.persist();
       return Response.created(URI.create("/niveau/" + niv.getNiveauid())).build();
   }

   public Niveau updateNiveau(long niveauId, Niveau niv){
       Niveau entity = Niveau.findById(niveauId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setNiveaucode(niv.getNiveaucode());
       entity.setNiveaulibelle(niv.getNiveaulibelle());
       return  entity;
   }

    public void  deleteNiveau(long niveauId){
        Niveau entity = Niveau.findById(niveauId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<Niveau> search(String niveauLibelle){
       return  Niveau.find("niveaulibelle",niveauLibelle).list() ;
   }

    public  long count(){
        return  Niveau.count();
    }


}
