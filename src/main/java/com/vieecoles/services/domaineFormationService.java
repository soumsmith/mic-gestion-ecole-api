package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.domaine;
import com.vieecoles.entities.domaine_formation;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class domaineFormationService implements PanacheRepositoryBase<domaine_formation, Long> {

   public List<domaine_formation> getListdomaine(){
       return  domaine_formation.listAll();
   }
   public  domaine_formation findById(Long domaineId){
       return domaine_formation.findById(domaineId);
   }

  /*  public Response createdomaine(domaine_formation dom) {
       dom.persist();
       return Response.created(URI.create("/domaine/" + dom.getDomainecode())).build();
   } */

   /* public  domaine_formation updatedomaine(long domaineId, domaine_formation dom){
    domaine_formation entity = domaine_formation.findById(domaineId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setDomainecode(dom.getDomainecode());
       entity.setDomainelibelle(dom.getDomainelibelle());
       return  entity;
   } */

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


   public  domaine_formation searchDomFon(String domLibelle){
    return  domaine_formation.find("domaine_formation_libelle",domLibelle).firstResult() ;
}


    public  long count(){
        return  domaine_formation.count();
    }


}
