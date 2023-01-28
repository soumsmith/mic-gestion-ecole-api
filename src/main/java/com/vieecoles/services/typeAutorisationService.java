package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.domaine_formation;
import com.vieecoles.entities.type_autorisation;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class typeAutorisationService implements PanacheRepositoryBase<type_autorisation, Long> {

   public List<type_autorisation> getListTypeAutorisation(){
       return  type_autorisation.listAll();
   }
   public  type_autorisation findById(Long id){
       return type_autorisation.findById(id);
   }

   public Response createTypeAutorisation(type_autorisation typAut) {
       typAut.persist();
       return Response.created(URI.create("/domaine/" + typAut.getType_autorisation_code())).build();
   }

   public  type_autorisation updatetypeAuto(long id, type_autorisation dom){
       type_autorisation entity = domaine_formation.findById(id);
       if(entity == null) {
           throw new NotFoundException();
       }
       /*entity.setDomaine_formation_code(dom.getDomaine_formation_code());
       entity.setDomaine_formation_libelle(dom.getDomaine_formation_libelle());*/
       return  entity;
   }

    public void  deletedomaine(long domId){
        domaine_formation entity = domaine_formation.findById(domId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<domaine_formation> search(String domLibelle){
       return  domaine_formation.find("domainelibelle",domLibelle).list() ;
   }

    public  long count(){
        return  domaine_formation.count();
    }


}
