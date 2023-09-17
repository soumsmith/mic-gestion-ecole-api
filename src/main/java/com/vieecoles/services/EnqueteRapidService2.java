package com.vieecoles.services;

import com.vieecoles.dto.Rapide_rapide2Dto;
import com.vieecoles.entities.Annee_Scolaire;
import com.vieecoles.entities.domaine;
import com.vieecoles.entities.operations.Rapide_rapide2;
import com.vieecoles.entities.operations.ecole;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class EnqueteRapidService2 implements PanacheRepositoryBase<Rapide_rapide2, Long> {
    @Inject
    EntityManager em;
   public List<Rapide_rapide2> getListdomaine(){
       return  domaine.listAll();
   }

   public  String insertorModifEnquete(Rapide_rapide2Dto r ) {
       Rapide_rapide2 ra = new Rapide_rapide2() ;
       ra = checkExistEnquete(r.getIdEcole(),r.getIdAnneeScolaire()) ;
       String mess = null ;
       if (ra ==null){
           Rapide_rapide2 ra2 = new Rapide_rapide2() ;
           Annee_Scolaire an = new Annee_Scolaire() ;
           ecole  e = new ecole() ;
           an = Annee_Scolaire.findById(r.getIdAnneeScolaire()) ;
           e = ecole.findById(r.getIdEcole()) ;

           ra2.setAnnee_scolaire(an);
           ra2.setEcole(e);
           ra2.setConseilInter(r.getConseilInter());
           ra2.setConseilProfesseur(r.getConseilProfesseur());
           ra2.setConseilProfesPrincip(r.getConseilProfesPrincip());
           ra2.setConseilEnseigne(r.getConseilEnseigne());
           ra2.setProfessClassExame(r.getProfessClassExame());
           ra2.setUnitePedagogi(r.getUnitePedagogi());
           ra2.setParentsEleve(r.getParentsEleve());
           ra2.setPerAdmichefClasse(r.getPerAdmichefClasse());
           ra2.setChefClasse(r.getChefClasse());
           ra2.persist();
           return  mess= "Enquête bien enregistrée!" ;

       } else {
           Annee_Scolaire an = new Annee_Scolaire() ;
           ecole  e = new ecole() ;
           an = Annee_Scolaire.findById(r.getIdAnneeScolaire()) ;
           e = ecole.findById(r.getIdEcole()) ;
           ra.setAnnee_scolaire(an);
           ra.setEcole(e);
           ra.setConseilInter(r.getConseilInter());
           ra.setConseilProfesseur(r.getConseilProfesseur());
           ra.setConseilProfesPrincip(r.getConseilProfesPrincip());
           ra.setConseilEnseigne(r.getConseilEnseigne());
           ra.setProfessClassExame(r.getProfessClassExame());
           ra.setUnitePedagogi(r.getUnitePedagogi());
           ra.setParentsEleve(r.getParentsEleve());
           ra.setPerAdmichefClasse(r.getPerAdmichefClasse());
           ra.setChefClasse(r.getChefClasse());
           return  mess= "Enquête bien modifiée!" ;

       }

   }



    public Rapide_rapide2 checkExistEnquete(Long idEcole ,Long AnneeId){
        try {
            return (Rapide_rapide2) em.createQuery("select o from Rapide_rapide2 o where o.annee_scolaire.annee_scolaireid =:AnneeId " +
                            " and o.ecole.ecoleid=:idEcole  ")
                    .setParameter("AnneeId",AnneeId)
                    .setParameter("idEcole",idEcole)
                    .getSingleResult();
        } catch (Exception e) {
            return  null;
        }


    }

}
