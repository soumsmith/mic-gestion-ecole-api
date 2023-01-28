package com.vieecoles.services.operations;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.operations.commune;

import java.net.URI;
import java.util.List;


@ApplicationScoped
public class communeService implements PanacheRepositoryBase<commune, Long> {
    @Inject
    EntityManager em;

    public  List<commune> findAllcommune(){
        return  em.createQuery("select o from commune o join fetch o.ville").getResultList() ;

    }
    public  List<commune> findByIdville(Long idtyp){

        return    em.createQuery("select o from commune o join fetch o.ville h where h.villeid =:typeObj")
                .setParameter("typeObj",idtyp)
                .getResultList();

    }

    public  commune  findByIDcommune(Long idpobj){
        return commune.find("communeid",idpobj).singleResult();
    }




   public Response create(commune obj) {

       obj.persist();
       return Response.created(URI.create("/commune/" + obj)).build();
   }

   public  int updatecommune(commune obj){



       if(obj == null) {
           throw new NotFoundException();
       }

       int q = em.createQuery("update commune e set e.ville=:ville , e.communecode=:code," +
                       "e.communelibelle=:libelle where e.communeid=: communeeId")
               .setParameter("ville",obj.getVille())
               .setParameter("code",obj.getCommunecode())
               .setParameter("libelle",obj.getCommunelibelle())
               .setParameter("communeeId",obj.getCommuneid()).executeUpdate();
       return  q;
   }

    public void  deletobjet(long objetID){
        commune obj1 = commune.findById(objetID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<commune> search(String Libelle){

       return   em.createQuery("select o from commune o where  o.communelibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();


   }

    public  long count(){
        return  commune.count();
    }


}
