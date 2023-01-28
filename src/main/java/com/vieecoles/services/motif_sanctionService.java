package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.motif_sanction;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class motif_sanctionService implements PanacheRepositoryBase<motif_sanction, Long> {

   public List<motif_sanction> getListmotifsanction(){
       return  motif_sanction.listAll();
   }
   public  motif_sanction findById(Long Id){
       return motif_sanction.findById(Id);
   }

   public Response createmotifSanction(motif_sanction mot) {
       mot.persist();
       return Response.created(URI.create("/motif_sanction/" + mot.getMotif_sanctioncode())).build();
   }

   public  motif_sanction updatemotif_sanction(long Id, motif_sanction mot){
       motif_sanction entity = motif_sanction.findById(Id);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setMotif_sanctioncode(mot.getMotif_sanctioncode());
       entity.setMotif_sanctionlibelle(mot.getMotif_sanctionlibelle());

       return  entity;
   }

    public void  deleteMotifsanction(long Id){
        motif_sanction entity = motif_sanction.findById(Id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<motif_sanction> search(String Libelle){
       return  motif_sanction.find("motif_sanctionlibelle",Libelle).list() ;
   }

    public  long count(){
        return  motif_sanction.count();
    }


}
