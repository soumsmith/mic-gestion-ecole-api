package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.ApprocheGenreDto;
import com.vieecoles.dto.DateNaissNiveauDto;
import com.vieecoles.dto.StatistiqueTransfertDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class StatistiqueTransfertsServices {
    @Inject
    EntityManager em;

    public StatistiqueTransfertDto CalculRepartElevParAnnNaiss(Long idEcole , String libelleAnnee , String libelleTrimestre){

        List<DateNaissNiveauDto> dateNiveauDtoList = new ArrayList<>() ;
        TypedQuery<DateNaissNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.DateNaissNiveauDto(SUBSTRING(b.dateNaissance,1,4)) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by SUBSTRING(b.dateNaissance,1,4)  ", DateNaissNiveauDto.class);

        dateNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                             . getResultList() ;


      int LongTableau =dateNiveauDtoList.size() ;

      Long nbreClasse6;Long nbreClasse5;Long nbreClasse4;Long nbreClasse3;Long nbreClasse2A;Long nbreClasse2C;
      Long nbreClasse1A;Long nbreClasse1C;Long nbreClasse1D;Long nbreClasseTleA;Long nbreClasseTleC;
      Long nbreClasseTleD;Long nbreClasseTotalEt;

       Integer effectifClasse ;
      StatistiqueTransfertDto m = new StatistiqueTransfertDto();
        nbreClasse6= getNombreTransfertNiveau(idEcole,1,libelleAnnee,libelleTrimestre);
      nbreClasse5= getNombreTransfertNiveau(idEcole,2,libelleAnnee,libelleTrimestre);
      nbreClasse4= getNombreTransfertNiveau(idEcole,3,libelleAnnee,libelleTrimestre);
      nbreClasse3= getNombreTransfertNiveau(idEcole,4,libelleAnnee,libelleTrimestre);
      nbreClasse2A= getNombreTransfertNiveau(idEcole,5,libelleAnnee,libelleTrimestre);
      nbreClasse2C= getNombreTransfertNiveau(idEcole,6,libelleAnnee,libelleTrimestre);
      nbreClasse1A= getNombreTransfertNiveau(idEcole,7,libelleAnnee,libelleTrimestre);
      nbreClasse1C= getNombreTransfertNiveau(idEcole,8,libelleAnnee,libelleTrimestre);
      nbreClasse1D= getNombreTransfertNiveau(idEcole,9,libelleAnnee,libelleTrimestre);
      Long nbreClasseTleA1= getNombreTransfertNiveau(idEcole,11,libelleAnnee,libelleTrimestre);
      Long nbreClasseTleA2= getNombreTransfertNiveau(idEcole,12,libelleAnnee,libelleTrimestre);
      nbreClasseTleA= getNombreTransfertNiveau(idEcole,10,libelleAnnee,libelleTrimestre);
      nbreClasseTleA=nbreClasseTleA+nbreClasseTleA1+nbreClasseTleA2;
      nbreClasseTleC= getNombreTransfertNiveau(idEcole,13,libelleAnnee,libelleTrimestre);
      nbreClasseTleD= getNombreTransfertNiveau(idEcole,14,libelleAnnee,libelleTrimestre);
      nbreClasseTotalEt=getNombreTransfertEtablissement(idEcole,libelleAnnee,libelleTrimestre);
      m.setNbreClasse6(nbreClasse6);
      m.setNbreClasse5(nbreClasse5);
      m.setNbreClasse4(nbreClasse4);
      m.setNbreClasse3(nbreClasse3);
      m.setNbreClasse2A(nbreClasse2A);
      m.setNbreClasse2C(nbreClasse2C);
      m.setNbreClasse1A(nbreClasse1A);
      m.setNbreClasse1C(nbreClasse1C);
      m.setNbreClasse1D(nbreClasse1D);
      m.setNbreClasseTleA(nbreClasseTleA);
      m.setNbreClasseTleC(nbreClasseTleC);
      m.setNbreClasseTleD(nbreClasseTleD);

      m.setNbreClasseTotalEt(nbreClasseTotalEt);
        return  m ;
    }


  public  Long geteffeF(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee  group by  o.ordreNiveau having  o.ordreNiveau=:niveau")
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
  public  Long geteffeEtablisseF(Long idEcole ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee ")
          .setParameter("sexe","FEMININ")
          .setParameter("idEcole",idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }

  public  Long geteffeG(Long idEcole , int niveau ,String libelleAnnee , String libelleTrimestre){

    Long  effeG ;
    try {
      effeG= (Long) em.createQuery("select count(o.id) from Bulletin o where  o.sexe=:sexe and o.ecoleId=:idEcole   and o.libellePeriode=:periode and o.anneeLibelle=:annee group by o.ordreNiveau having  o.ordreNiveau=:niveau")
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
  public  Long getNombreTransfertNiveau(Long idEcole , int  niveau ,String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select distinct count(o.id) from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.transfert=:transfert group by  o.ordreNiveau having  o.ordreNiveau=:niveau")

          .setParameter("idEcole",idEcole)
          .setParameter("niveau",niveau)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("transfert", true)
          .getSingleResult();
    } catch (NoResultException e){
      return 0L ;
    }
  }

  public  Long getNombreTransfertEtablissement(Long idEcole , String libelleAnnee , String libelleTrimestre){
    Long effeF ;
    try {
      return  effeF = (Long) em.createQuery("select distinct count(o.id) from Bulletin o where  o.ecoleId=:idEcole  and o.libellePeriode=:periode and o.anneeLibelle=:annee and o.transfert=:transfert ")

          .setParameter("idEcole",idEcole)
          .setParameter("annee", libelleAnnee)
          .setParameter("periode", libelleTrimestre)
          .setParameter("transfert", true)
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
