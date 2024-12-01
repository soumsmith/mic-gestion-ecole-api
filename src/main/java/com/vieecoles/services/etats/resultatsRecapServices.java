package com.vieecoles.services.etats;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.RecapDesResultatsElevesAffecteDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class resultatsRecapServices {
    @Inject
    EntityManager em;

    public List<RecapDesResultatsElevesAffecteDto> RecapCalculResultatsEleveAffecte(Long idEcole ,String libelleAnnee , String libelleTrimestre){

        List<NiveauOrderDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauOrderDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauOrderDto(b.niveau,b.ordreNiveau) from Bulletin b  where b.ecoleId =:idEcole and b.affecte=:affecte" +
                " and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.ordreNiveau, b.niveau order by b.ordreNiveau", NiveauOrderDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                          .setParameter("affecte", "AFFECTE")
                          .setParameter("annee", libelleAnnee)
                           .setParameter("periode", libelleTrimestre)
                           . getResultList() ;


      int LongTableau =classeNiveauDtoList.size() ;

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10F =0d ,pourMoySup10G =0d,pourMoyInf999F =0d,pourMoyInf999G =0d,pourMoyInf85G =0d,pourMoyInf85F =0d,
                moyClasseF =0d,moyClasseG =0d , moyClasse , moyClasseF_ET,moyClasse_ET,moyClasseG_ET ;
       Integer effectifClasse,orderNiveau ;
        List<RecapDesResultatsElevesAffecteDto> resultatsListElevesDto = new ArrayList<>(LongTableau);

        for (int i=0; i< LongTableau;i++) {
            RecapDesResultatsElevesAffecteDto resultatsListEleves= new RecapDesResultatsElevesAffecteDto();
            orderNiveau =getOrderNiveau(classeNiveauDtoList.get(i).getNiveau(),libelleAnnee , libelleTrimestre);

            effectifClasse= getEffectifParClasse(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            effeG = geteffeG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            effeF = geteffeF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            classF =getclassF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            classG= getclassG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nonclassF= getNonClasseF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nonclassG= getNonClasseG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nbreMoySup10F = getnbreMoySup10F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nbreMoySup10G= getnbreMoySup10G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nbreMoyInf999F= getnbreMoyInf999F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nbreMoyInf999G= getnbreMoyInf999G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nbreMoyInf85F= getnbreMoyInf85F(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            nbreMoyInf85G= getnbreMoyInf85G(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            moyClasseF=getmoyClasseF(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            moyClasseG=getmoyClasseG(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            moyClasse = getmoyClasse(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            moyClasse_ET = getmoyClasseET(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            moyClasseG_ET = getmoyClasseGET(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            moyClasseF_ET = getmoyClasseFET(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            //calcul pourcentage
            if(classG !=0)
            pourMoyInf85G= (double) ((nbreMoyInf85G*100d)/classG);

            if(classF !=0)
            pourMoyInf85F= (double) ((nbreMoyInf85F*100d)/classF);

            if(classG !=0)
            pourMoyInf999G=(double) ((nbreMoyInf999G*100d)/classG);

            if(classF !=0)
            pourMoyInf999F=(double) ((nbreMoyInf999F*100d)/classF);
            if(classF !=0)
            pourMoySup10F = (double) ((nbreMoySup10F*100d)/classF);

            if(classG !=0)
            pourMoySup10G = (double) ((nbreMoySup10G*100d)/classG);

            resultatsListEleves.setNiveau(classeNiveauDtoList.get(i).getNiveau());
            resultatsListEleves.setEffeG(effeG);
            resultatsListEleves.setEffeF(effeF);
            resultatsListEleves.setClassG(classG);
            resultatsListEleves.setClassF(classF);
            resultatsListEleves.setNonclassF(nonclassF);
            resultatsListEleves.setNonclassG(nonclassG);
            resultatsListEleves.setOrdre_niveau(orderNiveau);
            resultatsListEleves.setNbreMoySup10F(nbreMoySup10F);
            resultatsListEleves.setNbreMoySup10G(nbreMoySup10G);

            resultatsListEleves.setPourMoySup10F(pourMoySup10F);
            resultatsListEleves.setPourMoySup10G(pourMoySup10G);

            resultatsListEleves.setNbreMoyInf999F(nbreMoyInf999F);
            resultatsListEleves.setNbreMoyInf999G(nbreMoyInf999G);

            resultatsListEleves.setPourMoyInf999F(pourMoyInf999F);
            resultatsListEleves.setPourMoyInf999G(pourMoyInf999G);

            resultatsListEleves.setNbreMoyInf85G(nbreMoyInf85G);
            resultatsListEleves.setNbreMoyInf85F(nbreMoyInf85F);

            resultatsListEleves.setPourMoyInf85G(pourMoyInf85G);
            resultatsListEleves.setPourMoyInf85F(pourMoyInf85F);

            resultatsListEleves.setMoyClasseG(moyClasseG);
            resultatsListEleves.setMoyClasseF(moyClasseF);
            resultatsListEleves.setMoyClasse(moyClasse);
            resultatsListEleves.setMoyClasse_ET(moyClasse_ET);
            resultatsListEleves.setMoyClasseF_ET(moyClasseF_ET);
            resultatsListEleves.setMoyClasseG_ET(moyClasseG_ET);


            resultatsListElevesDto.add(resultatsListEleves) ;

        }

        return  resultatsListElevesDto ;
    }

  public  Integer getEffectifParClasse(Long idEcole , String niveau ,String annee , String periode){
       Long effectifClasse;
       List<Integer> effecArray =new ArrayList<>();
      Integer sum = 0;
      try {
          effecArray = (List<Integer>) em.createQuery("select o.effectif from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte and  o.anneeLibelle=:annee and o.libellePeriode=: periode group by  o.niveau,o.effectif having  o.niveau=:niveau ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("affecte","AFFECTE")
                   .setParameter("niveau",niveau)
                  .setParameter("annee",annee)
                  .setParameter("periode",periode)
                  .getResultList() ;

          for (Integer value : effecArray) {
              sum += value;
          }
      } catch (NoResultException e){
          return 0 ;
      }


  return  sum ;
  }


    public  Integer getOrderNiveau(String niveau ,String annee , String periode){
        Integer ordNiveau;
        try {
            ordNiveau = (Integer) em.createQuery("select distinct o.ordreNiveau from Bulletin o where  o.niveau=:niveau and  o.anneeLibelle=:annee and o.libellePeriode=: periode ")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
        } catch (NoResultException e){
            return 0 ;
        }
        return  ordNiveau ;
    }
  public  Long geteffeF(Long idEcole , String niveau ,String annee , String periode){
      Long effeF ;
      try {
          return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.anneeLibelle=:annee and o.libellePeriode=: periode group by  o.niveau having  o.niveau=:niveau")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("affecte","AFFECTE")
                  .setParameter("niveau",niveau)
                  .setParameter("annee",annee)
                  .setParameter("periode",periode)
                  .getSingleResult();
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long geteffeG(Long idEcole , String niveau ,String annee , String periode){

      Long  effeG ;
      try {
          effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.anneeLibelle=:annee and o.libellePeriode=: periode group by o.niveau having  o.niveau=:niveau")
                  .setParameter("sexe","MASCULIN")
                  .setParameter("idEcole",idEcole)
                  .setParameter("affecte","AFFECTE")
                  .setParameter("niveau",niveau)
                  .setParameter("annee",annee)
                  .setParameter("periode",periode)
                  .getSingleResult();
          return  effeG ;
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long getclassF(Long idEcole , String niveau ,String annee , String periode){
      Long classF;
      try {
          classF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=: periode group by  o.niveau having o.niveau=:niveau")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("affecte","AFFECTE")
                  .setParameter("isClass","O")
                  .setParameter("niveau",niveau)
                  .setParameter("annee",annee)
                  .setParameter("periode",periode)
                  .getSingleResult();

          return  classF ;
      } catch (NoResultException e){
          return 0L ;
      }


  }
    public Long getclassG(Long idEcole , String niveau ,String annee , String periode){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed =:isClass and o.anneeLibelle=:annee and o.libellePeriode=: periode group by o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getNonClasseF(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long nonclassF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=: periode group by o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","N")
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)

                    .setParameter("niveau",niveau)
                    .getSingleResult();

            return  nonclassF ;
        } catch (NoResultException e){
            return 0L ;
        }


    }

    public  Long getNonClasseG(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long    nonclassG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=: periode group by o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","N")

                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nonclassG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getnbreMoySup10F(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long   nbreMoySup10F = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and  o.isClassed=:isClass and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy and o.anneeLibelle=:annee and o.libellePeriode=: periode  group by  o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("moy",10.0)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nbreMoySup10F;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoySup10G(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.isClassed=:isClass and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy and o.anneeLibelle=:annee and o.libellePeriode=: periode group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("moy",10.0)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf999F(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.isClassed=:isClass and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.anneeLibelle=:annee and o.libellePeriode=: periode group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("isClass","O")
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)

                    .setParameter("niveau",niveau)
                    .getSingleResult();
            return  nbreMoyInf999F    ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf999G(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long nbreMoyInf999G = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe  and o.isClassed=:isClass and  o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.anneeLibelle=:annee and o.libellePeriode=: periode group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nbreMoyInf999G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf85G(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long   nbreMoyInf85G = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.isClassed=:isClass and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral<:moy and o.anneeLibelle=:annee and o.libellePeriode=: periode group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("moy",8.5)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nbreMoyInf85G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf85F(Long idEcole , String niveau ,String annee , String periode){
        try {
            Long  nbreMoyInf85F = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.isClassed=:isClass and o.ecoleId=:idEcole and o.affecte=:affecte and o.moyGeneral<:moy and o.anneeLibelle=:annee and o.libellePeriode=:periode group by  o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("moy",8.5)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  nbreMoyInf85F ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Double getmoyClasseF(Long idEcole ,String niveau ,String annee , String periode){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=:periode group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double  getmoyClasseG(Long idEcole ,String niveau ,String annee , String periode){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=:periode group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","O")

                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double getmoyClasse(Long idEcole ,String niveau ,String annee , String periode){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=:periode group by  o.niveau having  o.niveau=:niveau")

                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseGET(Long idEcole ,String niveau ,String annee , String periode){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=:periode")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","O")
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseFET(Long idEcole ,String niveau ,String annee , String periode){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=:periode")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","O")
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseET(Long idEcole ,String niveau ,String annee , String periode){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.ecoleId=:idEcole and o.affecte=:affecte and o.isClassed=:isClass and o.anneeLibelle=:annee and o.libellePeriode=:periode")

                    .setParameter("idEcole",idEcole)
                    .setParameter("affecte","AFFECTE")
                    .setParameter("isClass","O")
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }


}
