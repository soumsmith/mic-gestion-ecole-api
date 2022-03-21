package com.vieecoles.services;

import com.vieecoles.entities.pays;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class paysService implements PanacheRepositoryBase<pays, Long> {

   public List<pays> getListpays(){
       return  pays.listAll();
   }
   public  pays findById(Long Id){
       return pays.findById(Id);
   }

   public Response createpays(pays mat) {
       mat.persist();
       return Response.created(URI.create("/pays/" + mat.getPayscode())).build();
   }

   public  pays updatepays(long matId, pays mat){
       pays entity = pays.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setPayscode(mat.getPayscode());
       entity.setPayslibelle(mat.getPayslibelle());
        return  entity;
   }

    public void  deletepays(long matId){
        pays entity = pays.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<pays> search(String Libelle){
       return  pays.find("payslibelle",Libelle).list() ;
   }

    public  long count(){
        return  pays.count();
    }


}
