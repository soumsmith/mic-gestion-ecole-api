package com.vieecoles.services.eleves;

import com.vieecoles.entities.operations.categorie_information;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class categorie_informationService implements PanacheRepositoryBase<categorie_information, Integer> {

    @Inject
    EntityManager em;



   public List<categorie_information> getListCategorieInformation(){
       return  categorie_information.listAll();
   }




   public  categorie_information getCategorieInById(Long Id){
       return categorie_information.findById(Id);
   }

   public Response createCategorieInformation(categorie_information prof) {
       prof.persist();
       return Response.created(URI.create("/categorie_information/" + prof.getCategorie_informationcode())).build();
   }

   public  categorie_information updatecategorieInformation(categorie_information prof){
       categorie_information entity = categorie_information.findById(prof.getCategorie_informationid());
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setCategorie_informationcode(prof.getCategorie_informationcode());
       entity.setCategorie_informationlibelle(prof.getCategorie_informationlibelle());
       return  entity;
   }

    public void  deletecategorie_information(long proId){
        categorie_information entity = categorie_information.findById(proId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

    public  List<categorie_information> search(String Libelle){
        return   em.createQuery("select o from categorie_information o where  o.categorie_informationlibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();
    }

    public  long count(){
        return  categorie_information.count();
    }


}
