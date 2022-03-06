package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.fonction;
import ci.gouv.dgbf.entities.matiere;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class fonctionService implements PanacheRepositoryBase<fonction, Long> {

   public List<fonction> getListfonction(){
       return  matiere.listAll();
   }
   public  fonction findById(Long Id){
       return fonction.findById(Id);
   }

   public Response createfonction(fonction mat) {
       mat.persist();
       return Response.created(URI.create("/fonction/" + mat.getFonctioncode())).build();
   }

   public  fonction updatefonction(long matId, fonction mat){
       fonction entity = fonction.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setFonctioncode(mat.getFonctioncode());
       entity.setFonctionlibelle(mat.getFonctionlibelle());
        return  entity;
   }

    public void  deletefonction(long matId){
        fonction entity = fonction.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<fonction> search(String Libelle){
       return  fonction.find("fonctionlibelle",Libelle).list() ;
   }

    public  long count(){
        return  fonction.count();
    }


}
