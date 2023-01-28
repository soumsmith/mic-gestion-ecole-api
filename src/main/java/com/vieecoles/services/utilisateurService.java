package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import com.vieecoles.entities.utilisateur;

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
       return Response.created(URI.create("/utilisateur/" + mat.getUtilisateu_email())).build();
   }

   public  utilisateur updateutilisateur(long matId, utilisateur mat){
       utilisateur entity = utilisateur.findById(matId);
       if(entity == null) {
           throw new NotFoundException();
       }
       entity.setUtilisateur_mot_de_passe(mat.getUtilisateur_mot_de_passe());
       entity.setUtilisateu_email(mat.getUtilisateu_email());

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
