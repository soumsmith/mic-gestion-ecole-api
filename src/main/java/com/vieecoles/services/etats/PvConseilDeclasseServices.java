package com.vieecoles.services.etats;

import com.vieecoles.dto.ConseilClasseDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.NiveauDto2;
import com.vieecoles.dto.RecapResultatsElevesAffeEtNonAffDto;
import com.vieecoles.steph.entities.Matiere;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PvConseilDeclasseServices {
    @Inject
    EntityManager em;

    public List<ConseilClasseDto> RecapCalculResultatsEleveAffecte(Long idEcole , String libelleAnnee , String libelleTrimestre ,String classe){

        List<NiveauDto2> classeNiveauDtoList = new ArrayList<>() ;

        TypedQuery<NiveauDto2> Q = em.createQuery( "SELECT DISTINCT new com.vieecoles.dto.NiveauDto2(d.matiereCode ,d.num_ordre) from Bulletin b, DetailBulletin d " +
                " where b.id= d.bulletin.id and b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.libelleClasse=:classe order by d.num_ordre" , NiveauDto2.class);
        classeNiveauDtoList = Q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("classe", classe)
                .getResultList() ;

  //System.out.println("classeNiveauDtoList "+classeNiveauDtoList.toString());
        System.out.println("Longueur Tableau" +classeNiveauDtoList.size());
      int LongTableau =classeNiveauDtoList.size() ;

        Long  effeG,effeF,classF,classG,nonclassF,nonclassG,nbreMoySup10F,nbreMoySup10G,nbreMoyInf999F,nbreMoyInf999G,nbreMoyInf85G,nbreMoyInf85F;
        Double pourMoySup10=0d,pourMoyInf999=0d,pourMoyInf85=0d, pourMoySup10F =0d ,pourMoySup10G =0d,pourMoyInf999F =0d,pourMoyInf999G =0d,pourMoyInf85G =0d,pourMoyInf85F =0d,
                moyClasseF =0d,moyClasseG =0d , moyClasse , moyClasseF_ET = null,moyClasse_ET = null,moyClasseG_ET = null ;
           Integer effectifClasse ,orderNiveau = null;
        String cycle ,professeur;
        List<ConseilClasseDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
       // System.out.println("resultatsListElevesDto Size "+ resultatsListElevesDto.size());
        for (int i=0; i< LongTableau;i++) {
            ConseilClasseDto resultatsListEleves= new ConseilClasseDto();
            long idMatiere = 0;
            Matiere myMatiere = new Matiere();
            String libelleMatiere ;
            String id = String.valueOf(classeNiveauDtoList.get(i).getNiveau());
            idMatiere = Long.parseLong(id);
            System.out.println("idMatiere "+idMatiere);
            myMatiere = Matiere.findById(idMatiere) ;
            //  libelleMatiere= getLibelleMatiere(idMatiere) ;

            //libelleMatiere= myMatiere.getLibelle() ;
            libelleMatiere = getCodeLIbelleById(idMatiere);
            System.out.println("libelleMatiere "+libelleMatiere);










//orderNiveau = getOrderNiveau(classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);
            effectifClasse= getEffectifParClasse(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            professeur = getNomProfesseur(idEcole,id ,libelleAnnee , libelleTrimestre,classe) ;
            System.out.println("effectifClasse "+effectifClasse);
            effeG = geteffeG(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            System.out.println("effeG "+effeG);
            effeF = geteffeF(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            System.out.println("effeF "+effeF);
            classF =getclassF(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            System.out.println("classF "+classF);
            classG= getclassG(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            System.out.println("classG "+classG);
            nonclassF= getNonClasseF(idEcole,id,libelleAnnee , libelleTrimestre,classe);

            System.out.println("nonclassF "+nonclassF);
            nonclassG= getNonClasseG(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            System.out.println("nonclassG "+nonclassG);
            nbreMoySup10F = getnbreMoySup10F(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            System.out.println("nbreMoySup10F "+nbreMoySup10F);
            nbreMoySup10G= getnbreMoySup10G(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            System.out.println("nbreMoySup10G "+nbreMoySup10G);
            nbreMoyInf999F= getnbreMoyInf999F(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            System.out.println("nbreMoyInf999F "+nbreMoyInf999F);
            nbreMoyInf999G= getnbreMoyInf999G(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            System.out.println("nbreMoyInf999G "+nbreMoyInf999G);
            nbreMoyInf85F= getnbreMoyInf85F(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            System.out.println("nbreMoyInf85F "+nbreMoyInf85F);
            nbreMoyInf85G= getnbreMoyInf85G(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            System.out.println("nbreMoyInf85G "+nbreMoyInf85G);
            moyClasseF=getmoyClasseF(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            System.out.println("moyClasseF "+moyClasseF);
            moyClasseG=getmoyClasseG(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            System.out.println("moyClasseG "+moyClasseG);

            moyClasse = getmoyClasse(idEcole,id,libelleAnnee , libelleTrimestre,classe) ;
           // moyClasse_ET = getmoyClasseET(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            //moyClasseG_ET = getmoyClasseGET(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            //moyClasseF_ET = getmoyClasseFET(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            //calcul pourcentage


            if(classG !=0)
                pourMoyInf85G= (double) ((nbreMoyInf85G*100d)/classG);

            System.out.println("pourMoyInf85G "+pourMoyInf85G);

            if(classF !=0)
                pourMoyInf85F= (double) ((nbreMoyInf85F*100d)/classF);
            System.out.println("pourMoyInf85F "+pourMoyInf85G);

            if(classG !=0||classF !=0)
                pourMoyInf85 = (double)((nbreMoyInf85F+nbreMoyInf85G)*100d/(classG+classF)) ;

            if(classG !=0)
                pourMoyInf999G=(double) ((nbreMoyInf999G*100d)/classG);
            System.out.println("pourMoyInf999G "+pourMoyInf999G);

            if(classF !=0)
                pourMoyInf999F=(double) ((nbreMoyInf999F*100d)/classF);
            System.out.println("pourMoyInf999F "+pourMoyInf999F);

            if(classG !=0||classF !=0)
                pourMoyInf999 = (double)((nbreMoyInf999G + nbreMoyInf999F)*100d/(classG+classF)) ;

            if(classF !=0)
                pourMoySup10F = (double) ((nbreMoySup10F*100d)/classF);
            System.out.println("pourMoySup10F "+pourMoySup10F);

            if(classG !=0)
                pourMoySup10G = (double) ((nbreMoySup10G*100d)/classG);
            System.out.println("pourMoySup10G "+pourMoySup10G);

            if(classG !=0||classF !=0)
                pourMoySup10 = (double)((nbreMoySup10G+nbreMoySup10F)*100d/(classG+classF)) ;

            //System.out.println("resultatsListElevesDto "+resultatsListElevesDto.toString());

            System.out.println("resultats0 ");
            resultatsListEleves.setMatiere(libelleMatiere);
            System.out.println("resultats1 "+resultatsListEleves.getMatiere());
            resultatsListEleves.setClasse(classe);
            resultatsListEleves.setEffeG(effeG);
            resultatsListEleves.setEffeF(effeF);
            resultatsListEleves.setClassG(classG);
            resultatsListEleves.setClassF(classF);
            resultatsListEleves.setNonclassF(nonclassF);
            resultatsListEleves.setNonclassG(nonclassG);
            resultatsListEleves.setProfesseur(professeur);
           // resultatsListEleves.setCycle(cycle);

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
           // resultatsListEleves.setOrdre_niveau(orderNiveau);

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

    public  String getNomProfesseur(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        Long effectifClasse;
        String effecArray ;
        Integer sum = 0;
        try {
            effecArray = (String) em.createQuery("select distinct d.nom_prenom_professeur from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("matiere",matiere)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setParameter("classe", classe)
                    .getSingleResult() ;


        } catch (NoResultException e){
            return null ;
        }


        return  effecArray ;
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

  public  Long getclassF(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
      Long classF;
      try {
          classF = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and   o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked =:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("isClass","O")
                  .setParameter("matiere",matiere)
                  .setParameter("classe",classe)
                  .setParameter("annee", libelleAnnee)
                  .setParameter("periode", libelleTrimestre)
                  .getSingleResult();

          return  classF ;
      } catch (NoResultException e){
          return 0L ;
      }


  }
    public Long getclassG(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre,String classe){
        Long classG;
        try {
            classG = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  o.sexe=:sexe and o.ecoleId=:idEcole  and d.isRanked=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("matiere",matiere)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("classe", classe)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return classG ;
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
    public  Long getnbreMoySup10F(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Long   nbreMoySup10F = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  o.isClassed=:isClass and  o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne>=:moy and d.isRanked=:isClass and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("matiere",matiere)
                    .setParameter("classe",classe)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10F;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoySup10G(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Long    nbreMoySup10G = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  d.isRanked=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne>=:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("isClass","O")
                    .setParameter("moy",10.0)
                    .setParameter("matiere",matiere)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("classe", classe)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoySup10G ;
        } catch (NoResultException e){
            return 0L ;
        }

    }
    public Long getnbreMoyInf999F(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Long nbreMoyInf999F = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  d.isRanked=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne>=:moy and d.moyenne <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee  and o.libelleClasse=:classe and d.matiereCode=:matiere ")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("isClass","O")
                    .setParameter("matiere",matiere)
                    .setParameter("classe",classe)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult();
            return  nbreMoyInf999F    ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public Long getnbreMoyInf999G(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Long nbreMoyInf999G = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  d.isRanked=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne>=:moy and d.moyenne <=:moy2 and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("moy2",9.99)
                    .setParameter("matiere",matiere)
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
    public Long getnbreMoyInf85G(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Long   nbreMoyInf85G = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  d.isRanked=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne <:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","MASCULIN")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("matiere",matiere)
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

    public Long getnbreMoyInf85F(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        try {
            Long  nbreMoyInf85F = (Long) em.createQuery("select count(o.id) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  d.isRanked=:isClass and o.sexe=:sexe and o.ecoleId=:idEcole  and d.moyenne<:moy and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere")
                    .setParameter("sexe","FEMININ")
                    .setParameter("idEcole",idEcole)
                    .setParameter("moy",8.5)
                    .setParameter("matiere",matiere)
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
    public  Double getmoyClasse(Long idEcole ,String matiere ,String libelleAnnee , String libelleTrimestre ,  String  classe){
        try {
            Double   moyClasseF = (Double) em.createQuery("select AVG(d.moyenne) from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and   o.ecoleId=:idEcole  and d.isRanked=:isClass  and o.libellePeriode=:periode and o.anneeLibelle=:annee and d.matiereCode=:matiere and o.libelleClasse=:classe")
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
