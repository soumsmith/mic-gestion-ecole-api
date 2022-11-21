package com.vieecoles.services.eleves;

import com.vieecoles.dto.eleveDto;
import com.vieecoles.entities.cycle;
import com.vieecoles.entities.objet;
import com.vieecoles.entities.operations.eleve;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EleveService implements PanacheRepositoryBase<eleve, Long> {
    @Inject
    EntityManager em;
   public List<eleve> getListcycle(){
       return  eleve.listAll();
   }
   public  eleve findById(Long cycleId){
       return eleve.findById(cycleId);
   }

   public Response createeleve(eleve elev) {
       elev.persist();
       return Response.created(URI.create("/eleve/" + elev.getElevecode())).build();
   }

    public  List<eleveDto> getAlleleveParent(){

       TypedQuery<eleveDto> q = em.createQuery(
                "SELECT new com.vieecoles.dto.eleveDto(o.elevenom,o.eleveprenom , p.parentprenom , p.parentnom  ) from eleve o join  o.parents p ",
               eleveDto.class);
        List<eleveDto> dtoList = q.getResultList();
       return dtoList;

    }




    public  List<eleve> listEleve(){

        return    em.createQuery("select o from eleve o join o.parents p")

                .getResultList();

    }

   public  eleve updatEeleve(long EleveID, eleve elev){
       eleve entity = eleve.findById(EleveID);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setEleve_mail(elev.getEleve_mail());
       entity.setEleveadresse(elev.getEleveadresse());
       entity.setElevecode(elev.getElevecode());
       entity.setEleve_numero_extrait_naiss(elev.getEleve_numero_extrait_naiss());
       entity.setElevedate_etabli_extrait_naiss(elev.getElevedate_etabli_extrait_naiss());
       entity.setElevelieu_etabliss_etrait_naissance(elev.getElevelieu_etabliss_etrait_naissance());
       entity.setElevecellulaire(elev.getElevecellulaire());
       entity.setElevedate_naissance(elev.getElevedate_naissance());
       entity.setElevelieu_naissance(elev.getElevelieu_naissance());
       entity.setElevenom(elev.getElevenom());
       entity.setEleveprenom(elev.getEleveprenom());
        return  entity;
   }

    public void  deleteeleve(long zonId){
        eleve entity = eleve.findById(zonId);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

   public  List<eleve> search(String Libelle){
       return   em.createQuery("select o from objet o where  o.objetlibelle like CONCAT('%',:Libelle ,'%') ")
               .setParameter("Libelle",Libelle).getResultList();
   }

    public  long count(){
        return  cycle.count();
    }


}
