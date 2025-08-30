package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.EffectifiConseilClasseDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class EffectifConseilClassePoiServices {
    @Inject
    EntityManager em;

    public List<EffectifiConseilClasseDto> CalculResultatsEleveAffecte(Long idEcole , String libelleAnnee , String libelleTrimestre, int order ){

        List<ClasseNiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<ClasseNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.ClasseNiveauDto(b.libelleClasse ,b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ordreNiveau=:order" +
                " group by b.libelleClasse ,b.niveau order by b.libelleClasse,b.niveau", ClasseNiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                          //.setParameter("affecte", "AFFECTE")
                            .setParameter("periode", libelleTrimestre)
                              .setParameter("annee", libelleAnnee)
                              .setParameter("order", order)
                           . getResultList() ;

      int LongTableau =classeNiveauDtoList.size() ;
          String niveau;
        Long  effeG,effeF;
        Long effEtablisseG,effEtablisseF;
       Integer effectifClasse,orderNiveau ;
        List<EffectifiConseilClasseDto> resultatsListElevesDto = new ArrayList<>(LongTableau);
        System.out.println("resultatsListElevesDto Size "+ resultatsListElevesDto.size());
        for (int i=0; i< LongTableau;i++) {
          EffectifiConseilClasseDto resultatsListEleves= new EffectifiConseilClasseDto();
            orderNiveau= getOrderNiveau(classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);
            effectifClasse= getEffectifParClasse(idEcole,classeNiveauDtoList.get(i).getClasse(),classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            effeG = geteffeG(idEcole,classeNiveauDtoList.get(i).getClasse(),classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);
           effeF = geteffeF(idEcole,classeNiveauDtoList.get(i).getClasse(),classeNiveauDtoList.get(i).getNiveau(),libelleAnnee , libelleTrimestre);

          effEtablisseG = geteffEtablissG(idEcole,libelleAnnee , libelleTrimestre);
          effEtablisseF = geteffEtablissF(idEcole,libelleAnnee , libelleTrimestre);


          resultatsListEleves.setNiveau(classeNiveauDtoList.get(i).getNiveau());


            resultatsListEleves.setEffeG(effeG);
            resultatsListEleves.setEffeF(effeF);
          resultatsListEleves.setEffEtabliG(effEtablisseG);
          resultatsListEleves.setEffEtabliF(effEtablisseF);


            resultatsListElevesDto.add(resultatsListEleves) ;


        }

        return  resultatsListElevesDto ;
    }
    public  Integer getOrderNiveau(String niveau ,String annee , String periode){
        Integer ordNiveau;
        try {
            ordNiveau = (Integer) em.createQuery("select distinct o.ordreNiveau from Bulletin o where  o.niveau=:niveau and" +
                            " o.anneeLibelle=:annee and o.libellePeriode=: periode ")
                     .setParameter("niveau",niveau)
                    .setParameter("annee",annee)
                    .setParameter("periode",periode)
                    .getSingleResult();
        } catch (NoResultException e){
            return 0 ;
        }
        return  ordNiveau ;
    }
  public  Integer getEffectifParClasse(Long idEcole ,String classe, String niveau ,String annee , String periode){
       Integer effectifClasse;
      try {
          effectifClasse = (Integer) em.createQuery("select o.effectif from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle =:annee group by o.libelleClasse , o.niveau,o.effectif having o.libelleClasse=:classe and o.niveau=:niveau"
                          )
                  .setParameter("idEcole",idEcole)
                  .setParameter("classe",classe)
                  .setParameter("niveau",niveau)
                  .setParameter("periode",periode)
                  .setParameter("annee",annee)
                  .getSingleResult();
      } catch (NoResultException e){
          return 0 ;
      }


  return  effectifClasse ;
  }
  public  Long geteffeF(Long idEcole ,String classe, String niveau ,String annee , String periode){
      Long effeF ;
      try {
          return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.anneeLibelle=:annee and o.libellePeriode=:periode group by o.libelleClasse , o.niveau having o.libelleClasse=:classe and o.niveau=:niveau ")
                  .setParameter("sexe","FEMININ")
                  .setParameter("idEcole",idEcole)
                  .setParameter("classe",classe)
                  .setParameter("niveau",niveau)
                  .setParameter("annee",annee)
                  .setParameter("periode",periode)
                  .getSingleResult();
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long geteffEtablissF(Long idEcole  ,String annee , String periode){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.anneeLibelle=:annee and o.libellePeriode=:periode ")
          .setParameter("sexe","FEMININ")
          .setParameter("idEcole",idEcole)
          .setParameter("annee",annee)
          .setParameter("periode",periode)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }


  }

  public  Long geteffeG(Long idEcole ,String classe, String niveau ,String annee , String periode){

      Long  effeG ;
      try {
          effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole " +
                          " and o.libellePeriode=:periode and o.anneeLibelle =:annee group by o.libelleClasse , o.niveau having o.libelleClasse=:classe and o.niveau=:niveau")
                  .setParameter("sexe","MASCULIN")
                  .setParameter("idEcole",idEcole)
                  .setParameter("classe",classe)
                  .setParameter("niveau",niveau)
                  .setParameter("annee",annee)
                  .setParameter("periode",periode)
                  .getSingleResult();
          return  effeG ;
      } catch (NoResultException e){
          return 0L ;
      }


  }

  public  Long geteffEtablissG(Long idEcole  ,String annee , String periode){

    Long  effeG ;
    try {
      effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole " +
              " and o.libellePeriode=:periode and o.anneeLibelle =:annee ")
          .setParameter("sexe","MASCULIN")
          .setParameter("idEcole",idEcole)
          .setParameter("annee",annee)
          .setParameter("periode",periode)
          .getSingleResult();
      return  effeG ;
    } catch (NoResultException e){
      return 0L ;
    }


  }





}
