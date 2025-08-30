package com.vieecoles.services.etats;

import com.vieecoles.dto.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RepartitionElevParAnNaissServices {
    @Inject
    EntityManager em;

    public List<RepartitionEleveParAnNaissDto> CalculRepartElevParAnnNaiss(Long idEcole ,String libelleAnnee , String libelleTrimestre){

        List<DateNaissNiveauDto> dateNiveauDtoList = new ArrayList<>() ;
        TypedQuery<DateNaissNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.DateNaissNiveauDto(SUBSTRING(b.dateNaissance,1,4)) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by SUBSTRING(b.dateNaissance,1,4)  ", DateNaissNiveauDto.class);

        dateNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                             . getResultList() ;


      int LongTableau =dateNiveauDtoList.size() ;

      Long an6F, an5F,an4F,an3F,an2AF,an2CF,an1AF,an1CF,an1DF,anTAF,anTCF,
              anTDF,an6G,an5G,an4G,an3G,an2AG,an2CG,an1AG,an1CG,an1DG,anTAG,anTCG,anTDG,
        t6,t5,t4,t3,t2A,t2C,t1A,t1C,t1D,tTA,tTC,tTD
              ;

       Integer effectifClasse ;
        List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>(LongTableau);
       // System.out.println("resultatsListElevesDto Size "+ repartitionEleveParAnNaissDto.size());
        for (int i=0; i< LongTableau;i++) {
            RepartitionEleveParAnNaissDto repartParElevParNiveau= new RepartitionEleveParAnNaissDto();

            an6F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Sixième","FEMININ" , libelleTrimestre);
            //System.out.println("DateNaissance "+dateNiveauDtoList.get(i).getDateNaiss());
            an6G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Sixième","MASCULIN" , libelleTrimestre);
            t6 = geTotaltRepartiParAnneeParNiveau(idEcole,"Sixième" ,libelleAnnee , libelleTrimestre) ;

            an5F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Cinquième","FEMININ" , libelleTrimestre);
            an5G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Cinquième","MASCULIN" , libelleTrimestre);
            t5 = geTotaltRepartiParAnneeParNiveau(idEcole,"Cinquième" ,libelleAnnee , libelleTrimestre) ;

            an4G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Quatrième","MASCULIN" , libelleTrimestre);
            an4F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Quatrième","FEMININ" , libelleTrimestre);
            t4 = geTotaltRepartiParAnneeParNiveau(idEcole,"Quatrième" ,libelleAnnee , libelleTrimestre) ;

            an3G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Troisième","MASCULIN" , libelleTrimestre);
            an3F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Troisième","FEMININ" , libelleTrimestre);
            t3 = geTotaltRepartiParAnneeParNiveau(idEcole,"Troisième" ,libelleAnnee , libelleTrimestre) ;

            an2AG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde A","MASCULIN" , libelleTrimestre);
            an2AF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde A","FEMININ" , libelleTrimestre);
            t2A = geTotaltRepartiParAnneeParNiveau(idEcole,"Seconde A" ,libelleAnnee , libelleTrimestre) ;

            an2CG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde C","MASCULIN" , libelleTrimestre);
            an2CF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde C","FEMININ" , libelleTrimestre);
            t2C = geTotaltRepartiParAnneeParNiveau(idEcole,"Seconde C" ,libelleAnnee , libelleTrimestre) ;

            an1AG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première A","MASCULIN" , libelleTrimestre);
            an1AF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première A","FEMININ" , libelleTrimestre);
            t1A = geTotaltRepartiParAnneeParNiveau(idEcole,"Première A" ,libelleAnnee , libelleTrimestre) ;

            an1CG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première C","MASCULIN" , libelleTrimestre);
            an1CF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première C","FEMININ" , libelleTrimestre);
            t1C = geTotaltRepartiParAnneeParNiveau(idEcole,"Première C" ,libelleAnnee , libelleTrimestre) ;

            an1DG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première D","MASCULIN" , libelleTrimestre);
            an1DF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première D","FEMININ" , libelleTrimestre);
            t1D = geTotaltRepartiParAnneeParNiveau(idEcole,"Première D" ,libelleAnnee , libelleTrimestre) ;

            anTAG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale A","MASCULIN" , libelleTrimestre);
            anTAF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale A","FEMININ" , libelleTrimestre);
            tTA = geTotaltRepartiParAnneeParNiveau(idEcole,"Terminale A" ,libelleAnnee , libelleTrimestre) ;

            anTDG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale D","MASCULIN" , libelleTrimestre);
            anTDF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale D","FEMININ" , libelleTrimestre);
            tTD = geTotaltRepartiParAnneeParNiveau(idEcole,"Terminale D" ,libelleAnnee , libelleTrimestre) ;

            anTCG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale C","MASCULIN" , libelleTrimestre);
            anTCF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale C","FEMININ" , libelleTrimestre);
            tTC = geTotaltRepartiParAnneeParNiveau(idEcole,"Terminale C" ,libelleAnnee , libelleTrimestre) ;

            repartParElevParNiveau.setAn6F(an6F);
            repartParElevParNiveau.setAn6G(an6G);

            repartParElevParNiveau.setAn5F(an5F);
            repartParElevParNiveau.setAn5G(an5G);

            repartParElevParNiveau.setAn4F(an4F);
            repartParElevParNiveau.setAn4G(an4G);

            repartParElevParNiveau.setAn3F(an3F);
            repartParElevParNiveau.setAn3G(an3G);

            repartParElevParNiveau.setAn2AF(an2AF);
            repartParElevParNiveau.setAn2AG(an2AG);

            repartParElevParNiveau.setAn2CF(an2CF);
            repartParElevParNiveau.setAn2CG(an2CG);

            repartParElevParNiveau.setAn1AF(an1AF);
            repartParElevParNiveau.setAn1AG(an1AG);

            repartParElevParNiveau.setAn1CF(an1CF);
            repartParElevParNiveau.setAn1CG(an1CG);

            repartParElevParNiveau.setAn1DF(an1DF);
            repartParElevParNiveau.setAn1DG(an1DG);

            repartParElevParNiveau.setAnTDF(anTDF);
            repartParElevParNiveau.setAnTDG(anTDG);

            repartParElevParNiveau.setAnTAF(anTAF);
            repartParElevParNiveau.setAnTAG(anTAG);

            repartParElevParNiveau.setAnTCF(anTCF);
            repartParElevParNiveau.setAnTCG(anTCG);
            repartParElevParNiveau.setT6(t6);
            repartParElevParNiveau.setT5(t5);
            repartParElevParNiveau.setT4(t4);
            repartParElevParNiveau.setT3(t3);
            repartParElevParNiveau.setT2A(t2A);
            repartParElevParNiveau.setT2C(t2C);
            repartParElevParNiveau.setT1A(t1A);
            repartParElevParNiveau.setT1D(t1D);
            repartParElevParNiveau.setT1C(t1C);
            repartParElevParNiveau.settTA(tTA);
            repartParElevParNiveau.settTD(tTD);
            repartParElevParNiveau.settTC(tTC);
            repartParElevParNiveau.setNiveau("niveau1");
                repartParElevParNiveau.setAnnee(dateNiveauDtoList.get(i).getDateNaiss());
            repartitionEleveParAnNaissDto.add(repartParElevParNiveau);

        }

        return  repartitionEleveParAnNaissDto ;
    }


  public  Long getRepartiParAnneeParNiveau(Long idEcole ,String annee, String niveau, String sexe , String libelleTrimestre){
      Long repartParAnneParNiv ;
      try {
          NbreAnneeDto dateNiveauDtoList = new NbreAnneeDto() ;
          TypedQuery<NbreAnneeDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NbreAnneeDto(count(b.id),SUBSTRING(b.dateNaissance,1,4) )  from Bulletin as b  where b.sexe=:sexe and b.ecoleId=:idEcole and b.libellePeriode=:periode and b.niveau=:niveau and SUBSTRING(b.dateNaissance,1,4) =:annee  group by SUBSTRING(b.dateNaissance,1,4)  "
                  , NbreAnneeDto.class);
          dateNiveauDtoList =  q.setParameter("sexe",sexe)
                  .setParameter("idEcole",idEcole)
                  .setParameter("annee",annee)
                  .setParameter("periode", libelleTrimestre)
                  .setParameter("niveau",niveau)
                  .getSingleResult();

          return  repartParAnneParNiv = dateNiveauDtoList.getNbre() ;
      } catch (NoResultException e){
          return 0L ;
      }

  }



    public  Long geTotaltRepartiParAnneeParNiveau(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        Long repartParAnneParNiv = null;
        try {
            Long dateNiveauDtoList  ;
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(b.id)  from Bulletin as b  where  b.ecoleId=:idEcole and b.niveau=:niveau and b.libellePeriode=:periode and b.anneeLibelle=:annee  group by  b.niveau  "
                    );
            dateNiveauDtoList =  q.setParameter("idEcole",idEcole)
                                  .setParameter("niveau",niveau)
                                    .setParameter("annee", libelleAnnee)
                                    .setParameter("periode", libelleTrimestre)
                                   .getSingleResult();

            return  dateNiveauDtoList  ;
        } catch (NoResultException e){
            return 0L ;
        }

    }



}
