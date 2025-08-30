package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.niveau_etude;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class niveau_etudeService implements PanacheRepositoryBase<niveau_etude, Long> {

   public List<niveau_etude> getListdomaine(){
       return  niveau_etude.listAll();
   }
   public  niveau_etude findById(Long domaineId){
       return niveau_etude.findById(domaineId);
   }

   public Response createdomaine(niveau_etude dom) {
       dom.persist();
       return Response.created(URI.create("/domaine/" + dom.getNiveau_etude_code())).build();
   }

   public  niveau_etude updatedomaine(long domaineId, niveau_etude dom){
       niveau_etude entity = niveau_etude.findById(domaineId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setNiveau_etude_code(dom.getNiveau_etude_code());
       entity.setNiveau_etude_libelle(dom.getNiveau_etude_libelle());
       return  entity;
   }

    public void  deletedomaine(long domId){
        niveau_etude entity = niveau_etude.findById(domId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<niveau_etude> search(String domLibelle){
       return  niveau_etude.find("domainelibelle",domLibelle).list() ;
   }

    public  long count(){
        return  niveau_etude.count();
    }


}
