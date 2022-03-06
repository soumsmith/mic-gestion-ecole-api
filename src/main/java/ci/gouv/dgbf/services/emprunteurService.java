package ci.gouv.dgbf.services;

import ci.gouv.dgbf.entities.emprunteur;
import ci.gouv.dgbf.entities.pays;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class emprunteurService implements PanacheRepositoryBase<emprunteur, Long> {

   public List<emprunteur> getListemprunteur(){
       return  emprunteur.listAll();
   }
   public  emprunteur findById(Long Id){
       return emprunteur.findById(Id);
   }

   public Response createemprunteur(emprunteur mat) {
       mat.persist();
       return Response.created(URI.create("/emprunteur/" + mat.getEmprunteurcode())).build();
   }

   public  emprunteur updateemprunteur(long matId, emprunteur mat){
       emprunteur entity = emprunteur.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setEmprunteurcode(mat.getEmprunteurcode());
       entity.setEmprunteurnom(mat.getEmprunteurnom());
       entity.setEmprunteurprenom(mat.getEmprunteurprenom());
       entity.setEmprunteurdatenaissance(mat.getEmprunteurdatenaissance());
        return  entity;
   }

    public void  deleteemprunteur(long matId){
        emprunteur entity = emprunteur.findById(matId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<emprunteur> search(String nom){
       return  emprunteur.find("emprunteurnom",nom).list() ;
   }

    public  long count(){
        return  emprunteur.count();
    }


}
