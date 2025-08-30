package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.groupe_ecole;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class groupe_ecoleService implements PanacheRepositoryBase<groupe_ecole, Long> {

   public List<groupe_ecole> getListgroupeEcole(){
       return  groupe_ecole.listAll();
   }
   public  groupe_ecole findById(Long Id){
       return groupe_ecole.findById(Id);
   }

   public Response creategroupeEcole(groupe_ecole mat) {
       mat.persist();
       return Response.created(URI.create("/groupe-ecole/" + mat.getGroupe_ecolecode())).build();
   }

   public  groupe_ecole updategroupeEcole(long matId, groupe_ecole mat){
       groupe_ecole entity = groupe_ecole.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setGroupe_ecolecode(mat.getGroupe_ecolecode());
       entity.setGroupe_ecolelibelle(mat.getGroupe_ecolelibelle());
        return  entity;
   }

    public void  deletegroupeEcole(long matId){
        groupe_ecole entity = groupe_ecole.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<groupe_ecole> search(String Libelle){
       return  groupe_ecole.find("groupe_ecolelibelle",Libelle).list() ;
   }

    public  long count(){
        return  groupe_ecole.count();
    }


}
