package com.vieecoles.services;

import com.vieecoles.dao.entities.domaine;
import com.vieecoles.dao.entities.Zone;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class zoneService implements PanacheRepositoryBase<Zone, Long> {

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


}
