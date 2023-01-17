package com.vieecoles.services;

import com.vieecoles.dto.objetDto;
import com.vieecoles.dao.entities.objet;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;


@ApplicationScoped
public class objetService implements PanacheRepositoryBase<objet, Long> {
    @Inject
    EntityManager em;

    public  List<objetDto> findAllobjet(){
        return  em.createQuery("select o from objet o join fetch o.type_objet").getResultList() ;

    }
    public  List<objet> findByIdTypeObjet(Long idtyp){

        return    em.createQuery("select o from objet o join fetch o.type_objet h where h.type_objetid =:typeObj")
                .setParameter("typeObj",idtyp)
                .getResultList();

    }

    public  objet  findByIDObjet(Long idpobj){
        return objet.find("objetid",idpobj).singleResult();
    }




   public Response create(objet obj) {

       obj.persist();
       return Response.created(URI.create("/objet/" + obj)).build();
   }

   public  int updateObjet(objet obj){



       if(obj == null) {
           throw new NotFoundException();
       }

       int q = em.createQuery("update objet e set e.type_objet=:typObj , e.objetcode=:codeObje," +
                       "e.objetlibelle=:libelle where e.objetid=: objetId")
               .setParameter("objetId",obj.getObjetid())
               .setParameter("typObj",obj.getType_objet())
               .setParameter("codeObje",obj.getObjetcode())
               .setParameter("libelle",obj.getObjetlibelle()).executeUpdate();
       return  q;
   }

    public void  deletobjet(long objetID){
        objet obj1 = objet.findById(objetID);

        if(obj1 == null) {
            throw new NotFoundException();
        }
        obj1.delete();
    }

   public  List<objet> search(String Libelle){

       return   em.createQuery("select o from objet o where  o.objetlibelle like CONCAT('%',:Libelle ,'%') ")
                .setParameter("Libelle",Libelle).getResultList();


   }

    public  long count(){
        return  objet.count();
    }


}
