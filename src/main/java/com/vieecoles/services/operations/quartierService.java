package com.vieecoles.services.operations;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.operations.quartier;

import java.net.URI;
import java.util.List;


@ApplicationScoped
public class quartierService implements PanacheRepositoryBase<quartier, Long> {
    @Inject
    EntityManager em;

    public  List<quartier> findAllquartier(){
        return  em.createQuery("select o from quartier o join fetch o.commune").getResultList() ;

    }
    public  List<quartier> findByIdcommune(Long idtyp){

        return    em.createQuery("select o from quartier o join fetch o.commune h where h.communeid =:typeObj")
                .setParameter("typeObj",idtyp)
                .getResultList();

    }

    public  quartier  findByIDquartier(Long idpobj){
        return quartier.find("quartierid",idpobj).singleResult();
    }




   public Response create(quartier obj) {

       obj.persist();
       return Response.created(URI.create("/quartier/" + obj)).build();
   }

   public  int updatequartier(quartier obj){

       if(obj == null) {
           throw new NotFoundException();
       }

       int q = em.createQuery("update quartier e set e.commune=:commune , e.quartiercode=:code," +
                       "e.quartierlibelle=:libelle where e.quartierid=: quartiereId")
               .setParameter("commune",obj.getCommune())
               .setParameter("code",obj.getQuartiercode())
               .setParameter("libelle",obj.getQuartierlibelle())
               .setParameter("quartiereId",obj.getQuartierid()).executeUpdate();
       return  q;
   }

    public void  deletquartier(long objetID){
        quartier obj1 = quartier.findById(objetID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<quartier> search(String Libelle){

       return   em.createQuery("select o from quartier o where  o.quartierlibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();


   }

    public  long count(){
        return  quartier.count();
    }


}
