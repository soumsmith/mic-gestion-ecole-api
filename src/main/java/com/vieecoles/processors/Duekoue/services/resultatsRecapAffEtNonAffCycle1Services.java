package com.vieecoles.processors.Duekoue.services;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.NiveauOrderDto;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.dto.ResultatParNiveauDto;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class resultatsRecapAffEtNonAffCycle1Services {
    @Inject
    EntityManager em;

    public ResultatParNiveauDto RecapCalculResultatsEleveAffecte(Long idEcole ,String libelleAnnee , String libelleTrimestre){

        List<NiveauOrderDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauOrderDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauOrderDto(b.niveau,b.ordreNiveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ordreNiveau<5 " +
                "group by b.niveau ,b.ordreNiveau order by b.ordreNiveau", NiveauOrderDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                              . getResultList() ;

      int LongTableau =classeNiveauDtoList.size() ;
      System.out.println("Tableau premier cycle "+LongTableau);

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10=0d,pourMoyInf999=0d,pourMoyInf85=0d, pourMoySup10F =0d ,pourMoySup10G =0d,pourMoyInf999F =0d,pourMoyInf999G =0d,pourMoyInf85G =0d,pourMoyInf85F =0d,
                moyClasseF =0d,moyClasseG =0d , moyClasse , moyClasseF_ET,moyClasse_ET,moyClasseG_ET ;
       Integer effectifClasse ,orderNiveau;
        String cycle ;
        List<RecapResultatsElevesAffeEtNonAffDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
        ResultatParNiveauDto m= new ResultatParNiveauDto();
        try {

        for (int i=0; i< LongTableau;i++) {

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
            if(classeNiveauDtoList.get(i).getOrderNiveau()==1){
                m.setEffeClasse6(effeF+effeG);
                m.setEffeTotal6(classF+classG);
                m.setNbreMoySup10Niv6(nbreMoySup10G+nbreMoySup10F);
                m.setPourMoySup10Niv6(pourMoySup10);
                m.setEffeNonClasse6(nonclassF+nonclassG);
                m.setNbreMoyInf10Niv6(nbreMoyInf999G + nbreMoyInf999F);
                m.setPourMoyInf10Niv6(pourMoyInf999);
                m.setNbreMoyInf8_5Niv6(nbreMoyInf85F+nbreMoyInf85G);
                m.setPourMoyInf8_5Niv6(pourMoyInf85);

            } else if(classeNiveauDtoList.get(i).getOrderNiveau()==2){
                m.setEffeClasse5(effeF+effeG);
                m.setEffeTotal5(classF+classG);
                m.setNbreMoySup10Niv5(nbreMoySup10G+nbreMoySup10F);
                m.setPourMoySup10Niv5(pourMoySup10);
                m.setEffeNonClasse5(nonclassF+nonclassG);
                m.setNbreMoyInf10Niv5(nbreMoyInf999G + nbreMoyInf999F);
                m.setPourMoyInf10Niv5(pourMoyInf999);
                m.setNbreMoyInf8_5Niv5(nbreMoyInf85F+nbreMoyInf85G);
                m.setPourMoyInf8_5Niv5(pourMoyInf85);
            } else if(classeNiveauDtoList.get(i).getOrderNiveau()==3){
                m.setEffeClasse4(effeF+effeG);
                m.setEffeTotal4(classF+classG);
                m.setNbreMoySup10Niv4(nbreMoySup10G+nbreMoySup10F);
                m.setPourMoySup10Niv4(pourMoySup10);
                m.setEffeNonClasse4(nonclassF+nonclassG);
                m.setNbreMoyInf10Niv4(nbreMoyInf999G + nbreMoyInf999F);
                m.setPourMoyInf10Niv4(pourMoyInf999);
                m.setNbreMoyInf8_5Niv4(nbreMoyInf85F+nbreMoyInf85G);
                m.setPourMoyInf8_5Niv4(pourMoyInf85);
            } else if(classeNiveauDtoList.get(i).getOrderNiveau()==4){
                m.setEffeClasse3(effeF+effeG);
                m.setEffeTotal3(classF+classG);
                m.setNbreMoySup10Niv3(nbreMoySup10G+nbreMoySup10F);
                m.setPourMoySup10Niv3(pourMoySup10);
                m.setEffeNonClasse3(nonclassF+nonclassG);
                m.setNbreMoyInf10Niv3(nbreMoyInf999G + nbreMoyInf999F);
                m.setPourMoyInf10Niv3(pourMoyInf999);
                m.setNbreMoyInf8_5Niv3(nbreMoyInf85F+nbreMoyInf85G);
                m.setPourMoyInf8_5Niv3(pourMoyInf85);
            }


        }
        } catch (RuntimeException re) {
            re.printStackTrace();
        }

        return  m ;
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
            Long   nbreMoySup10F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and  o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having o.niveau=:niveau")
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
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
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
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau having  o.niveau=:niveau")
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
            Long nbreMoyInf999G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral>=:moy and o.moyGeneral <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
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
            Long   nbreMoyInf85G = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("niveau",niveau)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf85G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf85F(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Long  nbreMoyInf85F = (Long) em.createQuery("select count(o.id) from Bulletin o where o.isClassed=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and o.moyGeneral<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having o.niveau=:niveau")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("niveau",niveau)
                    .setParameter("isClass","O")
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf85F ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Double getmoyClasseF(Long idEcole ,String niveau ,String libelleAnnee , String libelleTrimestre){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
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
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
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
            Double   moyClasseF = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.ecoleId=:idEcole  and o.isClassed=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee group by  o.niveau having  o.niveau=:niveau")
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
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee ")
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
            Double  moyClasseG = (Double) em.createQuery("select AVG(o.moyGeneral) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.isClassed=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee ")
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


}
