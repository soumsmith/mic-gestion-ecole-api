package com.vieecoles.services;

import com.vieecoles.entities.domaine;
import com.vieecoles.entities.zone;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class zoneService implements PanacheRepositoryBase<zone, Long> {

   public List<zone> getListzone(){
       return  zone.listAll();
   }
   public  zone findById(Long zoneId){
       return domaine.findById(zoneId);
   }

   public Response createzone(zone zon) {
       zon.persist();
       return Response.created(URI.create("/zone/" + zon.getZonecode())).build();
   }

   public  zone updatezone(long zoneId, zone zon){
       zone entity = zone.findById(zoneId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setZonecode(zon.getZonecode());
       entity.setZonelibelle(zon.getZonelibelle());
       return  entity;
   }

    public void  deletezone(long zonId){
        zone entity = zone.findById(zonId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<zone> search(String Libelle){
       return  zone.find("zonelibelle",Libelle).list() ;
   }

    public  long count(){
        return  zone.count();
    }


}
