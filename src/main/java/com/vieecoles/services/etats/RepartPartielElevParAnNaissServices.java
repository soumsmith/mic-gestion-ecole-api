package com.vieecoles.services.etats;

import com.vieecoles.dto.DateNaissNiveauDto;
import com.vieecoles.dto.NbreAnneeDto;
import com.vieecoles.dto.RepartitionEleveParAnNaissDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RepartPartielElevParAnNaissServices {
    @Inject
    EntityManager em;

    public List<RepartitionEleveParAnNaissDto> CalculRepartElevParAnnNaiss(Long idEcole ,String libelleAnnee ){

        List<DateNaissNiveauDto> dateNiveauDtoList = new ArrayList<>() ;
        TypedQuery<DateNaissNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.DateNaissNiveauDto(SUBSTRING(e.elevedate_naissance,1,4)) from Inscriptions o , eleve  e where o.eleve.eleveid = e.eleveid and o.annee_scolaire.annee_scolaire_libelle=:annee and o.ecole.ecoleid =:idEcole  " +
                " group by SUBSTRING(e.elevedate_naissance,1,4)  ", DateNaissNiveauDto.class);

        dateNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                             . getResultList() ;

  System.out.println("dateNiveauDtoList "+dateNiveauDtoList.toString());

        System.out.println("Longueur Tableau" +dateNiveauDtoList.size());
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

            an6F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Sixième","FEMININ" );
            //System.out.println("DateNaissance "+dateNiveauDtoList.get(i).getDateNaiss());
            an6G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Sixième","MASCULIN" );
            t6 = geTotaltRepartiParAnneeParNiveau(idEcole,"Sixième" ,libelleAnnee ) ;

            an5F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Cinquième","FEMININ" );
            an5G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Cinquième","MASCULIN" );
            t5 = geTotaltRepartiParAnneeParNiveau(idEcole,"Cinquième" ,libelleAnnee ) ;

            an4G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Quatrième","MASCULIN" );
            an4F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Quatrième","FEMININ" );
            t4 = geTotaltRepartiParAnneeParNiveau(idEcole,"Quatrième" ,libelleAnnee ) ;

            an3G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Troisième","MASCULIN" );
            an3F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Troisième","FEMININ" );
            t3 = geTotaltRepartiParAnneeParNiveau(idEcole,"Troisième" ,libelleAnnee ) ;

            an2AG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde A","MASCULIN" );
            an2AF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde A","FEMININ" );
            t2A = geTotaltRepartiParAnneeParNiveau(idEcole,"Seconde A" ,libelleAnnee ) ;

            an2CG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde C","MASCULIN" );
            an2CF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde C","FEMININ" );
            t2C = geTotaltRepartiParAnneeParNiveau(idEcole,"Seconde C" ,libelleAnnee) ;

            an1AG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première A","MASCULIN" );
            an1AF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première A","FEMININ");
            t1A = geTotaltRepartiParAnneeParNiveau(idEcole,"Première A" ,libelleAnnee ) ;

            an1CG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première C","MASCULIN" );
            an1CF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première C","FEMININ" );
            t1C = geTotaltRepartiParAnneeParNiveau(idEcole,"Première C" ,libelleAnnee ) ;

            an1DG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première D","MASCULIN" );
            an1DF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première D","FEMININ" );
            t1D = geTotaltRepartiParAnneeParNiveau(idEcole,"Première D" ,libelleAnnee ) ;

            anTAG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale A","MASCULIN" );
            anTAF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale A","FEMININ" );
            tTA = geTotaltRepartiParAnneeParNiveau(idEcole,"Terminale A" ,libelleAnnee ) ;

            anTDG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale D","MASCULIN" );
            anTDF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale D","FEMININ" );
            tTD = geTotaltRepartiParAnneeParNiveau(idEcole,"Terminale D" ,libelleAnnee ) ;

            anTCG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale C","MASCULIN" );
            anTCF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale C","FEMININ" );
            tTC = geTotaltRepartiParAnneeParNiveau(idEcole,"Terminale C" ,libelleAnnee ) ;

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


  public  Long getRepartiParAnneeParNiveau(Long idEcole ,String annee, String niveau, String sexe ){
      Long repartParAnneParNiv ;
      try {
          NbreAnneeDto dateNiveauDtoList = new NbreAnneeDto() ;
          TypedQuery<NbreAnneeDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NbreAnneeDto(count(b.id),SUBSTRING(b.eleve.dateNaissance,1,4) )  from Inscription  b  where b.eleve.sexe =:sexe and b.ecole.id =:idEcole  and b.branche.niveau.libelle =:niveau and SUBSTRING(b.eleve.dateNaissance,1,4) =:annee  group by SUBSTRING(b.eleve.dateNaissance,1,4)  "
                  , NbreAnneeDto.class);
          dateNiveauDtoList =  q.setParameter("sexe",sexe)
                  .setParameter("idEcole",idEcole)
                  .setParameter("annee",annee)
                  .setParameter("niveau",niveau)
                  .getSingleResult();

          return  repartParAnneParNiv = dateNiveauDtoList.getNbre() ;
      } catch (NoResultException e){
          return 0L ;
      }

  }



    public  Long geTotaltRepartiParAnneeParNiveau(Long idEcole , String niveau ,String libelleAnnee ){
        Long repartParAnneParNiv = null;
        try {
            Long dateNiveauDtoList  ;
            TypedQuery<Long> q = (TypedQuery<Long>) em.createQuery( "SELECT count(b.id)  from Inscription as b  where  b.ecole.id=:idEcole and b.branche.niveau.libelle=:niveau  and b.annee.libelle=:annee  group by  b.branche.niveau.libelle  "
                    );
            dateNiveauDtoList =  q.setParameter("idEcole",idEcole)
                                  .setParameter("niveau",niveau)
                                    .setParameter("annee", libelleAnnee)
                                   .getSingleResult();

            return  dateNiveauDtoList  ;
        } catch (NoResultException e){
            return 0L ;
        }

    }



}
