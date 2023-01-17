package com.vieecoles.services.operations;

import com.vieecoles.dao.entities.objet;
import com.vieecoles.dao.entities.operations.ville;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@ApplicationScoped
public class villeService implements PanacheRepositoryBase<ville, Long> {
    @Inject
    EntityManager em;

    public  List<ville> findAllville(){
        return  em.createQuery("select o from ville o join fetch o.pays").getResultList() ;

    }
    public  List<ville> findByIdPays(Long idtyp){

        return    em.createQuery("select o from ville o join fetch o.pays h where h.paysid =:typeObj")
                .setParameter("typeObj",idtyp)
                .getResultList();

    }

    public  ville  findByIDville(Long idpobj){
        return objet.find("villeid",idpobj).singleResult();
    }




   public Response create(ville obj) {

       obj.persist();
       return Response.created(URI.create("/objet/" + obj)).build();
   }

   public  int updateObjet(ville obj){



       if(obj == null) {
           throw new NotFoundException();
       }

       int q = em.createQuery("update ville e set e.pays=:pays , e.villecode=:code," +
                       "e.villelibelle=:libelle where e.villeid=: villeId")
               .setParameter("pays",obj.getPays())
               .setParameter("code",obj.getVillecode())
               .setParameter("libelle",obj.getVillelibelle())
               .setParameter("villeId",obj.getVilleid()).executeUpdate();
       return  q;
   }

    public void  deletobjet(long objetID){
        ville obj1 = ville.findById(objetID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<ville> search(String Libelle){

       return   em.createQuery("select o from ville o where  o.villelibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();


   }

    public  long count(){
        return  ville.count();
    }


}
