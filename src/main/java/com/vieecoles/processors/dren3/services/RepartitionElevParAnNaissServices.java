package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.DateNaissNiveauDto;
import com.vieecoles.dto.EleveParAnneNaissNombreDto;
import com.vieecoles.dto.NbreAnneeDto;
import com.vieecoles.dto.RepartitionEleveParAnNaissDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class RepartitionElevParAnNaissServices {
    @Inject
    EntityManager em;

    public EleveParAnneNaissNombreDto CalculRepartElevParAnnNaiss(Long idEcole , String libelleAnnee , String libelleTrimestre){

        List<DateNaissNiveauDto> dateNiveauDtoList = new ArrayList<>() ;
        TypedQuery<DateNaissNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.DateNaissNiveauDto(SUBSTRING(b.dateNaissance,1,4)) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by SUBSTRING(b.dateNaissance,1,4)  ", DateNaissNiveauDto.class);

        dateNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                             . getResultList() ;

      int LongTableau =dateNiveauDtoList.size() ;

        Long nbre01G=0L;Long nbre01F=0L;Long nbre98G=0L;Long nbre98F=0L; Long nbre99G=0L;Long nbre99F=0L;Long nbre2001G=0L;
        Long nbre2001F=0L;Long nbre2002G=0L;
        Long nbre2002F=0L;Long nbre2003G=0L;Long nbre2003F=0L; Long nbre2004G=0L;Long nbre2004F=0L;Long nbre2005G=0L;
        Long nbre2005F=0L;Long nbre2006G=0L;Long nbre2006F=0L;
        Long nbre2007G=0L;Long nbre2007F=0L;Long nbre2008G=0L;Long nbre2008F=0L;Long nbre2009G=0L;
        Long nbre2009F=0L;Long nbre2010G=0L;Long nbre2010F=0L;
        Long nbre2011G=0L;Long nbre2011F=0L;Long nbre2012G=0L;Long nbre2012F=0L;Long nbre2013G=0L;Long nbre2013F=0L;
        Long nbre2014G=0L;Long nbre2014F=0L;
        Long nbre2015G=0L;Long nbre2015F=0L;Long nbre2016G=0L;Long nbre2016F=0L;Long nbre2017G=0L;
        Long nbre2017F=0L;Long nbre2018G=0L;Long nbre2018F=0L;
        Long nbre2019G=0L; Long nbre2019F=0L;Long nbre2020G=0L;Long nbre2020F=0L;Long nbre2021G=0L;
        Long nbre2021F=0L;Long nbre2000G=0L;Long nbre2000F=0L;
        Long totalG=0L;Long totalF=0L;

       Integer effectifClasse ;
        EleveParAnneNaissNombreDto m = new EleveParAnneNaissNombreDto();
        nbre01F=getRepartiParAnnee(idEcole,"1901","FEMININ" , libelleTrimestre);
        nbre01G=getRepartiParAnnee(idEcole,"1901","MASCULIN" , libelleTrimestre);
        m.setNbre01F(nbre01F);
        m.setNbre01G(nbre01G);
        nbre98F=getRepartiParAnnee(idEcole,"1998","FEMININ" , libelleTrimestre);
        nbre98G=getRepartiParAnnee(idEcole,"1998","MASCULIN" , libelleTrimestre);
        m.setNbre98F(nbre98F);
        m.setNbre98G(nbre98G);
        nbre99F=getRepartiParAnnee(idEcole,"1999","FEMININ" , libelleTrimestre);
        nbre99G=getRepartiParAnnee(idEcole,"1999","MASCULIN" , libelleTrimestre);
        m.setNbre99F(nbre99F);
        m.setNbre99G(nbre99G);
        nbre2000F=getRepartiParAnnee(idEcole,"2000","FEMININ" , libelleTrimestre);
        nbre2000G=getRepartiParAnnee(idEcole,"2000","MASCULIN" , libelleTrimestre);
         m.setNbre2000F(nbre2000F);
         m.setNbre2000G(nbre2000G);
        nbre2001F=getRepartiParAnnee(idEcole,"2001","FEMININ" , libelleTrimestre);
        nbre2001G=getRepartiParAnnee(idEcole,"2001","MASCULIN" , libelleTrimestre);
        m.setNbre2001F(nbre2001F);
        m.setNbre2001G(nbre2001G);
        nbre2002F=getRepartiParAnnee(idEcole,"2002","FEMININ" , libelleTrimestre);
        nbre2002G=getRepartiParAnnee(idEcole,"2002","MASCULIN" , libelleTrimestre);
        m.setNbre2001F(nbre2002F);
        m.setNbre2001G(nbre2002G);

        nbre2003F=getRepartiParAnnee(idEcole,"2003","FEMININ" , libelleTrimestre);
        nbre2003G=getRepartiParAnnee(idEcole,"2003","MASCULIN" , libelleTrimestre);
        m.setNbre2003F(nbre2003F);
        m.setNbre2003G(nbre2003G);

        nbre2004F=getRepartiParAnnee(idEcole,"2004","FEMININ" , libelleTrimestre);
        nbre2004G=getRepartiParAnnee(idEcole,"2004","MASCULIN" , libelleTrimestre);
        m.setNbre2004F(nbre2004F);
        m.setNbre2004G(nbre2004G);

        nbre2005F=getRepartiParAnnee(idEcole,"2005","FEMININ" , libelleTrimestre);
        nbre2005G=getRepartiParAnnee(idEcole,"2005","MASCULIN" , libelleTrimestre);
        m.setNbre2005F(nbre2005F);
        m.setNbre2005G(nbre2005G);

        nbre2006F=getRepartiParAnnee(idEcole,"2006","FEMININ" , libelleTrimestre);
        nbre2006G=getRepartiParAnnee(idEcole,"2006","MASCULIN" , libelleTrimestre);
        m.setNbre2006F(nbre2006F);
        m.setNbre2006G(nbre2006G);
        nbre2007F=getRepartiParAnnee(idEcole,"2007","FEMININ" , libelleTrimestre);
        nbre2007G=getRepartiParAnnee(idEcole,"2007","MASCULIN" , libelleTrimestre);
        m.setNbre2007F(nbre2007F);
        m.setNbre2007G(nbre2007G);
        nbre2008F=getRepartiParAnnee(idEcole,"2008","FEMININ" , libelleTrimestre);
        nbre2008G=getRepartiParAnnee(idEcole,"2008","MASCULIN" , libelleTrimestre);
        m.setNbre2008F(nbre2008F);
        m.setNbre2008G(nbre2008G);
        nbre2009F=getRepartiParAnnee(idEcole,"2009","FEMININ" , libelleTrimestre);
        nbre2009G=getRepartiParAnnee(idEcole,"2009","MASCULIN" , libelleTrimestre);
        m.setNbre2009F(nbre2009F);
        m.setNbre2009G(nbre2009G);

        nbre2010F=getRepartiParAnnee(idEcole,"2010","FEMININ" , libelleTrimestre);
        nbre2010G=getRepartiParAnnee(idEcole,"2010","MASCULIN" , libelleTrimestre);
        m.setNbre2010F(nbre2010F);
        m.setNbre2010G(nbre2010G);
        System.out.println("Annee Masculin:"+nbre2010G);
      System.out.println("Annee Feminin:"+nbre2010F);

        nbre2011F=getRepartiParAnnee(idEcole,"2011","FEMININ" , libelleTrimestre);
        nbre2011G=getRepartiParAnnee(idEcole,"2011","MASCULIN" , libelleTrimestre);
        m.setNbre2011F(nbre2011F);
        m.setNbre2011G(nbre2011G);

        nbre2012F=getRepartiParAnnee(idEcole,"2012","FEMININ" , libelleTrimestre);
        nbre2012G=getRepartiParAnnee(idEcole,"2012","MASCULIN" , libelleTrimestre);
        m.setNbre2012F(nbre2012F);
        m.setNbre2012G(nbre2012G);

        nbre2013F=getRepartiParAnnee(idEcole,"2013","FEMININ" , libelleTrimestre);
        nbre2013G=getRepartiParAnnee(idEcole,"2013","MASCULIN" , libelleTrimestre);
        m.setNbre2013F(nbre2013F);
        m.setNbre2013G(nbre2013G);

        nbre2014F=getRepartiParAnnee(idEcole,"2014","FEMININ" , libelleTrimestre);
        nbre2014G=getRepartiParAnnee(idEcole,"2014","MASCULIN" , libelleTrimestre);
        m.setNbre2014F(nbre2014F);
        m.setNbre2014G(nbre2014G);

        nbre2015F=getRepartiParAnnee(idEcole,"2015","FEMININ" , libelleTrimestre);
        nbre2015G=getRepartiParAnnee(idEcole,"2015","MASCULIN" , libelleTrimestre);
        m.setNbre2015F(nbre2015F);
        m.setNbre2015G(nbre2015G);

        nbre2016F=getRepartiParAnnee(idEcole,"2016","FEMININ" , libelleTrimestre);
        nbre2016G=getRepartiParAnnee(idEcole,"2016","MASCULIN" , libelleTrimestre);
        m.setNbre2016F(nbre2016F);
        m.setNbre2016G(nbre2016G);

        nbre2017F=getRepartiParAnnee(idEcole,"2017","FEMININ" , libelleTrimestre);
        nbre2017G=getRepartiParAnnee(idEcole,"2017","MASCULIN" , libelleTrimestre);
        m.setNbre2017F(nbre2017F);
        m.setNbre2017G(nbre2017G);

        nbre2018F=getRepartiParAnnee(idEcole,"2018","FEMININ" , libelleTrimestre);
        nbre2018G=getRepartiParAnnee(idEcole,"2018","MASCULIN" , libelleTrimestre);
        m.setNbre2018F(nbre2018F);
        m.setNbre2018G(nbre2018G);

        nbre2019F=getRepartiParAnnee(idEcole,"2019","FEMININ" , libelleTrimestre);
        nbre2019G=getRepartiParAnnee(idEcole,"2019","MASCULIN" , libelleTrimestre);
        m.setNbre2019F(nbre2019F);
        m.setNbre2019G(nbre2019G);

        nbre2020F=getRepartiParAnnee(idEcole,"2020","FEMININ" , libelleTrimestre);
        nbre2020G=getRepartiParAnnee(idEcole,"2020","MASCULIN" , libelleTrimestre);
        m.setNbre2020F(nbre2020F);
        m.setNbre2020G(nbre2020G);

        nbre2021F=getRepartiParAnnee(idEcole,"2021","FEMININ" , libelleTrimestre);
        nbre2021G=getRepartiParAnnee(idEcole,"2021","MASCULIN" , libelleTrimestre);
        m.setNbre2021F(nbre2021F);
        m.setNbre2021G(nbre2021G);
        totalF=getRepartiParAnneeTotal(idEcole,"FEMININ" , libelleTrimestre);
         totalG=getRepartiParAnneeTotal(idEcole,"MASCULIN" , libelleTrimestre);
         m.setTotalF(totalF);
         m.setTotalG(totalG);

        return  m ;
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

    public  Long getRepartiParAnnee(Long idEcole ,String annee, String sexe , String libelleTrimestre){
        Long repartParAnneParNiv ;
        try {
          Long eff ;
            eff =(Long) em.createQuery( "SELECT count(b.id)  from Bulletin as b  where b.sexe=:sexe and b.ecoleId=:idEcole and b.libellePeriode=:periode  and SUBSTRING(b.dateNaissance,1,4) =:annee group by SUBSTRING(b.dateNaissance,1,4)  "
                ).setParameter("sexe",sexe)
                .setParameter("idEcole",idEcole)
                .setParameter("annee",annee)
                .setParameter("periode", libelleTrimestre)
                .getSingleResult();
            return (long) eff;
        } catch (NoResultException e){
            return 0L ;
        }

    }

  public  Long getRepartiParAnneeTotal(Long idEcole , String sexe , String libelleTrimestre){
    Long repartParAnneParNiv ;
    try {
     Long eff;
      eff =(Long) em.createQuery( "SELECT count(b.id)  from Bulletin as b  where b.sexe=:sexe and b.ecoleId=:idEcole and b.libellePeriode=:periode "
         ).setParameter("sexe",sexe)
          .setParameter("idEcole",idEcole)
          .setParameter("periode", libelleTrimestre)
          .getSingleResult();

      return   eff ;
    } catch (NoResultException e){
      return 0L ;
    }

  }





}
