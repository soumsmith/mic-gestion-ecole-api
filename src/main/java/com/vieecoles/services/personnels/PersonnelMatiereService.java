package com.vieecoles.services.personnels;

import com.vieecoles.entities.operations.personnel_matiere_classe;
import com.vieecoles.projection.personnel_matiere_classeSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PersonnelMatiereClasseService implements PanacheRepositoryBase<personnel_matiere_classe, Long> {
    @Inject
    EntityManager em;

@Transactional
   public Long   CreerPersonnelMatiereClasse(personnel_matiere_classe personnel_matiere_classe) {
    try {
         personnel_matiere_classe.persist();

    } catch (Exception e) {
       return  null ;
    }
    return personnel_matiere_classe.getPersonnel_matiere_classeid() ;
          }

          public  List<personnel_matiere_classeSelect> getMatiereClasseByProfesseur(Long personnelId){
        TypedQuery<personnel_matiere_classeSelect> q =   em.createQuery("select new com.vieecoles.projection.personnel_matiere_classeSelect(o.Personnel_matiere_classeid,o.Personnel_matiere_classe_date_creation,m.matierelibelle,c.classelibelle, p.personnelnom ,p.personnelprenom )  from personnel_matiere_classe o  join  o.personnel p join   o.matiere m join  o.classe c where  o.personnel.personnelid=:personnelId", personnel_matiere_classeSelect.class) ;
        List<personnel_matiere_classeSelect> personnel_matiere_classeSelect = q.setParameter("personnelId",personnelId).getResultList();
             return personnel_matiere_classeSelect ;
          }


    public List<personnel_matiere_classeSelect> getAllListMatiereClasseProfesseur(){
        TypedQuery<personnel_matiere_classeSelect> q =   em.createQuery("select new com.vieecoles.projection.personnel_matiere_classeSelect( o.Personnel_matiere_classeid,o.Personnel_matiere_classe_date_creation,m.matierelibelle,c.classelibelle, p.personnelnom ,p.personnelprenom )  from personnel_matiere_classe o  join  o.personnel p join   o.matiere m join  o.classe c", personnel_matiere_classeSelect.class) ;
        List<personnel_matiere_classeSelect> mpersonnel_matiere_classeSelect = q.getResultList();
        return mpersonnel_matiere_classeSelect ;
    }


    @Transactional
    public void    deleteLineMatiereClasseByProfesseur( Long idMatiereClasse) {
        personnel_matiere_classe myPersoMatiereClasse = new personnel_matiere_classe() ;
        myPersoMatiereClasse = personnel_matiere_classe.findById(idMatiereClasse) ;

        try{
            myPersoMatiereClasse.delete();
        }catch (Exception e) {

        }

    }



}
