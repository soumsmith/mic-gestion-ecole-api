package com.vieecoles.processors.dren3.services.annuels;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class resultatsRecapAffEtNonAffRestrictCycle2AnnuelServices {
    @Inject
    EntityManager em;

    public List<RecapResultatsElevesAffeEtNonAffDto> RecapCalculResultatsEleveAffecte(Long idEcole ,String libelleAnnee , String libelleTrimestre){

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee  and b.ordreNiveau>=5" +
                "group by b.niveau ,b.ordreNiveau order by b.ordreNiveau", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                              . getResultList() ;

      int LongTableau =classeNiveauDtoList.size() ;

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10=0d,pourMoyInf999=0d,pourMoyInf85=0d, pourMoySup10F =0d ,pourMoySup10G =0d,pourMoyInf999F =0d,pourMoyInf999G =0d,pourMoyInf85G =0d,pourMoyInf85F =0d,
                moyClasseF =0d,moyClasseG =0d , moyClasse , moyClasseF_ET,moyClasse_ET,moyClasseG_ET ;
       Integer effectifClasse ,orderNiveau;
        String cycle ;
        List<RecapResultatsElevesAffeEtNonAffDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
       // System.out.println("resultatsListElevesDto Size "+ resultatsListElevesDto.size());
        for (int i=0; i< LongTableau;i++) {
            RecapResultatsElevesAffeEtNonAffDto resultatsListEleves= new RecapResultatsElevesAffeEtNonAffDto();
            orderNiveau = getOrderNiveau(classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);
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
            if(orderNiveau<=4){
                cycle="1er";
            } else {
                cycle="2nd";
            }

            if(classG !=0)
                pourMoyInf85G= (double) ((nbreMoyInf85G*100d)/classG);

            if(classF !=0)
                pourMoyInf85F= (double) ((nbreMoyInf85F*100d)/classF);


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


            resultatsListEleves.setNiveau(classeNiveauDtoList.get(i).getNiveau());

            resultatsListEleves.setEffeG(effeG);
            resultatsListEleves.setEffeF(effeF);
            resultatsListEleves.setClassG(classG);
            resultatsListEleves.setClassF(classF);
            resultatsListEleves.setNonclassF(nonclassF);
            resultatsListEleves.setNonclassG(nonclassG);
            resultatsListEleves.setCycle(cycle);
            resultatsListEleves.setPeriode (libelleTrimestre);

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
            resultatsListEleves.setOrdre_niveau(orderNiveau);

            resultatsListEleves.setMoyClasseG(moyClasseG);
            resultatsListEleves.setMoyClasseF(moyClasseF);
            resultatsListEleves.setMoyClasse(moyClasse);
            resultatsListEleves.setMoyClasse_ET(moyClasse_ET);
            resultatsListEleves.setMoyClasseF_ET(moyClasseF_ET);
            resultatsListEleves.setMoyClasseG_ET(moyClasseG_ET);
            resultatsListEleves.setPourMoyInf85(pourMoyInf85);
            resultatsListEleves.setPourMoyInf999(pourMoyInf999);
            resultatsListEleves.setPourMoySup10(pourMoySup10);
            resultatsListElevesDto.add(resultatsListEleves) ;



        }

        return  resultatsListElevesDto ;
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
  public  Integer getEffectifParClasse(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
       Long effectifClasse;
      List<Integer> effecArray =new ArrayList<>();
      Integer sum = 0;
      try {
          effecArray = (List<Integer>) em.createQuery("select o.effectif from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau,o.effectif having  o.niveau=:niveau ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .getResultList() ;

          for (Integer value : effecArray) {
              sum += value;
          }
      } catch (NoResultException e){
          return 0 ;
      }


  return  sum ;
  }
  public  Long geteffeF(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
      Long effeF ;
      try {
          return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .getSingleResult();
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long geteffeG(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){

      Long  effeG ;
      try {
          effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee group by o.niveau having  o.niveau=:niveau")
                  .setParameter("sexe","MASCULIN")
                  .setParameter("idEcole",idEcole)
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .getSingleResult();
          return  effeG ;
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long getclassF(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
      Long classF;
      try {
          classF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having o.niveau=:niveau")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("isClass","O")
                  .setParameter("niveau",niveau)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .getSingleResult();

          return  classF ;
      } catch (NoResultException e){
          return 0L ;
      }


  }
    public Long getclassG(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed =:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee group by o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getNonClasseF(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nonclassF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","N")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();

            return  nonclassF ;
        } catch (NoResultException e){
            return 0L ;
        }


    }

    public  Long getNonClasseG(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long    nonclassG = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","N")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nonclassG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public  Long getnbreMoySup10F(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long   nbreMoySup10F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and  o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn>=:moy and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10F;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoySup10G(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn>=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf999F(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn>=:moy and o.moyAn <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf999F    ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf999G(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long nbreMoyInf999G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn>=:moy and o.moyAn <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("niveau",niveau)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf999G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf85G(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long   nbreMoyInf85G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.redoublant=:red group by  o.niveau having  o.niveau=:niveau")
                .setParameter("sexe","MASCULIN")
                .setParameter("idEcole",idEcole)
                .setParameter("moy",8.5)
                .setParameter("niveau",niveau)
                .setParameter("isClass","O")
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("red", "NON")
                .getSingleResult();
            return  nbreMoyInf85G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf85F(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long  nbreMoyInf85F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyAn<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.redoublant=:red group by  o.niveau having o.niveau=:niveau")
                .setParameter("sexe","FEMININ")
                .setParameter("idEcole",idEcole)
                .setParameter("moy",8.5)
                .setParameter("niveau",niveau)
                .setParameter("isClass","O")
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("red", "NON")
                .getSingleResult();
            return  nbreMoyInf85F ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Double getmoyClasseF(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyAn) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double  getmoyClasseG(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyAn) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0D ;
        }

    }
    public  Double getmoyClasse(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyAn) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0D ;
        }

    }

    public  Double  getmoyClasseGET(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyAn) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee ")
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
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyAn) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee ")
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
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyAn) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee")
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


}
