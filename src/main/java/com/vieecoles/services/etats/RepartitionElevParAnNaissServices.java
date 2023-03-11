package com.vieecoles.services.etats;

import com.vieecoles.dto.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RepartitionElevParAnNaissServices {
    @Inject
    EntityManager em;

    public List<RepartitionEleveParAnNaissDto> CalculRepartElevParAnnNaiss(Long idEcole){

        List<DateNaissNiveauDto> dateNiveauDtoList = new ArrayList<>() ;
        TypedQuery<DateNaissNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.DateNaissNiveauDto(SUBSTRING(b.dateNaissance,1,4),b.niveau) from Bulletin b  where b.ecoleId =:idEcole " +
                "group by SUBSTRING(b.dateNaissance,1,4) ,b.niveau ", DateNaissNiveauDto.class);

        dateNiveauDtoList = q.setParameter("idEcole", idEcole)
                             . getResultList() ;

  System.out.println("dateNiveauDtoList "+dateNiveauDtoList.toString());

        System.out.println("Longueur Tableau" +dateNiveauDtoList.size());
      int LongTableau =dateNiveauDtoList.size() ;

      Long an6F, an5F,an4F,an3F,an2AF,an2CF,an1AF,an1CF,an1DF,anTAF,anTCF,
              anTDF,an6G,an5G,an4G,an3G,an2AG,an2CG,an1AG,an1CG,an1DG,anTAG,anTCG,anTDG;

       Integer effectifClasse ;
        List<RepartitionEleveParAnNaissDto> repartitionEleveParAnNaissDto = new ArrayList<>(LongTableau);
        System.out.println("resultatsListElevesDto Size "+ repartitionEleveParAnNaissDto.size());
        for (int i=0; i< LongTableau;i++) {
            RepartitionEleveParAnNaissDto repartParElevParNiveau= new RepartitionEleveParAnNaissDto();

            an6F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Sixième","FEMININ");
            an6G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Sixième","MASCULIN");

            an5F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Cinquième","FEMININ");
            an5G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Cinquième","MASCULIN");

            an4G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Quatrième","MASCULIN");
            an4F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Quatrième","FEMININ");

            an3G = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Troisième","MASCULIN");
            an3F = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Troisième","FEMININ");

            an2AG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde A","MASCULIN");
            an2AF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde A","FEMININ");

            an2CG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde C","MASCULIN");
            an2CF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Seconde C","FEMININ");

            an1AG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première A","MASCULIN");
            an1AF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première A","FEMININ");

            an1CG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première C","MASCULIN");
            an1CF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première C","FEMININ");

            an1DG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première D","MASCULIN");
            an1DF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Première D","FEMININ");

            anTAG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale A","MASCULIN");
            anTAF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale A","FEMININ");

            anTDG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale D","MASCULIN");
            anTDF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale D","FEMININ");

            anTCG = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale C","MASCULIN");
            anTCF = getRepartiParAnneeParNiveau(idEcole,dateNiveauDtoList.get(i).getDateNaiss(),"Terminale C","FEMININ");

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
            repartParElevParNiveau.setNiveau(dateNiveauDtoList.get(i).getNiveau());
            repartParElevParNiveau.setAnnee(dateNiveauDtoList.get(i).getDateNaiss());
            repartitionEleveParAnNaissDto.add(repartParElevParNiveau);

        }

        return  repartitionEleveParAnNaissDto ;
    }


  public  Long getRepartiParAnneeParNiveau(Long idEcole ,String annee, String niveau, String sexe){
      Long repartParAnneParNiv ;
      try {
          NbreAnneeDto dateNiveauDtoList = new NbreAnneeDto() ;
          TypedQuery<NbreAnneeDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NbreAnneeDto(count(b.id),SUBSTRING(b.dateNaissance,1,4) )  from Bulletin as b  where b.sexe=:sexe and b.ecoleId=:idEcole and b.niveau=:niveau and SUBSTRING(b.dateNaissance,1,4) =:annee  group by SUBSTRING(b.dateNaissance,1,4) , b.niveau  "
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



}
