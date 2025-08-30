package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.personnel_status;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class personnel_statusService implements PanacheRepositoryBase<personnel_status, Long> {

   public List<personnel_status> getListpersonnelStatus(){
       return  personnel_status.listAll();
   }
   public  personnel_status findById(Long Id){
       return personnel_status.findById(Id);
   }

   public Response createpersonnelStatus(personnel_status mat) {
       mat.persist();
       return Response.created(URI.create("/personnel_status/" + mat.getPersonnel_statuscode())).build();
   }

   public  personnel_status updatepersonnelStatus(long matId, personnel_status mat){
       personnel_status entity = personnel_status.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
      }
       entity.setPersonnel_statuscode(mat.getPersonnel_statuscode());
       entity.setPersonnel_statulibelle(mat.getPersonnel_statulibelle());
        return  entity;
   }

    public void  deletepersonnStatu(long matId){
        personnel_status entity = personnel_status.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<personnel_status> search(String Libelle){
       return  personnel_status.find("personnel_statuslibelle",Libelle).list() ;
   }

    public  long count(){
        return  personnel_status.count();
    }


}
