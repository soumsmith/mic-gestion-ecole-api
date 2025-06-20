package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.matiere;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class matiereService implements PanacheRepositoryBase<matiere, Long> {
    @Inject
    EntityManager em;
   public List<matiere> getListMatiere(){
       return  matiere.listAll();
   }


    public List <matiere> ListeMatiereParEcole(String tenantId ){
        TypedQuery<matiere> q = (TypedQuery<matiere>)
                em.createQuery("select o from matiere o join o.tenant t join o.categorie_matiere c" +
                        " where  o.tenant.tenantid =:idtenant ");
        List<matiere> personnelSelect = q.setParameter("idtenant",tenantId).
                getResultList();

        return personnelSelect ;
    }




   public  matiere findById(Long matiereId){
       return matiere.findById(matiereId);
   }

   public Response creatematiere(matiere mat) {
       mat.persist();
       return Response.created(URI.create("/matiere/" + mat.getMatierecode())).build();
   }

   public  matiere updatematiere(long matId, matiere mat){
       matiere entity = matiere.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setMatierecode(mat.getMatierecode());
       entity.setMatierelibelle(mat.getMatierelibelle());
       entity.setMatierecoefficien(mat.getMatierecoefficien());
       return  entity;
   }

    public void  deletemat(long matId){
        matiere entity = matiere.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<matiere> search(String Libelle){
       return  matiere.find("matierelibelle",Libelle).list() ;
   }

    public  long count(){
        return  matiere.count();
    }


}
