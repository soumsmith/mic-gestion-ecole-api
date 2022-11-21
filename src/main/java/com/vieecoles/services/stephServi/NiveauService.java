package com.vieecoles.services;

import com.vieecoles.entities.Niveau;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
@RequestScoped
public class NiveauService implements PanacheRepositoryBase<Niveau, Long> {

   public List<Niveau> getList(){
       return  Niveau.listAll();
   }
   public  Niveau findById(Long niveauId){
       return Niveau.findById(niveauId);
   }

   public Response create(Niveau niv) {
       niv.persist();
       return Response.created(URI.create("/niveau/" + niv.getId())).build();
   }

   public  Niveau update(long niveauId, Niveau niv){
       Niveau entity = Niveau.findById(niveauId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setCode(niv.getCode());
       entity.setLibelle(niv.getLibelle());
       return  entity;
   }

    public void  delete(long niveauId){
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
