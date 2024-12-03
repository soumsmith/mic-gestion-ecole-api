package com.vieecoles.services.etats.PvConseilsClasse;

import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.ProcesVerbalMatiereSpecifiqueDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@ApplicationScoped
public class MatiereSpecifiquePvServices {
    @Inject
    EntityManager em;
   @Transactional
    public ProcesVerbalMatiereSpecifiqueDto  getMatiereSpecifique(Long idEcole, String libellePeriode , String libelleAnnee ,String classe){
        int LongTableau;

     ProcesVerbalMatiereSpecifiqueDto matiere = new ProcesVerbalMatiereSpecifiqueDto();

        Double moyenAng = 0.0,moyenMath = 0.0,moyenPhysiq = 0.0,moyen_fr = 0.0;
     Double  moyenCompoFr = null,moyenOrthoGram = null,moyenExpreOral = null;

     Integer niveauOrdre= getNiveauId(classe,libelleAnnee ,libellePeriode,idEcole);
     System.out.println("niveauOrdrew>>> "+niveauOrdre);
     if(niveauOrdre<=4) {
       Double moyFr = calculMoycoefFran(classe,libelleAnnee ,libellePeriode,idEcole ) ;
       Double coef = calculcoefFran(classe,libelleAnnee ,libellePeriode,idEcole) ;
       moyen_fr=moyFr/coef;
     } else {
       moyen_fr=getMoyMatiere(1L,libellePeriode ,libelleAnnee ,idEcole,classe) ;

     }

       moyenAng = getMoyMatiere(5L,libellePeriode ,libelleAnnee ,idEcole,classe) ;
       System.out.println("moyen_ang = " + moyenAng);
       moyenMath = getMoyMatiere(7L,libellePeriode ,libelleAnnee ,idEcole,classe) ;
       System.out.println("moyen_math = " + moyenMath);
       moyenPhysiq = getMoyMatiere(8L,libellePeriode ,libelleAnnee ,idEcole ,classe) ;
       System.out.println("moyen_physiq = " + moyenPhysiq);
       matiere.setClasse(classe);
       matiere.setMoyenneAnglais(moyenAng);
       matiere.setMoyenneFrancais(moyen_fr);
       matiere.setMoyenneMath(moyenMath);
       matiere.setMoyennePhysiqueChimie(moyenPhysiq);


        return  matiere ;
    }

  public  Double calculMoycoefFran(String classe, String annee,String periode,Long idEcole){
    try {
      Double  moyTfr = (Double) em.createQuery("select  SUM(d.coef*d.moyenne) from DetailBulletin d join d.bulletin b where b.libelleClasse =:classe and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
          .setParameter("classe",classe)
          .setParameter("annee",annee)
          .setParameter("periode",periode)
          .setParameter("idEcole",idEcole)
          .getSingleResult();
      return  moyTfr ;
    } catch (NoResultException e){
      return 0D ;
    }
  }

  public  Double calculcoefFran(String classe, String annee,String periode,Long idEcole){
    try {
      Double  moyTfr = (Double) em.createQuery("select SUM(d.coef) from DetailBulletin d join d.bulletin b where b.libelleClasse=:classe and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  and d.matiereLibelle in ('COMPOSITION FRANCAISE','ORTHOGRAPHE ET GRAMMAIRE','EXPRESSION ORALE') ")
          .setParameter("classe",classe)
          .setParameter("annee",annee)
          .setParameter("periode",periode)
          .setParameter("idEcole",idEcole)
          .getSingleResult();
      return  moyTfr ;
    } catch (NoResultException e){
      return 0D ;
    }
  }
    public  Double getMoyMatiere(Long libelleMatiere,String periode ,String libelleAnnee ,Long idEcole ,String classe){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(d.moyenne)  from DetailBulletin  d join d.bulletin b  where d.matiereId=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                            " and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.libelleClasse=:classe ")
                    .setParameter("libelleMatiere",libelleMatiere)
                    .setParameter("periode",periode)
                    .setParameter("libelleAnnee", libelleAnnee)
                    .setParameter("idEcole", idEcole)
                    .setParameter("classe", classe)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
  public  Integer getNiveauId(String classe, String annee,String periode,Long idEcole){
    try {
      Integer  num = (Integer) em.createQuery("select b.ordreNiveau from Bulletin b where b.libelleClasse=:classe and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.anneeLibelle=:annee  ")
          .setParameter("classe",classe)
          .setParameter("annee",annee)
          .setParameter("periode",periode)
          .setParameter("idEcole",idEcole)
          .getSingleResult();
      return  num ;
    } catch (NoResultException e){
      return 0;
    }
  }


}
