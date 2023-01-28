package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.typedurre;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class typedurreService implements PanacheRepositoryBase<typedurre, Long> {

   public List<typedurre> getListtypedurre(){
       return  typedurre.listAll();
   }
   public  typedurre findById(Long Id){
       return typedurre.findById(Id);
   }

   public Response createtypedurre(typedurre mat) {
       mat.persist();
       return Response.created(URI.create("/typedurre/" + mat.getTypedurrecode())).build();
   }

   public  typedurre updatetypedurre(long matId, typedurre mat){
       typedurre entity = typedurre.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setTypedurrecode(mat.getTypedurrecode());
       entity.setTypedurrelibelle(mat.getTypedurrelibelle());
        return  entity;
   }

    public void  deletetypedurre(long matId){
        typedurre entity = typedurre.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<typedurre> search(String Libelle){
       return  typedurre.find("typedurrelibelle",Libelle).list() ;
   }

    public  long count(){
        return  typedurre.count();
    }


}
