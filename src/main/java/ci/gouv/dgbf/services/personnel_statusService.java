package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.fonction;
import ci.gouv.dgbf.entities.matiere;
import ci.gouv.dgbf.entities.personnel_status;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class personnel_statusService implements PanacheRepositoryBase<personnel_status, Long> {

   public List<personnel_status> getListpersonnelStatus(){
       return  personnel_status.listAll();
   }
   public  personnel_status findById(Long Id){
       return personnel_status.findById(Id);
   }

   public Response createpersonnelStatus(personnel_status mat) {
       mat.persist();
       return Response.created(URI.create("/personnel_status/" + mat.getPersonnel_statuscode())).build();
   }

   public  personnel_status updatepersonnelStatus(long matId, personnel_status mat){
       personnel_status entity = personnel_status.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setPersonnel_statuscode(mat.getPersonnel_statuscode());
       entity.setPersonnel_statuslibelle(mat.getPersonnel_statuslibelle());
        return  entity;
   }

    public void  deletepersonnStatu(long matId){
        personnel_status entity = personnel_status.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<personnel_status> search(String Libelle){
       return  personnel_status.find("personnel_statuslibelle",Libelle).list() ;
   }

    public  long count(){
        return  personnel_status.count();
    }


}
