package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.ApprocheGenreDto;
import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.BoursiersDto;
import com.vieecoles.dto.DateNaissNiveauDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class StatistiqueBoursierServices {
    @Inject
    EntityManager em;

    public BoursiersDto CalculRepartElevParAnnNaiss(Long idEcole , String libelleAnnee , String libelleTrimestre){

        List<DateNaissNiveauDto> dateNiveauDtoList = new ArrayList<>() ;
        TypedQuery<DateNaissNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.DateNaissNiveauDto(SUBSTRING(b.dateNaissance,1,4)) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by SUBSTRING(b.dateNaissance,1,4)  ", DateNaissNiveauDto.class);

        dateNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                             . getResultList() ;


      int LongTableau =dateNiveauDtoList.size() ;

      Long effectifBoursier6G;Long effectifBoursier5G;Long effectifBoursier4G;Long effectifBoursier3G;Long effectifBoursier2AG;
      Long effectifBoursier2CG;Long effectifBoursier1AG;Long effectifBoursier1CG;Long effectifBoursier1DG;Long effectifBoursierTleAG;
      Long effectifBoursierTleCG;Long effectifBoursierTleDG;Long effectifBoursierTotalEtG;Long effectifBoursier6F;Long effectifBoursier5F;
      Long effectifBoursier4F;Long effectifBoursier3F;Long effectifBoursier2AF;Long effectifBoursier2CF;Long effectifBoursier1AF;
      Long effectifBoursier1CF;Long effectifBoursier1DF;Long effectifBoursierTleAF;Long effectifBoursierTleCF;Long effectifBoursierTleDF;
      Long effectifBoursierTotalEtF;

       Integer effectifClasse ;
      BoursiersDto m = new BoursiersDto();
      effectifBoursier6F=geteffeBoursierF(idEcole,1,libelleAnnee,libelleTrimestre) ;
      effectifBoursier6G=geteffeBoursierG(idEcole,1,libelleAnnee,libelleTrimestre) ;
      m.setEffectifBoursier6G(effectifBoursier6G);
      m.setEffectifBoursier6F(effectifBoursier6F);

      effectifBoursier5F=geteffeBoursierF(idEcole,2,libelleAnnee,libelleTrimestre) ;
      effectifBoursier5G=geteffeBoursierG(idEcole,2,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier5G(effectifBoursier5G);
      m.setEffectifBoursier5F(effectifBoursier5F);

      effectifBoursier4F=geteffeBoursierF(idEcole,3,libelleAnnee,libelleTrimestre) ;
      effectifBoursier4G=geteffeBoursierG(idEcole,3,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier4G(effectifBoursier4G);
      m.setEffectifBoursier4F(effectifBoursier4F);

      effectifBoursier3F=geteffeBoursierF(idEcole,4,libelleAnnee,libelleTrimestre) ;
      effectifBoursier3G=geteffeBoursierG(idEcole,4,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier3G(effectifBoursier3G);
      m.setEffectifBoursier3F(effectifBoursier3F);

      effectifBoursier2AF=geteffeBoursierF(idEcole,5,libelleAnnee,libelleTrimestre) ;
      effectifBoursier2AG=geteffeBoursierG(idEcole,5,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier2AG(effectifBoursier2AG);
      m.setEffectifBoursier2AF(effectifBoursier2AF);

      effectifBoursier2CF=geteffeBoursierF(idEcole,6,libelleAnnee,libelleTrimestre) ;
      effectifBoursier2CG=geteffeBoursierG(idEcole,6,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier2CG(effectifBoursier2CG);
      m.setEffectifBoursier2CF(effectifBoursier2CF);

      effectifBoursier1AF=geteffeBoursierF(idEcole,7,libelleAnnee,libelleTrimestre) ;
      effectifBoursier1AG=geteffeBoursierG(idEcole,7,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier1AG(effectifBoursier1AG);
      m.setEffectifBoursier1AF(effectifBoursier1AF);

      effectifBoursier1CF=geteffeBoursierF(idEcole,8,libelleAnnee,libelleTrimestre) ;
      effectifBoursier1CG=geteffeBoursierG(idEcole,8,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier1CG(effectifBoursier1CG);
      m.setEffectifBoursier1CF(effectifBoursier1CF);

      effectifBoursier1DF=geteffeBoursierF(idEcole,9,libelleAnnee,libelleTrimestre) ;
      effectifBoursier1DG=geteffeBoursierG(idEcole,9,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursier1DG(effectifBoursier1DG);
      m.setEffectifBoursier1DF(effectifBoursier1DF);

      effectifBoursierTleAF=geteffeBoursierF(idEcole,10,libelleAnnee,libelleTrimestre) ;
      Long effectifBoursierTleA1F=geteffeBoursierF(idEcole,11,libelleAnnee,libelleTrimestre) ;
      Long effectifBoursierTleA2F=geteffeBoursierF(idEcole,12,libelleAnnee,libelleTrimestre) ;
      effectifBoursierTleAF=effectifBoursierTleAF+effectifBoursierTleA1F+effectifBoursierTleA2F;

      effectifBoursierTleAG=geteffeBoursierG(idEcole,10,libelleAnnee,libelleTrimestre) ;
      Long effectifBoursierTleA1G=geteffeBoursierG(idEcole,11,libelleAnnee,libelleTrimestre) ;
      Long effectifBoursierTleA2G=geteffeBoursierG(idEcole,12,libelleAnnee,libelleTrimestre) ;
      effectifBoursierTleAG=effectifBoursierTleAG+effectifBoursierTleA1G+effectifBoursierTleA2G;

      m.setEffectifBoursierTleAG(effectifBoursierTleAG);
      m.setEffectifBoursierTleAF(effectifBoursierTleAF);

      effectifBoursierTleCF=geteffeBoursierF(idEcole,13,libelleAnnee,libelleTrimestre) ;
      effectifBoursierTleCG=geteffeBoursierG(idEcole,13,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursierTleCG(effectifBoursierTleCG);
      m.setEffectifBoursierTleCF(effectifBoursierTleCF);

      effectifBoursierTleDF=geteffeBoursierF(idEcole,14,libelleAnnee,libelleTrimestre) ;
      effectifBoursierTleDG=geteffeBoursierG(idEcole,14,libelleAnnee,libelleTrimestre) ;

      m.setEffectifBoursierTleDG(effectifBoursierTleDG);
      m.setEffectifBoursierTleDF(effectifBoursierTleDF);

      effectifBoursierTotalEtF=geteffeBoursierEtabliF(idEcole,libelleAnnee,libelleTrimestre) ;
      effectifBoursierTotalEtG=geteffeBoursierEtabliG(idEcole,libelleAnnee,libelleTrimestre) ;
      m.setEffectifBoursierTotalEtF(effectifBoursierTotalEtF);
      m.setEffectifBoursierTotalEtG(effectifBoursierTotalEtG);




        return  m ;
    }


  public  Long geteffeBoursierF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee and (o.boursier=:boursier1 or  o.boursier=:boursier2 )  group by  o.ordreNiveau having  o.ordreNiveau=:niveau order by o.ordreNiveau desc ")
          .setParameter("sexe","FEMININ")
          .setParameter("idEcole",idEcole)
          .setParameter("niveau",niveau)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("boursier1", "B")
          .setParameter("boursier2", "1/2B")
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }
  public  Long geteffeBoursierEtabliF(Long idEcole  ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee and (o.boursier=:boursier1 or  o.boursier=:boursier2 )")
          .setParameter("sexe","FEMININ")
          .setParameter("idEcole",idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("boursier1", "B")
          .setParameter("boursier2", "1/2B")
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }


  public  Long geteffeBoursierG(Long idEcole , int niveau ,String libelleAnnee , String libelleTrimestre){

    Long  effeG ;
    try {
      effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee and (o.boursier=:boursier1 or  o.boursier=:boursier2 )  group by o.ordreNiveau having  o.ordreNiveau=:niveau")
          .setParameter("sexe","MASCULIN")
          .setParameter("idEcole",idEcole)
          .setParameter("niveau",niveau)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("boursier1", "B")
          .setParameter("boursier2", "1/2B")
          .getSingleResult();
      return  effeG ;
    } catch (NoResultException e){
      return 0L ;
    }

  }
  public  Long geteffeBoursierEtabliG(Long idEcole ,String libelleAnnee , String libelleTrimestre){

    Long  effeG ;
    try {
      effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee and (o.boursier=:boursier1 or  o.boursier=:boursier2 )")
          .setParameter("sexe","MASCULIN")
          .setParameter("idEcole",idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("boursier1", "B")
          .setParameter("boursier2", "1/2B")
          .getSingleResult();
      return  effeG ;
    } catch (NoResultException e){
      return 0L ;
    }

  }

  public  Long geteffeEtablissementG(Long idEcole ,String libelleAnnee , String libelleTrimestre){

    Long  effeG ;
    try {
      effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee")
          .setParameter("sexe","MASCULIN")
          .setParameter("idEcole",idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
      return  effeG ;
    } catch (NoResultException e){
      return 0L ;
    }

  }
  public  Long getNombreClasse(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select distinct count(o.classeId) from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.ordreNiveau having  o.ordreNiveau=:niveau")

          .setParameter("idEcole",idEcole)
          .setParameter("niveau",niveau)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }

  public  Long getNombreClasseEtabliss(Long idEcole  ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select distinct count(o.classeId) from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.niveau")

          .setParameter("idEcole",idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }









}
