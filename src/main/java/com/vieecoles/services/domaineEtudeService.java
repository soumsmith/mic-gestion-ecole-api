package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.domaine_formation;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class domaineEtudeService implements PanacheRepositoryBase<domaine_formation, Long> {

   public List<domaine_formation> getListdomaine(){
       return  domaine_formation.listAll();
   }
   public  domaine_formation findById(Long domaineId){
       return domaine_formation.findById(domaineId);
   }

   public Response createdomaine(domaine_formation dom) {
       dom.persist();
       return Response.created(URI.create("/domaine/" + dom.getDomaine_formation_code())).build();
   }

   public  domaine_formation updatedomaine(long domaineId, domaine_formation dom){
       domaine_formation entity = domaine_formation.findById(domaineId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setDomaine_formation_code(dom.getDomaine_formation_code());
       entity.setDomaine_formation_libelle(dom.getDomaine_formation_libelle());
       return  entity;
   }

    public void  deletedomaine(long domId){
        domaine_formation entity = domaine_formation.findById(domId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<domaine_formation> search(String domLibelle){
       return  domaine_formation.find("domainelibelle",domLibelle).list() ;
   }

    public  long count(){
        return  domaine_formation.count();
    }


}
