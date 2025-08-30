package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.domaine;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class domaineService implements PanacheRepositoryBase<domaine, Long> {

   public List<domaine> getListdomaine(){
       return  domaine.listAll();
   }
   public  domaine findById(Long domaineId){
       return domaine.findById(domaineId);
   }

   public Response createdomaine(domaine dom) {
       dom.persist();
       return Response.created(URI.create("/domaine/" + dom.getDomainecode())).build();
   }

   public  domaine updatedomaine(long domaineId, domaine dom){
       domaine entity = domaine.findById(domaineId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setDomainecode(dom.getDomainecode());
       entity.setDomainelibelle(dom.getDomainelibelle());
       return  entity;
   }

    public void  deletedomaine(long domId){
        domaine entity = domaine.findById(domId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<domaine> search(String domLibelle){
       return  domaine.find("domainelibelle",domLibelle).list() ;
   }


   public  domaine searchDomFon(String domLibelle){
    return  domaine.find("domainelibelle",domLibelle).firstResult() ;
}


    public  long count(){
        return  domaine.count();
    }


}
