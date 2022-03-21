package com.vieecoles.services.eleves;

import com.vieecoles.entities.operations.details_infos_eleves;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@ApplicationScoped
public class details_info_elevesService implements PanacheRepositoryBase<details_infos_eleves, Integer> {
    @Inject
    EntityManager em;

    public  List<details_infos_eleves> findAlldetailsInfoEleves(){
        return  em.createQuery("select o from details_infos_eleves o join fetch o.categorie_information").getResultList() ;

    }
    public  List<details_infos_eleves> findByIdCategorie(Long idniv){

        return    em.createQuery("select o from details_infos_eleves o join fetch o.categorie_information h where h.categorie_informationid =:idniv")
                .setParameter("idniv",idniv)
                .getResultList();

    }

    public  details_infos_eleves  findByIDdetailsInfosEleves(Long idpobj){
        return details_infos_eleves.find("details_infos_elevesid",idpobj).singleResult();
    }




   public Response create(details_infos_eleves obj) {

       obj.persist();
       return Response.created(URI.create("/details_infos_eleves/" + obj)).build();
   }

   public  int updatdetailsIbfosEleves(details_infos_eleves obj){



       if(obj == null) {
           throw new NotFoundException();
       }

       int q = em.createQuery("update details_infos_eleves e set e.details_infos_elevescode=:code, e.details_infos_eleveslibelle=:libelle" +
                       ", e.details_infos_elevesvaleur=:valeur,e.eleve=:eleve  where e.details_infos_elevesid=: idEl ")
                .setParameter("code",obj.getDetails_infos_elevescode())
               .setParameter("idEl",obj.getDetails_infos_elevesid())
               .setParameter("libelle",obj.getDetails_infos_eleveslibelle())
               .setParameter("valeur",obj.getDetails_infos_elevesid())
               .setParameter("eleve",obj.getEleve()).executeUpdate();
       return  q;
   }

    public void  deletInfoElev(long ID){
        details_infos_eleves obj1 = details_infos_eleves.findById(ID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<details_infos_eleves> search(String Libelle){

       return   em.createQuery("select o from details_infos_eleves o where  o.details_infos_eleveslibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();

   }

    public  long count(){
        return  details_infos_eleves.count();
    }


}
