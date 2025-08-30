package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.EffectifiConseilClasseDto;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class PyaramideClassePoiServices {
    @Inject
    EntityManager em;

    public int  getNombreClasse(Long idEcole , String libelleAnnee , String libelleTrimestre, int order ){

        List<ClasseNiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<ClasseNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.ClasseNiveauDto(b.libelleClasse ,b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ordreNiveau=:order" +
                " group by b.libelleClasse ,b.niveau order by b.libelleClasse,b.niveau", ClasseNiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                          //.setParameter("affecte", "AFFECTE")
                            .setParameter("periode", libelleTrimestre)
                              .setParameter("annee", libelleAnnee)
                              .setParameter("order", order)
                           . getResultList() ;


      return classeNiveauDtoList.size();
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






}
