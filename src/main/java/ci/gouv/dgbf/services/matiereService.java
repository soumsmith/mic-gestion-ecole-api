package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.matiere;
import ci.gouv.dgbf.entities.niveau;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class matiereService implements PanacheRepositoryBase<matiere, Long> {

   public List<matiere> getListMatiere(){
       return  matiere.listAll();
   }
   public  matiere findById(Long matiereId){
       return matiere.findById(matiereId);
   }

   public Response creatematiere(matiere mat) {
       mat.persist();
       return Response.created(URI.create("/matiere/" + mat.getMatierecode())).build();
   }

   public  matiere updatematiere(long matId, matiere mat){
       matiere entity = matiere.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setMatierecode(mat.getMatierecode());
       entity.setMatierecode(mat.getMatierelibelle());
       entity.setMatierecoefficien(mat.getMatierecoefficien());
       return  entity;
   }

    public void  deletemat(long matId){
        matiere entity = matiere.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<matiere> search(String Libelle){
       return  matiere.find("matierelibelle",Libelle).list() ;
   }

    public  long count(){
        return  matiere.count();
    }


}
