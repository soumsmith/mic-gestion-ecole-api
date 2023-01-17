package com.vieecoles.services;

import com.vieecoles.dao.entities.tenant;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped

public class tenantService implements PanacheRepositoryBase<tenant, String> {

   public List<tenant> getListTenat(){
       return  tenant.listAll();
   }



   public  tenant findById(Long Id){
       return tenant.findById(Id);
   }

   public tenant createtenant (tenant mat) {
       try {
           mat.persist();
           return mat  ;
       } catch (Exception e) {
           return null;
       }

   }

   public  tenant updatetenant(  String tenantId ,tenant myTenat  ){
       tenant entity = tenant.findById(tenantId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setTenantid(tenantId);
       entity.setTenantcode(myTenat.getTenantcode());
       entity.setTenantlibelle(myTenat.getTenantlibelle());
        return  entity;
   }

    public void  deletetenant(String tenantId){
        tenant entity = tenant.findById(tenantId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<tenant> search(String Libelle){
       return  tenant.find("tenantlibelle",Libelle).list() ;
   }

    public  long count(){
        return  tenant.count();
    }


}
