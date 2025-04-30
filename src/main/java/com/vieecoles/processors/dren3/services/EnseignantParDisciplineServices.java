package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.EnseignantDisciplineDto;
import com.vieecoles.dto.NiveauDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@ApplicationScoped
public class EnseignantParDisciplineServices {
    @Inject
    EntityManager em;
   @Transactional
    public EnseignantDisciplineDto EnseignantParDiscipline(Long idEcole, String libellePeriode , String libelleAnnee){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.matricule) from Bulletin b  where b.ecoleId =:idEcole  and b.libellePeriode=:periode and b.anneeLibelle=:libelleAnnee   order by b.ordreNiveau ,b.nom asc, b.prenoms asc "
               , NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("periode", libellePeriode)
                                 .setParameter("libelleAnnee", libelleAnnee)
                                 .getResultList() ;

        EnseignantDisciplineDto m = new EnseignantDisciplineDto();

       Long nbreFAll1 =0L;Long nbreGAll1=0L;Long nbreFAN1=0L;Long nbreGAN1=0L;Long nbreFEDHC1=0L;Long nbreGEDHC1=0L;Long nbreFEPS1=0L;Long nbreGEPS1=0L;
       Long nbreFESP1=0L; Long nbreGESP1=0L;Long nbreFFR1=0L;Long nbreGFR1=0L;Long nbreFHG1=0L;Long nbreGHG1=0L;
       Long nbreFMATH1=0L;Long nbreGMATH1=0L;Long nbreFPC1=0L;Long nbreGPC1=0L;Long nbreFPHILO1=0L;
       Long nbreGPHILO1=0L;Long nbreFSVT1=0L; Long nbreGSVT1=0L; Long nbreF1=0L;Long nbreG1=0L;Long nbreFAll2=0L;Long nbreGAll2=0L;
       Long nbreFAN2=0L;Long nbreGAN2=0L;Long nbreFEDHC2=0L;Long nbreGEDHC2=0L;Long nbreFEPS2=0L;Long nbreGEPS2=0L;
       Long nbreFESP2=0L;Long nbreGESP2=0L;Long nbreFFR2=0L;Long nbreGFR2=0L;Long nbreFHG2=0L; Long nbreGHG2=0L;Long nbreFMATH2=0L;
       Long nbreGMATH2; Long nbreFPC2=0L;Long nbreGPC2=0L;Long nbreFPHILO2=0L;Long nbreGPHILO2=0L; Long nbreFSVT2=0L;
       Long nbreGSVT2=0L;Long nbreF2=0L;Long nbreG2=0L;

            //
            String mascul="MASCULIN";
            String feminin="FEMININ";

           nbreFAll1 = getNbreProf1(25L,libellePeriode,libelleAnnee ,idEcole,feminin) ;
           nbreGAll1 = getNbreProf1(25L,libellePeriode,libelleAnnee ,idEcole,mascul) ;
           m.setNbreFAll1(nbreFAll1);
           m.setNbreGAll1(nbreGAll1);

           nbreFESP1 = getNbreProf1(21L,libellePeriode,libelleAnnee ,idEcole,feminin) ;
           nbreGESP1 = getNbreProf1(21L,libellePeriode,libelleAnnee ,idEcole,mascul) ;
           m.setNbreFESP1(nbreFESP1);
           m.setNbreGESP1(nbreGESP1);
            nbreFFR1 = getNbreProfFran(libellePeriode,libelleAnnee ,idEcole,feminin) ;
            nbreGFR1 = getNbreProfFran(libellePeriode,libelleAnnee ,idEcole,mascul) ;
             m.setNbreFFR1(nbreFFR1);
             m.setNbreGFR1(nbreGFR1);

