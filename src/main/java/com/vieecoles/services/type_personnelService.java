package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.type_personnel;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class type_personnelService implements PanacheRepositoryBase<type_personnel, Long> {

   public List<type_personnel> getListTypePersonnel(){
       return  type_personnel.listAll();
   }
   public  type_personnel findById(Long type_personnelId){
       return type_personnel.findById(type_personnelId);
   }

   public Response createtype_personnel(type_personnel type_personn) {
       type_personn.persist();
       return Response.created(URI.create("/type_personnel/" + type_personn.getType_personnelcode())).build();
   }

   public  type_personnel updatetype_personnel(long typePersonnId, type_personnel typ_personn){
       type_personnel entity = type_personnel.findById(typ_personn);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setType_personnelcode(typ_personn.getType_personnelcode());
       entity.setType_personnellibelle(typ_personn.getType_personnellibelle());
       return  entity;
   }

    public void  deletetype_personnel(long type_personnelId){
        type_personnel entity = type_personnel.findById(type_personnelId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<type_personnel> search(String type_personnelLibelle){
       return  type_personnel.find("type_personnellibelle",type_personnelLibelle).list() ;
   }

    public  long count(){
        return  type_personnel.count();
    }


}
