package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

//import com.oracle.svm.core.annotate.Inject;
import com.vieecoles.entities.Zone;
import com.vieecoles.entities.domaine;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class zoneService implements PanacheRepositoryBase<Zone, Long> {
    @Inject
    EntityManager em;
   public List<Zone> getListzone(){
       return  Zone.listAll();
   }
   public Zone findById(Long zoneId){
       return domaine.findById(zoneId);
   }

   public Response createzone(Zone zon) {
       zon.persist();
       return Response.created(URI.create("/zone/" + zon.getZonecode())).build();
   }

   public Zone updatezone(long zoneId, Zone zon){
       Zone entity = Zone.findById(zoneId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setZonecode(zon.getZonecode());
       entity.setZonelibelle(zon.getZonelibelle());
       return  entity;
   }

    public void  deletezone(long zonId){
        Zone entity = Zone.findById(zonId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<Zone> search(String Libelle){
       return  Zone.find("zonelibelle",Libelle).list() ;
   }

    public  long count(){
        return  Zone.count();
    }

  /*   public  List<Zone> findZoneByCommune(Long idtyp){
        return    em.createQuery("select o from Zone o join fetch o.mCommune h where h.communeid =:typeObj")
                .setParameter("idtyp",idtyp)
                .getResultList();

    } */

    public  List<Zone> findZoneByCommune(Long idtyp){
        return  Zone.find("commune_communeid",idtyp).list() ;

    }



}
