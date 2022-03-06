package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.cycle;
import ci.gouv.dgbf.entities.domaine;
import ci.gouv.dgbf.entities.zone;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class cycleService implements PanacheRepositoryBase<cycle, Long> {

   public List<cycle> getListcycle(){
       return  cycle.listAll();
   }
   public  cycle findById(Long cycleId){
       return cycle.findById(cycleId);
   }

   public Response createcycle(cycle zon) {
       zon.persist();
       return Response.created(URI.create("/zone/" + zon.getCyclecode())).build();
   }

   public  cycle updateCycle(long zoneId, cycle zon){
       cycle entity = cycle.findById(zoneId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setCyclecode(zon.getCyclecode());
       entity.setCyclelibelle(zon.getCyclelibelle());
       return  entity;
   }

    public void  deletecycle(long zonId){
        cycle entity = cycle.findById(zonId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<cycle> search(String Libelle){
       return  cycle.find("cyclelibelle",Libelle).list() ;
   }

    public  long count(){
        return  cycle.count();
    }


}
