package com.vieecoles.services.etats;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.ResultatsElevesAffecteDto;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class BoursiersServices {
    @Inject
    EntityManager em;

    public List<BoursierDto>  boursier(Long idEcole ,String libelleAnnee , String libelleTrimestre){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.niveau ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<BoursierDto> resultatsListElevesDto = new ArrayList<>();

        String matricule,nom,prenoms,DateNaissance,LieuNaissance,sexe;

        for (int i=0; i< LongTableau;i++) {
            List<BoursierDto> resultatsListEleves= new ArrayList<>();
            List<BoursierDto> resultatsListEleves2= new ArrayList<>();
            resultatsListEleves = getBoursierParNiveauBoursier(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            resultatsListEleves2 = getBoursierParNiveauDemiBoursier(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre) ;
            resultatsListElevesDto.addAll(resultatsListEleves);
            resultatsListElevesDto.addAll(resultatsListEleves2);
        }

        return  resultatsListElevesDto ;
    }




    public List<BoursierDto> getBoursierParNiveauBoursier(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        List<BoursierDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<BoursierDto> q= em.createQuery("select new com.vieecoles.dto.BoursierDto(o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.lieuNaissance,o.niveau,o.ordreNiveau) from Bulletin o where  o.ecoleId=:idEcole and  o.niveau=:niveau and o.boursier=:boursier and o.libellePeriode=:periode and o.anneeLibelle=:annee", BoursierDto.class);
            classeNiveauDtoList = q.setParameter("boursier","B")

                                  .setParameter("idEcole",idEcole)
                                  .setParameter("niveau",niveau)
                                    .setParameter("annee", libelleAnnee)
                                    .setParameter("periode", libelleTrimestre)
                                  .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public List<BoursierDto> getBoursierParNiveauDemiBoursier(Long idEcole , String niveau ,String libelleAnnee , String libelleTrimestre){
        List<BoursierDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<BoursierDto> q= em.createQuery("select new com.vieecoles.dto.BoursierDto(o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.lieuNaissance,o.niveau ,o.ordreNiveau) from Bulletin o where  o.ecoleId=:idEcole and  o.niveau=:niveau and o.boursier=:boursier and  o.libellePeriode=:periode and o.anneeLibelle=:annee", BoursierDto.class);
            classeNiveauDtoList = q.setParameter("boursier","1/2B")
                    .setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }


}
