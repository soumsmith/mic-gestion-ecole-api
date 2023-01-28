package com.vieecoles.services.personnels;

import com.vieecoles.entities.operations.emploi_du_temps_professeur;
import com.vieecoles.projection.emploi_du_temps_professeurSelect;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class emploi_du_temps_professeurService implements PanacheRepositoryBase<emploi_du_temps_professeur, Long> {
    @Inject
    EntityManager em;

@Transactional
   public Long   CreerEmploiDutempsProfesseur(emploi_du_temps_professeur emploi_du_temps_professeur) {
    try {
        emploi_du_temps_professeur.persist();

    } catch (Exception e) {
       return  null ;
    }
    return emploi_du_temps_professeur.getEmploi_du_temps_professeurid() ;
          }

          public  List<emploi_du_temps_professeurSelect> getEmploiDutempsByProfesseur(Long personnelId){
        TypedQuery<emploi_du_temps_professeurSelect> q =   em.createQuery("select new com.vieecoles.projection.emploi_du_temps_professeurSelect(o.emploi_du_temps_professeurid,o.emploi_du_temps_professeur_jour,o.emploi_du_temps_professeur_heure_debut,o.emploi_du_temps_professeur_heure_fin ,m.matierelibelle,c.classelibelle, p.personnelnom ,p.personnelprenom )  from emploi_du_temps_professeur o  join  o.personnel p join   o.matiere m join  o.classe c where  o.personnel.personnelid=:personnelId", emploi_du_temps_professeurSelect.class) ;
        List<emploi_du_temps_professeurSelect> emploiDuTempSelect = q.setParameter("personnelId",personnelId).getResultList();
             return emploiDuTempSelect ;
          }


    public List<emploi_du_temps_professeurSelect> getEmploiDuTempsProfesseur(){
        TypedQuery<emploi_du_temps_professeurSelect> q =   em.createQuery("select new com.vieecoles.projection.emploi_du_temps_professeurSelect(o.emploi_du_temps_professeurid,o.emploi_du_temps_professeur_jour,o.emploi_du_temps_professeur_heure_debut,o.emploi_du_temps_professeur_heure_fin ,m.matierelibelle,c.classelibelle, p.personnelnom ,p.personnelprenom )  from emploi_du_temps_professeur o  join  o.personnel p join   o.matiere m join  o.classe c ", emploi_du_temps_professeurSelect.class) ;
        List<emploi_du_temps_professeurSelect> emploiDuTempSelect = q.getResultList();
        return emploiDuTempSelect ;
    }


    @Transactional
    public void    deleteEmploiDuTempsProfesseur( Long idMatiereClasse) {
        emploi_du_temps_professeur myPersoEmploiDu = new emploi_du_temps_professeur() ;
        myPersoEmploiDu = emploi_du_temps_professeur.findById(idMatiereClasse) ;

        try{
            myPersoEmploiDu.delete();
        }catch (Exception e) {

        }

    }



}
