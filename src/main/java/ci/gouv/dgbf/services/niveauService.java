package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.niveau;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
@ApplicationScoped
public class niveauService implements PanacheRepositoryBase<niveau, Long> {

   public List<niveau> getListNIveau(){
       return  niveau.listAll();
   }
   public  niveau findById(Long niveauId){
       return niveau.findById(niveauId);
   }

   public Response createniveau(niveau niv) {
       niv.persist();
       return Response.created(URI.create("/niveau/" + niv.getNiveauid())).build();
   }

   public  niveau updateNiveau(long niveauId, niveau niv){
       niveau entity = niveau.findById(niveauId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setNiveaucode(niv.getNiveaucode());
       entity.setNiveaulibelle(niv.getNiveaulibelle());
       return  entity;
   }

    public void  deleteNiveau(long niveauId){
        niveau entity = niveau.findById(niveauId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<niveau> search(String niveauLibelle){
       return  niveau.find("niveaulibelle",niveauLibelle).list() ;
   }

    public  long count(){
        return  niveau.count();
    }


}
