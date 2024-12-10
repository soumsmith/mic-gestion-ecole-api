package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.ApprocheGenreDto;
import com.vieecoles.dto.DateNaissNiveauDto;
import com.vieecoles.dto.EleveParAnneNaissNombreDto;
import com.vieecoles.dto.NbreAnneeDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class ApprocheParGenreServices {
    @Inject
    EntityManager em;

    public ApprocheGenreDto CalculRepartElevParAnnNaiss(Long idEcole , String libelleAnnee , String libelleTrimestre){

        List<DateNaissNiveauDto> dateNiveauDtoList = new ArrayList<>() ;
        TypedQuery<DateNaissNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.DateNaissNiveauDto(SUBSTRING(b.dateNaissance,1,4)) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by SUBSTRING(b.dateNaissance,1,4)  ", DateNaissNiveauDto.class);

        dateNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                             . getResultList() ;


      int LongTableau =dateNiveauDtoList.size() ;

      Long nbreClasse6; Long nbreClasse5; Long nbreClasse4;Long nbreClasse3; Long nbreClasse2A; Long nbreClasse2C; Long nbreClasse1A; Long nbreClasse1C;
      Long nbreClasse1D;Long nbreClasseTleA; Long nbreClasseTleC;Long nbreClasseTleD; Long nbreClasseTotalEt;Long effectifParClasse6G;Long effectifParClasse5G;
      Long effectifParClasse4G; Long effectifParClasse3G;Long effectifParClasse2AG;Long effectifParClasse2CG;Long effectifParClasse1AG;Long effectifParClasse1CG;
      Long effectifParClasse1DG; Long effectifParClasseTleAG;Long effectifParClasseTleCG; Long effectifParClasseTleDG;Long effectifParClasseTotalEtG;
      Long effectifParClasse6F; Long effectifParClasse5F;Long effectifParClasse4F;Long effectifParClasse3F; Long effectifParClasse2AF;Long effectifParClasse2CF;
      Long effectifParClasse1AF;Long effectifParClasse1CF;Long effectifParClasse1DF; Long effectifParClasseTleAF;Long effectifParClasseTleCF;
      Long effectifParClasseTleDF;Long effectifParClasseTotalEtF;

       Integer effectifClasse ;
      ApprocheGenreDto m = new ApprocheGenreDto();
        nbreClasse6= getNombreClasse(idEcole,1,libelleAnnee,libelleTrimestre);
      nbreClasse5= getNombreClasse(idEcole,2,libelleAnnee,libelleTrimestre);
      nbreClasse4= getNombreClasse(idEcole,3,libelleAnnee,libelleTrimestre);
      nbreClasse3= getNombreClasse(idEcole,4,libelleAnnee,libelleTrimestre);
      nbreClasse2A= getNombreClasse(idEcole,5,libelleAnnee,libelleTrimestre);
      nbreClasse2C= getNombreClasse(idEcole,6,libelleAnnee,libelleTrimestre);
      nbreClasse1A= getNombreClasse(idEcole,7,libelleAnnee,libelleTrimestre);
      nbreClasse1C= getNombreClasse(idEcole,8,libelleAnnee,libelleTrimestre);
      nbreClasse1D= getNombreClasse(idEcole,9,libelleAnnee,libelleTrimestre);
      Long nbreClasseTleA1= getNombreClasse(idEcole,11,libelleAnnee,libelleTrimestre);
      Long nbreClasseTleA2= getNombreClasse(idEcole,12,libelleAnnee,libelleTrimestre);
      nbreClasseTleA= getNombreClasse(idEcole,10,libelleAnnee,libelleTrimestre);
      nbreClasseTleA=nbreClasseTleA+nbreClasseTleA1+nbreClasseTleA2;
      nbreClasseTleC= getNombreClasse(idEcole,13,libelleAnnee,libelleTrimestre);
      nbreClasseTleD= getNombreClasse(idEcole,14,libelleAnnee,libelleTrimestre);
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
      effectifParClasse6F= geteffeF(idEcole,1,libelleAnnee,libelleTrimestre);
      effectifParClasse6G= geteffeG(idEcole,1,libelleAnnee,libelleTrimestre);
     m.setEffectifParClasse6F(effectifParClasse6F);
      m.setEffectifParClasse6G(effectifParClasse6G);
      effectifParClasse5F= geteffeF(idEcole,2,libelleAnnee,libelleTrimestre);
      effectifParClasse5G= geteffeG(idEcole,2,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasse5F(effectifParClasse5F);
      m.setEffectifParClasse5G(effectifParClasse5G);
      effectifParClasse4F= geteffeF(idEcole,3,libelleAnnee,libelleTrimestre);
      effectifParClasse4G= geteffeG(idEcole,3,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasse4F(effectifParClasse4F);
      m.setEffectifParClasse4G(effectifParClasse4G);
      effectifParClasse3F= geteffeF(idEcole,4,libelleAnnee,libelleTrimestre);
      effectifParClasse3G= geteffeG(idEcole,4,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasse3F(effectifParClasse3F);
      m.setEffectifParClasse3G(effectifParClasse3G);

      effectifParClasse2AF= geteffeF(idEcole,5,libelleAnnee,libelleTrimestre);
      effectifParClasse2AG= geteffeG(idEcole,5,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasse2AF(effectifParClasse2AF);
      m.setEffectifParClasse2AG(effectifParClasse2AG);
      effectifParClasse2CF= geteffeF(idEcole,6,libelleAnnee,libelleTrimestre);
      effectifParClasse2CG= geteffeG(idEcole,6,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasse2CF(effectifParClasse2CF);
      m.setEffectifParClasse2CG(effectifParClasse2CG);
      effectifParClasse1AF= geteffeF(idEcole,7,libelleAnnee,libelleTrimestre);
      effectifParClasse1AG= geteffeG(idEcole,7,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasse1AF(effectifParClasse1AF);
      m.setEffectifParClasse1AG(effectifParClasse1AG);

      effectifParClasse1CF= geteffeF(idEcole,8,libelleAnnee,libelleTrimestre);
      effectifParClasse1CG= geteffeG(idEcole,8,libelleAnnee,libelleTrimestre);

      m.setEffectifParClasse1CF(effectifParClasse1CF);
      m.setEffectifParClasse1CG(effectifParClasse1CG);

      effectifParClasse1DF= geteffeF(idEcole,9,libelleAnnee,libelleTrimestre);
      effectifParClasse1DG= geteffeG(idEcole,9,libelleAnnee,libelleTrimestre);

      m.setEffectifParClasse1DF(effectifParClasse1DF);
      m.setEffectifParClasse1DG(effectifParClasse1DG);
      effectifParClasseTleAF= geteffeF(idEcole,10,libelleAnnee,libelleTrimestre);
      Long effectifParClasseTleA1F= geteffeF(idEcole,11,libelleAnnee,libelleTrimestre);
      Long effectifParClasseTleA2F= geteffeF(idEcole,12,libelleAnnee,libelleTrimestre);
      effectifParClasseTleAF=effectifParClasseTleAF+effectifParClasseTleA1F+effectifParClasseTleA2F;

      effectifParClasseTleAG= geteffeG(idEcole,10,libelleAnnee,libelleTrimestre);
      Long effectifParClasseTleA1G= geteffeG(idEcole,11,libelleAnnee,libelleTrimestre);
      Long effectifParClasseTleA2G= geteffeG(idEcole,12,libelleAnnee,libelleTrimestre);
      effectifParClasseTleAG=effectifParClasseTleAG+effectifParClasseTleA1G+effectifParClasseTleA2G;
      m.setEffectifParClasseTleAF(effectifParClasseTleAF);
      m.setEffectifParClasseTleAG(effectifParClasseTleAG);

      effectifParClasseTleCF= geteffeF(idEcole,13,libelleAnnee,libelleTrimestre);
      effectifParClasseTleCG= geteffeG(idEcole,13,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasseTleCF(effectifParClasseTleCF);
      m.setEffectifParClasseTleCG(effectifParClasseTleCG);

      effectifParClasseTleDF= geteffeF(idEcole,14,libelleAnnee,libelleTrimestre);
      effectifParClasseTleDG= geteffeG(idEcole,14,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasseTleDF(effectifParClasseTleDF);
      m.setEffectifParClasseTleDG(effectifParClasseTleDG);
      nbreClasseTotalEt= getNombreClasseEtabliss(idEcole,libelleAnnee,libelleTrimestre);
      effectifParClasseTotalEtG=geteffeEtablissementG(idEcole,libelleAnnee,libelleTrimestre);
      effectifParClasseTotalEtF=geteffeEtablisseF(idEcole,libelleAnnee,libelleTrimestre);
      m.setEffectifParClasseTotalEtF(effectifParClasseTotalEtF);
      m.setEffectifParClasseTotalEtG(effectifParClasseTotalEtG);
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
