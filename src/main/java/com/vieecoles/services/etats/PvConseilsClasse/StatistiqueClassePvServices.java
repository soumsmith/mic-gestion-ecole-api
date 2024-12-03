package com.vieecoles.services.etats.PvConseilsClasse;

import com.vieecoles.dto.NiveauDto2;
import com.vieecoles.dto.ProcesVerbalStatistiqueClasseDto;
import com.vieecoles.dto.ProcesVerbalStatistiqueDisciplineDto;
import com.vieecoles.steph.entities.Matiere;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class StatistiqueClassePvServices {
    @Inject
    EntityManager em;

    public ProcesVerbalStatistiqueClasseDto getStatistiqueClasse(Long idEcole , String libelleAnnee , String libelleTrimestre , String classe){


        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10=0d,pourMoyInf999=0d,pourMoyInf85=0d, pourMoySup10F =0d ,pourMoySup10G =0d,pourMoyInf999F =0d,pourMoyInf999G =0d,pourMoyInf85G =0d,pourMoyInf85F =0d,
                moyClasseF =0d,moyClasseG =0d , moyClasse , moyClasseF_ET = null,moyClasse_ET = null,moyClasseG_ET = null ;
           Integer effectifClasse ,orderNiveau = null;
        String cycle ,professeur;

        //Nouveau
 Integer effectif; Integer nbreClasse; Long nbreMoyenSup10; Long nbreMoyenInf10; Long nbreMoyenInf8_5;
        Integer nbreThFelicitation; Integer nbreThEncouragement; Integer nbreThonneur; Integer nbreAvertissementTravail;
        Integer nbreBlameTravail; Integer nbreAvertissementConduite; Integer nbreBlameConduite;
        Double pourMoyenInf8_5;  Double moyenMini;  Double moyenMaxi; Double pourMoyenSup10;
        Double pourMoyenInf10;


            ProcesVerbalStatistiqueClasseDto resultatsListEleves= new ProcesVerbalStatistiqueClasseDto();


            resultatsListEleves.setClasse(classe);
            classF =getclassF(idEcole,classe,libelleAnnee , libelleTrimestre);
            classG= getclassG(idEcole,classe ,libelleAnnee , libelleTrimestre);
            effeF=geteffeF(idEcole,classe ,libelleAnnee , libelleTrimestre);
            effeG=geteffeG(idEcole,classe ,libelleAnnee , libelleTrimestre);
            resultatsListEleves.setEffectif((int) (effeF+effeG));
            resultatsListEleves.setClasse(classe);
            resultatsListEleves.setNbreClasse((int) (classG+classF));

            nbreMoySup10F = getnbreMoySup10F(idEcole,classe ,libelleAnnee , libelleTrimestre);
            nbreMoySup10G= getnbreMoySup10G(idEcole,classe,libelleAnnee , libelleTrimestre);
            nbreMoyInf999F= getnbreMoyInf999F(idEcole,classe ,libelleAnnee , libelleTrimestre);
            nbreMoyInf999G= getnbreMoyInf999G(idEcole,classe ,libelleAnnee , libelleTrimestre);
            nbreMoyInf85F= getnbreMoyInf85F(idEcole,classe,libelleAnnee , libelleTrimestre);
            nbreMoyInf85G= getnbreMoyInf85G(idEcole,classe,libelleAnnee , libelleTrimestre);



            if(classG !=0)
                pourMoyInf85G= (double) ((nbreMoyInf85G*100d)/classG);

            if(classF !=0)
                pourMoyInf85F= (double) ((nbreMoyInf85F*100d)/classF);
            System.out.println("pourMoyInf85F "+pourMoyInf85G);

            if(classG !=0||classF !=0)
                pourMoyInf85 = (double)((nbreMoyInf85F+nbreMoyInf85G)*100d/(classG+classF)) ;

            if(classG !=0)
                pourMoyInf999G=(double) ((nbreMoyInf999G*100d)/classG);

            if(classF !=0)
                pourMoyInf999F=(double) ((nbreMoyInf999F*100d)/classF);

            if(classG !=0||classF !=0)
                pourMoyInf999 = (double)((nbreMoyInf999G + nbreMoyInf999F)*100d/(classG+classF)) ;

            if(classF !=0)
                pourMoySup10F = (double) ((nbreMoySup10F*100d)/classF);

            if(classG !=0)
                pourMoySup10G = (double) ((nbreMoySup10G*100d)/classG);

            if(classG !=0||classF !=0)
                pourMoySup10 = (double)((nbreMoySup10G+nbreMoySup10F)*100d/(classG+classF)) ;


            resultatsListEleves.setClasse(classe);

            resultatsListEleves.setNbreMoyenSup10(nbreMoySup10G+nbreMoySup10F);
            resultatsListEleves.setPourMoyenSup10(pourMoySup10);
            resultatsListEleves.setNbreMoyenInf10(nbreMoyInf999G + nbreMoyInf999F);
            resultatsListEleves.setPourMoyenInf10(pourMoyInf999);
            resultatsListEleves.setNbreMoyenInf8_5(nbreMoyInf85F+nbreMoyInf85G);
            resultatsListEleves.setPourMoyenInf8_5(pourMoyInf85);
            moyClasse= getmoyClasse(idEcole,classe,libelleAnnee , libelleTrimestre);
           moyenMini= getmoyMinClasse(idEcole,classe,libelleAnnee , libelleTrimestre);
           moyenMaxi= getmoyMaxClasse(idEcole,classe,libelleAnnee , libelleTrimestre);
           resultatsListEleves.setMoyenClasse(moyClasse);
           resultatsListEleves.setMoyenMini(moyenMini);
           resultatsListEleves.setMoyenMaxi(moyenMaxi);
           nbreThonneur= Math.toIntExact(getnbreThHonneur(idEcole, classe, libelleAnnee, libelleTrimestre));
           nbreThEncouragement= Math.toIntExact(
               getnbreThHonneurEncouragement(idEcole, classe, libelleAnnee, libelleTrimestre));
           nbreThFelicitation= Math.toIntExact(
               getnbreThFelicitation(idEcole, classe, libelleAnnee, libelleTrimestre));
           nbreAvertissementTravail= Math.toIntExact(
               getnbreAvertissementPourTravail(idEcole, classe, libelleAnnee, libelleTrimestre));
           nbreBlameTravail= Math.toIntExact(
               getnbreBlamePourTravail(idEcole, classe, libelleAnnee, libelleTrimestre));
            resultatsListEleves.setNbreThonneur(nbreThonneur);
            resultatsListEleves.setNbreThEncouragement(nbreThEncouragement);
            resultatsListEleves.setNbreThFelicitation(nbreThFelicitation);
            resultatsListEleves.setNbreAvertissementTravail(nbreAvertissementTravail);
            resultatsListEleves.setNbreBlameTravail(nbreBlameTravail);
            nbreAvertissementConduite= Math.toIntExact(
                getnbreAvertiseConduite(idEcole, libelleAnnee, libelleTrimestre, classe));
            nbreBlameConduite= Math.toIntExact(getnbreBlameConduite(idEcole, classe, libelleTrimestre, classe));
           // resultatsListEleves.setNbreAvertissementConduite(nbreAvertissementConduite);
        //    resultatsListEleves.setNbreBlameConduite(nbreBlameConduite);

        return  resultatsListEleves ;
    }
    public  Integer getOrderNiveau(String niveau ,String libelleAnnee , String libelleTrimestre){
        Integer ordNiveau;
        try {
            ordNiveau = (Integer) em.createQuery("select distinct o.ordreNiveau from Bulletin o where  o.niveau=:niveau and o.libellePeriode=:periode and o.anneeLibelle=:annee ")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
        } catch (NoResultException e){
            return 0 ;
        }
        return  ordNiveau ;
    }


  public  Integer getEffectifParClasse(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
       Long effectifClasse;
      Integer effecArray ;
      Integer sum = 0;
      try {
          effecArray = (Integer) em.createQuery("select o.effectif from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("matiere",matiere)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .setParameter("classe", classe)
                  .getSingleResult();


      } catch (NoResultException e){
          return 0 ;
      }


  return  effecArray ;
  }
  public  Long geteffeF(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
      Long effeF ;
      try {
          return  effeF = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and   o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe and d.matiereCode=: matiere")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("matiere",matiere)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .setParameter("classe", classe)
                  .getSingleResult();
      } catch (NoResultException e){
          return 0L ;
      }


  }


  public  Long geteffeG(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){

      Long  effeG ;
      try {
          effeG= (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                  .setParameter("sexe","MASCULIN")
                  .setParameter("idEcole",idEcole)
                  .setParameter("matiere",matiere)
                  .setParameter("classe",classe)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .getSingleResult();
          return  effeG ;
      } catch (NoResultException e){
          return 0L ;
      }


  }

    public  Long getclassF(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        Long classF;
        try {
            classF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("isClass","O")
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();

            return  classF ;
        } catch (NoResultException e){
            return 0L ;
        }


    }
    public Long getclassG(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed =:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("isClass","O")
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long geteffeF(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        Long effeF ;
        try {
            return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
        } catch (NoResultException e){
            return 0L ;
        }


    }

    public  Long geteffeG(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){

        Long  effeG ;
        try {
            effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe ")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  effeG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }



    public  Long getNonClasseF(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre, String classe){
        try {
            Long nonclassF = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","N")
                    .setParameter("matiere",matiere)
                    .setParameter("classe",classe)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();

            return  nonclassF ;
        } catch (NoResultException e){
            return 0L ;
        }


    }

    public  Long getNonClasseG(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre, String classe){
        try {
            Long    nonclassG = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and   o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere ")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","N")
                    .setParameter("matiere",matiere)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("classe", classe)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nonclassG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getnbreMoySup10F(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Long   nbreMoySup10F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and  o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn>=:moy and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("isClass","O")
                .setParameter("moy",10.0)
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  nbreMoySup10F;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoySup10G(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn>=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("isClass","O")
                .setParameter("moy",10.0)
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf999F(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("moy",8.5)
                .setParameter("moy2",9.99)
                .setParameter("isClass","O")
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  nbreMoyInf999F    ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf999G(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nbreMoyInf999G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral >=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("moy",8.5)
                .setParameter("moy2",9.99)
                .setParameter("classe",classe)
                .setParameter("isClass","O")
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  nbreMoyInf999G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf85G(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Long   nbreMoyInf85G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("moy",8.5)
                .setParameter("classe",classe)
                .setParameter("isClass","O")
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  nbreMoyInf85G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf85F(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Long  nbreMoyInf85F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral <:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("moy",8.5)
                .setParameter("classe",classe)
                .setParameter("isClass","O")
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  nbreMoyInf85F ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

  public Long getnbreAvertiseConduite(Long idEcole ,String libelleAnnee , String libelleTrimestre ,String classe){
    try {
      Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  d.isRanked=:isClass  and o.ecoleId=:idEcole  and d.moyenne>=:moy and d.moyenne <:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe and d.matiereCode=:matiere ")

          .setParameter("idEcole",idEcole)
          .setParameter("moy",10.0)
          .setParameter("moy2",12.0)
          .setParameter("isClass","O")
          .setParameter("matiere","12")
          .setParameter("classe",classe)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  nbreMoyInf999F    ;
    } catch (NoResultException e){
      return 0L ;
    }

  }

  public Long getnbreBlameConduite(Long idEcole ,String libelleAnnee , String libelleTrimestre ,String classe){
    try {
      Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  d.isRanked=:isClass  and o.ecoleId=:idEcole  and  d.moyenne <:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe and d.matiereCode=:matiere ")

          .setParameter("idEcole",idEcole)
          .setParameter("moy",10.0)
          .setParameter("isClass","O")
          .setParameter("matiere","12")
          .setParameter("classe",classe)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  nbreMoyInf999F    ;
    } catch (NoResultException e){
      return 0L ;
    }

  }

    public  Double getmoyClasseF(Long idEcole ,String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(d.moyenne) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and   o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("matiere",matiere)
                    .setParameter("classe",classe)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double  getmoyClasseG(Long idEcole ,String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(d.moyenne) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and   o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("matiere",matiere)
                    .setParameter("classe",classe)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }


    public  Double  getmoyClasseGET(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                     .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseFET(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                     .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseET(Long idEcole ,String niveau  ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double getmoyClasse(Long idEcole ,String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("idEcole",idEcole)
                .setParameter("isClass","O")
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double getmoyMinClasse(Long idEcole ,String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyMin) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("idEcole",idEcole)
                .setParameter("isClass","O")
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double getmoyMaxClasse(Long idEcole ,String classe ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyMax) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("idEcole",idEcole)
                .setParameter("isClass","O")
                .setParameter("classe",classe)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

  public Long getnbreThHonneur(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
    try {
      Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass  and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
           .setParameter("idEcole",idEcole)
          .setParameter("moy",12.0)
          .setParameter("moy2",12.99)
          .setParameter("isClass","O")
          .setParameter("classe",classe)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  nbreMoyInf999F    ;
    } catch (NoResultException e){
      return 0L ;
    }

  }

  public Long getnbreThHonneurEncouragement(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
    try {
      Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass  and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
          .setParameter("idEcole",idEcole)
          .setParameter("moy",13.0)
          .setParameter("moy2",13.99)
          .setParameter("isClass","O")
          .setParameter("classe",classe)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  nbreMoyInf999F    ;
    } catch (NoResultException e){
      return 0L ;
    }

  }

  public Long getnbreAvertissementPourTravail(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
    try {
      Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass  and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
          .setParameter("idEcole",idEcole)
          .setParameter("moy",8.5)
          .setParameter("moy2",9.99)
          .setParameter("isClass","O")
          .setParameter("classe",classe)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  nbreMoyInf999F    ;
    } catch (NoResultException e){
      return 0L ;
    }

  }

  public Long getnbreBlamePourTravail(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
    try {
      Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass  and o.ecoleId=:idEcole   and o.moyGeneral <:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
          .setParameter("idEcole",idEcole)
          .setParameter("moy",8.5)
          .setParameter("isClass","O")
          .setParameter("classe",classe)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  nbreMoyInf999F    ;
    } catch (NoResultException e){
      return 0L ;
    }

  }



  public Long getnbreThFelicitation(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
    try {
      Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass  and o.ecoleId=:idEcole  and o.moyGeneral>=:moy  and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe")
          .setParameter("idEcole",idEcole)
          .setParameter("moy",14.0)
          .setParameter("isClass","O")
          .setParameter("classe",classe)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  nbreMoyInf999F    ;
    } catch (NoResultException e){
      return 0L ;
    }

  }

    public String getCodeLIbelleById(Long idMatier){
        String libelle= null;

        if(idMatier==1L) {
            libelle="FR";
        } else if (idMatier==2L) {
            libelle="CF";
        }else if (idMatier==3L) {
            libelle="Ex O";
        } else if (idMatier==4L) {
            libelle="OG";
        } else if (idMatier==5L) {
            libelle="ANG";
        } else if (idMatier==6L) {
            libelle="HG";
        } else if (idMatier==7L) {
            libelle="Mathes";
        } else if (idMatier==8L) {
            libelle="PHYS";
        }else if (idMatier==9L) {
            libelle="SVT";
        } else if (idMatier==10L) {
            libelle="EPS";
        }
        else if (idMatier==11L) {
            libelle="EDHC";
        }

        else if (idMatier==12L) {
            libelle="COND";
        }

        else if (idMatier==13L) {
            libelle="INFO";
        }
        else if (idMatier==14L) {
            libelle="ENTREP";
        }
        else if (idMatier==19L) {
            libelle="ART-PLA";
        }
        else if (idMatier==21L) {
            libelle="ESP";
        }
        else if (idMatier==25L) {
            libelle="ALL";
        }
        else if (idMatier==26L) {
            libelle="PHILO";
        }
        else if (idMatier==27L) {
            libelle="TICE";
        }
        else if (idMatier==36L) {
            libelle="ART-VIS";
        }
        else if (idMatier==73L) {
            libelle="ARAB";
        }
        else if (idMatier==29L) {
            libelle="MEMO";
        }
        else if (idMatier==35L) {
            libelle="SIRAH";
        }
        else if (idMatier==30L) {
            libelle="FIQ";
        }
        else if (idMatier==38L) {
            libelle="AKLQ";
        }
        else if (idMatier==37L) {
            libelle="AQD";
        }
        else {
            Matiere matiere = new Matiere();
            matiere = Matiere.findById(idMatier);
            libelle = matiere.getLibelle() ;
        }


        return  libelle ;
    }
}
