package com.vieecoles.services.personnels;

import com.vieecoles.entities.Annee_Scolaire;
import com.vieecoles.entities.matiere;
import com.vieecoles.entities.tenant;
import com.vieecoles.entities.operations.classe;
import com.vieecoles.entities.operations.personnel;
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
   public Long   CreerPersonnelMatiereClasse(Long personnelId, Long classeId,Long MatiereId,Long anneeId,String tenantId) {
            String   retourMess;

            retourMess=  checkpersonnel_matiere_classe(personnelId,classeId,MatiereId,anneeId,tenantId) ;
                    if (retourMess.equals("EXISTE PAS"))
                        {
                                    personnel myPersonnel = new personnel() ;
                                    myPersonnel= personnel.findById(personnelId);
                                    classe myclasse=  new classe();
                                    myclasse= classe.findById(classeId);
                                    matiere mymatiere= new matiere();
                                    mymatiere= matiere.findById(MatiereId);
                                    Annee_Scolaire myanne = new Annee_Scolaire();
                                    myanne= Annee_Scolaire.findById(anneeId);
                                    tenant mytenant= new tenant();
                                    mytenant =tenant.findById(tenantId);
                                    personnel_matiere_classe mypersonclass= new personnel_matiere_classe();
                                    mypersonclass.setMatiere(mymatiere);
                                    mypersonclass.setPersonnel(myPersonnel);
                                    mypersonclass.setClasse(myclasse);
                                    mypersonclass.setTenant(mytenant);
                                    mypersonclass.setAnnee_scolaire(myanne);

                                    mypersonclass.persist();
                                    return mypersonclass.getPersonnel_matiere_classeid() ;
                    } else  {
                                return null;
                    }

     }



    @Transactional
    public Long   ModifierPersonnelMatiereClasse(Long entityId, Long classeId,Long MatiereId) {

    try {
        String   retourMess;
        personnel_matiere_classe mypersonclass= new personnel_matiere_classe();
        mypersonclass = personnel_matiere_classe.findById(entityId);

        classe myclasse=  new classe();
        myclasse= classe.findById(classeId);
        matiere mymatiere= new matiere();
        mymatiere= matiere.findById(MatiereId);

        mypersonclass.setMatiere(mymatiere);

        mypersonclass.setClasse(myclasse);
        return  mypersonclass.getPersonnel_matiere_classeid() ;
    } catch (Exception e) {
       return  null ;
    }


    }


    public List <personnel_matiere_classe> ListeParEcoleAnnee(String tenantId ,Long anneeScolaire){

        TypedQuery<personnel_matiere_classe> q = (TypedQuery<personnel_matiere_classe>)
                em.createQuery("select o from Personnel_matiere_classe o join o.personnel join o.tenant join o.classe join o.annee_scolaire" +
                        " join o.matiere where o.tenant.tenantid =:idtenant and o.annee_scolaire.annee_scolaireid=:anneeId");
        List<personnel_matiere_classe> personnelSelect = q.setParameter("idtenant",tenantId).setParameter("anneeId",anneeScolaire).
                getResultList();
        return personnelSelect ;
    }


     public String  checkpersonnel_matiere_classe(Long personnelId, Long classeId,Long MatiereId,Long anneeId,String tenantId){
      try {
          TypedQuery<personnel_matiere_classe> q = (TypedQuery<personnel_matiere_classe>)
                  em.createQuery("select o from Personnel_matiere_classe o join o.personnel p join o.tenant t join o.classe c join o.annee_scolaire a" +
                          " join o.matiere m where p.personnelid=: personnelId and t.tenantid=: tenantId and m.matiereid=:MatiereId and c.classeid=:classeId and a.annee_scolaireid=: anneeId  ");
          personnel_matiere_classe personnelSelect = q.setParameter("personnelId",personnelId).setParameter("tenantId",tenantId).setParameter("MatiereId",MatiereId)
                  .setParameter("classeId",classeId).setParameter("anneeId",anneeId).getSingleResult() ;

          return "EXISTE" ;
      } catch (Exception e){
          return  "EXISTE PAS" ;
      }

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