            nbreFAN1 = getNbreProf1(5L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGAN1 = getNbreProf1(5L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGAN1(nbreGAN1);
            m.setNbreFAN1(nbreFAN1);

            nbreFEDHC1 = getNbreProf1(11L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGEDHC1 = getNbreProf1(11L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
              m.setNbreGEDHC1(nbreGEDHC1);
              m.setNbreFEDHC1(nbreFEDHC1);

            nbreFEPS1 = getNbreProf1(10L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGEPS1 = getNbreProf1(10L,libellePeriode,libelleAnnee ,idEcole, mascul) ;

            m.setNbreFEPS1(nbreFEPS1);
            m.setNbreGEPS1(nbreGEPS1);

            nbreFHG1 = getNbreProf1(6L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGHG1 = getNbreProf1(6L,libellePeriode,libelleAnnee ,idEcole, mascul) ;

            m.setNbreGHG1(nbreGHG1);
            m.setNbreFHG1(nbreFHG1);

            nbreFMATH1 = getNbreProf1(7L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGMATH1 = getNbreProf1(7L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGMATH1(nbreGMATH1);
            m.setNbreFMATH1(nbreFMATH1);

            nbreFPC1 = getNbreProf1(8L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGPC1 = getNbreProf1(8L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGPC1(nbreGPC1);
            m.setNbreFPC1(nbreFPC1);

            nbreFPHILO1 = getNbreProf1(26L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGPHILO1 = getNbreProf1(26L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGPHILO1(nbreGPHILO1);
            m.setNbreFPHILO1(nbreFPHILO1);

            nbreFSVT1 = getNbreProf1(9L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGSVT1 = getNbreProf1(9L,libellePeriode,libelleAnnee ,idEcole, mascul) ;

            m.setNbreGSVT1(nbreGSVT1);
            m.setNbreFSVT1(nbreFSVT1);

            nbreF1 = getNbreProfEtablissement1(libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreG1 = getNbreProfEtablissement1(libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreG1(nbreG1);
            m.setNbreF1(nbreF1);

       nbreFAll2 = getNbreProf2(25L, libellePeriode, libelleAnnee, idEcole, feminin);
       nbreGAll2 = getNbreProf2(25L, libellePeriode, libelleAnnee, idEcole, mascul);
       m.setNbreFAll2(nbreFAll2);
       m.setNbreGAll2(nbreGAll2);

       nbreFESP2 = getNbreProf2(21L, libellePeriode, libelleAnnee, idEcole, feminin);
       nbreGESP2 = getNbreProf2(21L, libellePeriode, libelleAnnee, idEcole, mascul);
       m.setNbreFESP2(nbreFESP2);
       m.setNbreGESP2(nbreGESP2);


            nbreFFR2 = getNbreProf2(1L,libellePeriode,libelleAnnee ,idEcole,feminin) ;
            nbreGFR2 = getNbreProf2(1L,libellePeriode,libelleAnnee ,idEcole,mascul) ;
            m.setNbreGFR2(nbreGFR2);
            m.setNbreFFR2(nbreFFR2);

            nbreFAN2 = getNbreProf2(5L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGAN2 = getNbreProf2(5L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGAN2(nbreGAN2);
            m.setNbreFAN2(nbreFAN2);

            nbreFEDHC2 = getNbreProf2(11L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGEDHC2 = getNbreProf2(11L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGEDHC2(nbreGEDHC2);
            m.setNbreFEDHC2(nbreFEDHC2);

            nbreFEPS2 = getNbreProf2(10L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGEPS2 = getNbreProf2(10L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreFEPS2(nbreFEPS2);
            m.setNbreGEPS2(nbreGEPS2);

            nbreFHG2 = getNbreProf2(6L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGHG2 = getNbreProf2(6L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGHG2(nbreGHG2);
            m.setNbreFHG2(nbreFHG2);

            nbreFMATH2 = getNbreProf2(7L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGMATH2 = getNbreProf2(7L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGMATH2(nbreGMATH2);
            m.setNbreFMATH2(nbreFMATH2);

            nbreFPC2 = getNbreProf2(8L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGPC2 = getNbreProf2(8L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGPC2(nbreGPC2);
            m.setNbreFPC2(nbreFPC2);

            nbreFPHILO2 = getNbreProf2(26L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGPHILO2 = getNbreProf2(26L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGPHILO2(nbreGPHILO2);
            m.setNbreFPHILO2(nbreFPHILO2);

            nbreFSVT2 = getNbreProf2(9L,libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreGSVT2 = getNbreProf2(9L,libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreGSVT2(nbreGSVT2);
            m.setNbreFSVT2(nbreFSVT2);

            nbreF2 = getNbreProfEtablissement2(libellePeriode,libelleAnnee ,idEcole, feminin) ;
            nbreG2 = getNbreProfEtablissement2(libellePeriode,libelleAnnee ,idEcole, mascul) ;
            m.setNbreG2(nbreG2);
            m.setNbreF2(nbreF2);
        return  m ;
    }



    public  Long getNbreProf1(Long libelleMatiere,String periode ,String libelleAnnee ,Long idEcole ,String sexe){
        try {
            Long   moyClasseF = (Long) em.createQuery("select count(distinct d.nom_prenom_professeur)  from DetailBulletin  d join d.bulletin b  where  d.matiereId=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                    " and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.ordreNiveau <5 and d.sexeProfesseur=:sexe")
                .setParameter("libelleMatiere",libelleMatiere)
                .setParameter("periode",periode)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("idEcole", idEcole)
                .setParameter("sexe", sexe)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long getNbreProfEtablissement1(String periode ,String libelleAnnee ,Long idEcole ,String sexe){
        try {
            Long   moyClasseF = (Long) em.createQuery("select count(distinct d.nom_prenom_professeur)  from DetailBulletin  d join d.bulletin b  where   b.anneeLibelle=:libelleAnnee " +
                    " and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.ordreNiveau <5 and d.sexeProfesseur=:sexe")
                .setParameter("periode",periode)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("idEcole", idEcole)
                .setParameter("sexe", sexe)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }
    }
    public  Long getNbreProfEtablissement2(String periode ,String libelleAnnee ,Long idEcole ,String sexe){
        try {
            Long   moyClasseF = (Long) em.createQuery("select count(distinct d.nom_prenom_professeur)  from DetailBulletin  d join d.bulletin b  where   b.anneeLibelle=:libelleAnnee " +
                    " and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.ordreNiveau >=5 and d.sexeProfesseur=:sexe")
                .setParameter("periode",periode)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("idEcole", idEcole)
                .setParameter("sexe", sexe)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long getNbreProf2(Long libelleMatiere,String periode ,String libelleAnnee ,Long idEcole ,String sexe){
        try {
            Long   moyClasseF = (Long) em.createQuery("select count(distinct d.nom_prenom_professeur)  from DetailBulletin  d join d.bulletin b  where  d.matiereId=:libelleMatiere  and b.anneeLibelle=:libelleAnnee " +
                    " and b.libellePeriode=:periode and b.ecoleId=:idEcole and b.ordreNiveau>=5 and d.sexeProfesseur=:sexe")
                .setParameter("libelleMatiere",libelleMatiere)
                .setParameter("periode",periode)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("idEcole", idEcole)
                .setParameter("sexe", sexe)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }
    }

    public  Long getNbreProfFran(String periode ,String libelleAnnee ,Long idEcole ,String sexe){
        try {
            Long   moyClasseF = (Long) em.createQuery("select count(distinct d.nom_prenom_professeur)  from DetailBulletin  d join d.bulletin b  where  d.matiereId in (2,3,4)  and b.anneeLibelle=:libelleAnnee " +
                    " and b.libellePeriode=:periode and b.ecoleId=:idEcole and d.sexeProfesseur=:sexe ")
                .setParameter("periode",periode)
                .setParameter("libelleAnnee", libelleAnnee)
                .setParameter("idEcole", idEcole)
                .setParameter("sexe", sexe)
                .getSingleResult();
            return  moyClasseF ;
        } catch (NoResultException e){
            return 0L ;
        }

    }







}
