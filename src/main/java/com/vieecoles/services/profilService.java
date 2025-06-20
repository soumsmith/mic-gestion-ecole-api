package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.domaine;
import com.vieecoles.entities.profil;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class profilService implements PanacheRepositoryBase<profil, Long> {
    @Inject
    EntityManager em;
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

   public  profil getIdProfilAdmin(String Libelle){
    return  profil.find("profil_libelle",Libelle).firstResult() ;
}


    public  long count(){
        return  profil.count();
    }

    @Transactional
    public List<profil>  getProfilForEcole(){
        List<profil>  profilList = new ArrayList<>() ;
        try {
            profilList= (List<profil>) em.createQuery("select o from profil  o  where  o.profil_libelle not  in ('Fondateur','Admin')")
                   // .setParameter("names",names)
                    .getResultList();
        } catch (Exception e) {
            profilList = null;
        }
        return profilList ;
    }


}
