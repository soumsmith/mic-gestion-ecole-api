package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.fonction;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class fonctionService implements PanacheRepositoryBase<fonction, Long> {
    @Inject
    EntityManager em;
   public List<fonction> getListfonction(){
       return  fonction.listAll();
   }
   public  fonction findById(Long Id){
       return fonction.findById(Id);
   }

   public Response createfonction(fonction mat) {
       mat.persist();
       return Response.created(URI.create("/fonction/" + mat.getFonctioncode())).build();
   }

   public  fonction updatefonction(long matId, fonction mat){
       fonction entity = fonction.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setFonctioncode(mat.getFonctioncode());
       entity.setFonctionlibelle(mat.getFonctionlibelle());
        return  entity;
   }

    public void  deletefonction(long matId){
        fonction entity = fonction.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
    public  fonction getFoncID(String Libelle){
        return (fonction) fonction.find("fonctionlibelle",Libelle).singleResult();
    }
   public  List<fonction> search(String Libelle){
       return  fonction.find("fonctionlibelle",Libelle).list() ;
   }

    public  long count(){
        return  fonction.count();
    }
    public  List<fonction> findFonctionWithoutFondateur(String libelle ){
        TypedQuery<fonction> q = (TypedQuery<fonction>) em.createQuery( "SELECT  o from fonction o where o.fonctionlibelle not like :libelle ");
        List<fonction> listfonction = q.setParameter("libelle" ,libelle).
                               getResultList();
        return  listfonction;
    }

}
