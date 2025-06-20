package com.vieecoles.services.etats.PvConseilsClasse;

import com.vieecoles.dto.ConseilClasseDto;
import com.vieecoles.dto.NiveauDto2;
import com.vieecoles.dto.ProcesVerbalStatistiqueDisciplineDto;
import com.vieecoles.steph.entities.EcoleHasMatiere;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.services.PersonnelMatiereClasseService;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class MatiereParDisciplinePvServices {
    @Inject
    EntityManager em;
    @Inject
    PersonnelMatiereClasseService persMatClasService;

    public List<ProcesVerbalStatistiqueDisciplineDto> getDiscpline(Long idEcole , String libelleAnnee , String libelleTrimestre , String classe){

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
        List<ProcesVerbalStatistiqueDisciplineDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
       // System.out.println("resultatsListElevesDto Size "+ resultatsListElevesDto.size());
        for (int i=0; i< LongTableau;i++) {
            ProcesVerbalStatistiqueDisciplineDto resultatsListEleves= new ProcesVerbalStatistiqueDisciplineDto();
            long idMatiere = 0;
            Matiere myMatiere = new Matiere();
            String libelleMatiere ;
            String id = String.valueOf(classeNiveauDtoList.get(i).getNiveau());
            idMatiere = Long.parseLong(id);
            System.out.println("idMatiere "+idMatiere);
            myMatiere = Matiere.findById(idMatiere) ;
            libelleMatiere = getCodeLIbelleById(idMatiere);
            effectifClasse= getEffectifParClasse(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            resultatsListEleves.setEffectif(effectifClasse);
            resultatsListEleves.setClasse(classe);
            classF =getclassF(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            classG= getclassG(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            nbreMoySup10F = getnbreMoySup10F(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            nbreMoySup10G= getnbreMoySup10G(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            nbreMoyInf999F= getnbreMoyInf999F(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            nbreMoyInf999G= getnbreMoyInf999G(idEcole,id ,libelleAnnee , libelleTrimestre,classe);
            nbreMoyInf85F= getnbreMoyInf85F(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            nbreMoyInf85G= getnbreMoyInf85G(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            moyClasse = getmoyClasse(idEcole,id,libelleAnnee , libelleTrimestre,classe) ;


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

            resultatsListEleves.setMatiere(libelleMatiere);
            resultatsListEleves.setClasse(classe);

            resultatsListEleves.setNbreMoyenSup10(nbreMoySup10G+nbreMoySup10F);
            resultatsListEleves.setPourMoyenSup10(pourMoySup10);
            resultatsListEleves.setNbreMoyenInf10(nbreMoyInf999G + nbreMoyInf999F);
            resultatsListEleves.setPourMoyenInf10(pourMoyInf999);
            resultatsListEleves.setNbreMoyenInf8_5(nbreMoyInf85F+nbreMoyInf85G);
            resultatsListEleves.setPourMoyenInf8_5(pourMoyInf85);
            resultatsListEleves.setMoyenMatiere(moyClasse);
            resultatsListEleves.setMatiere(libelleMatiere);
            resultatsListEleves.setClasse(classe);
            String nomProfesseur="";
            nomProfesseur= getNomProfesseur(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            resultatsListEleves.setNomProfesseur(nomProfesseur);
            Long idMatiereReal= getidMatiereReal(idEcole,id,libelleAnnee , libelleTrimestre,classe);
            Long idAnnee= getIdAnneeScolaire(idEcole,libelleAnnee,libelleTrimestre);
            Long classeId= getIdClasse(idEcole,libelleAnnee,libelleTrimestre,classe);
            PersonnelMatiereClasse personnel = persMatClasService.findProfesseurByMatiereAndClasse(Long.valueOf(idAnnee),Long.valueOf(classeId) ,Long.valueOf(idMatiereReal) );
            if(personnel!=null)
                resultatsListEleves.setSexeProfesseur(personnel.getPersonnel().getSexe());
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

    public  Long getidMatiereReal(Long idEcole , String matiere ,String libelleAnnee , String libelleTrimestre ,String classe){
        Long effectifClasse;
        Long effecArray ;
        Integer sum = 0;
        try {
            effecArray = (Long) em.createQuery("select distinct d.matiereRealId from DetailBulletin d,Bulletin o    where d.bulletin.id=  o.id and  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe and d.matiereCode=:matiere ")
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

    public  Long  getIdAnneeScolaire(Long idEcole ,String libelleAnnee , String libelleTrimestre){
        try {
            Long  moyClasseG = (Long) em.createQuery("select distinct o.anneeId from Bulletin o where   o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee")
                .setParameter("idEcole",idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0L ;
        }

    }

    public  Long  getIdClasse(Long idEcole ,String libelleAnnee , String libelleTrimestre, String classe){
        try {
            Long  moyClasseG = (Long) em.createQuery("select distinct o.classeId from Bulletin o where   o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.libelleClasse=:classe")
                .setParameter("idEcole",idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .setParameter("classe", classe)
                .getSingleResult();
            return  moyClasseG ;
        } catch (NoResultException e){
            return 0L ;
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
