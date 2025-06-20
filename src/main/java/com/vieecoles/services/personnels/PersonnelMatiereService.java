package com.vieecoles.services.personnels;

import com.vieecoles.entities.operations.personnel_matiere;
import com.vieecoles.projection.personnel_matiereSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PersonnelMatiereService implements PanacheRepositoryBase<personnel_matiere, Long> {
    @Inject
    EntityManager em;

@Transactional
   public Long   CreerPersonnelMatiere(personnel_matiere personnel_matiere) {
    try {
        personnel_matiere.persist();

    } catch (Exception e) {
       return  null ;
    }
    return personnel_matiere.getPersonnel_matiereid() ;
          }

          public  List<personnel_matiereSelect> getMatiereByProfesseur(Long personnelId){
        TypedQuery<personnel_matiereSelect> q =   em.createQuery("select new com.vieecoles.projection.personnel_matiereSelect(o.personnel_matiereid,m.matierelibelle, p.personnelnom ,p.personnelprenom )  from personnel_matiere o  join  o.personnel p join   o.matiere m  where  o.personnel.personnelid=:personnelId", personnel_matiereSelect.class) ;
        List<personnel_matiereSelect> personnel_matiereSelect = q.setParameter("personnelId",personnelId).getResultList();
             return personnel_matiereSelect;
          }


    public List<personnel_matiereSelect> getAllListMatiereClasseProfesseur(){
        TypedQuery<personnel_matiereSelect> q =   em.createQuery("select new com.vieecoles.projection.personnel_matiereSelect(o.personnel_matiereid,m.matierelibelle, p.personnelnom ,p.personnelprenom )  from personnel_matiere o  join  o.personnel p join   o.matiere m ", personnel_matiereSelect.class) ;
        List<personnel_matiereSelect> personnel_matiereSelect = q.getResultList();
        return personnel_matiereSelect;
    }


    @Transactional
    public void    deleteLineMatiereByProfesseur( Long idMatiereClasse) {
        personnel_matiere  myPersoMatiere = new personnel_matiere() ;
        myPersoMatiere = personnel_matiere.findById(idMatiereClasse) ;

        try{
            myPersoMatiere.delete();
        }catch (Exception e) {

        }

    }



}
