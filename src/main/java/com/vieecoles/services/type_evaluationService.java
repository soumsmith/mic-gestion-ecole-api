package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.type_evaluation;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class type_evaluationService implements PanacheRepositoryBase<type_evaluation, Long> {

   public List<type_evaluation> getListevaluation(){
       return  type_evaluation.listAll();
   }
   public  type_evaluation findById(Long type_evalId){
       return type_evaluation.findById(type_evalId);
   }

   public Response createtype_eval(type_evaluation type_eval) {
       type_eval.persist();
       return Response.created(URI.create("/type_evaluation/" + type_eval.getType_evaluationcode())).build();
   }

   public  type_evaluation updatetype_eval(long type_evalId, type_evaluation typ_eva){
       type_evaluation entity = type_evaluation.findById(type_evalId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setType_evaluationcode(typ_eva.getType_evaluationcode());
       entity.setType_evaluationlibelle(typ_eva.getType_evaluationlibelle());
       return  entity;
   }

    public void  deletetype_eval(long type_evalId){
        type_evaluation entity = type_evaluation.findById(type_evalId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<type_evaluation> search(String Libelle){
       return  type_evaluation.find("type_evaluationlibelle",Libelle).list() ;
   }

    public  long count(){
        return  type_evaluation.count();
    }


}
