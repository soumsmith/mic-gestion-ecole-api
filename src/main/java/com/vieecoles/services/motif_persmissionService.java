package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.motif_permission;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class motif_persmissionService implements PanacheRepositoryBase<motif_permission, Long> {

   public List<motif_permission> getListmotifPermission(){
       return  motif_permission.listAll();
   }
   public  motif_permission findById(Long Id){
       return motif_permission.findById(Id);
   }

   public Response createmotifPermission(motif_permission mot) {
       mot.persist();
       return Response.created(URI.create("/motif_permission/" + mot.getMotif_permissioncode())).build();
   }

   public  motif_permission updatemotif_permission(long Id, motif_permission mot){
       motif_permission entity = motif_permission.findById(Id);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setMotif_permissioncode(mot.getMotif_permissioncode());
       entity.setMotif_permissionlibelle(mot.getMotif_permissionlibelle());

       return  entity;
   }

    public void  deleteMotifPermiss(long Id){
        motif_permission entity = motif_permission.findById(Id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<motif_permission> search(String Libelle){
       return  motif_permission.find("motif_permissionlibelle",Libelle).list() ;
   }

    public  long count(){
        return  motif_permission.count();
    }


}
