package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.pays;
import ci.gouv.dgbf.entities.utilisateur;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class utilisateurService implements PanacheRepositoryBase<utilisateur, Long> {

   public List<utilisateur> getListutlisateur(){
       return  utilisateur.listAll();
   }
   public  utilisateur findById(Long Id){
       return utilisateur.findById(Id);
   }

   public Response createutilisateur(utilisateur mat) {
       mat.persist();
       return Response.created(URI.create("/utilisateur/" + mat.getUtilisateurcode())).build();
   }

   public  utilisateur updateutilisateur(long matId, utilisateur mat){
       utilisateur entity = utilisateur.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setUtilisateurcode(mat.getUtilisateurcode());
       entity.setUtilisateurlibelle(mat.getUtilisateurcode());
        return  entity;
   }

    public void  deleteutilisateur(long matId){
        utilisateur entity = utilisateur.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<utilisateur> search(String Libelle){
       return  utilisateur.find("utilisateurlibelle",Libelle).list() ;
   }

    public  long count(){
        return  utilisateur.count();
    }


}
