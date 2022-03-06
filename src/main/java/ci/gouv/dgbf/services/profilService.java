package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.domaine;
import ci.gouv.dgbf.entities.profil;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class profilService implements PanacheRepositoryBase<profil, Long> {

   public List<profil> getListprofil(){
       return  profil.listAll();
   }
   public  profil findById(Long proId){
       return domaine.findById(proId);
   }

   public Response createprofil(profil prof) {
       prof.persist();
       return Response.created(URI.create("/profil/" + prof.getProfilcode())).build();
   }

   public  profil updateprofil(long proId, profil prof){
       profil entity = profil.findById(proId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setProfilcode(prof.getProfilcode());
       entity.setProfil_libelle(prof.getProfilcode());
       return  entity;
   }

    public void  deleteprofil(long proId){
        profil entity = profil.findById(proId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<profil> search(String Libelle){
       return  profil.find("profil_libelle",Libelle).list() ;
   }

    public  long count(){
        return  profil.count();
    }


}
