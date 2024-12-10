package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.ClasseNiveauDto;
import com.vieecoles.dto.MajorParClasseNiveauDren3Dto;
import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.NiveauOrderDto;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@ApplicationScoped
public class MajorParClasseDren3Services {
    @Inject
    EntityManager em;

    public List<MajorParClasseNiveauDren3Dto> MajorParNiveauClasse(Long idEcole ,String libelleAnnee , String libelleTrimestre,String niveau){


        List<MajorParClasseNiveauDren3Dto> majorExeco = new ArrayList<>();
        majorExeco = getListMajorParClasseNiveau(idEcole,niveau,libelleAnnee , libelleTrimestre) ;

        return  majorExeco ;
    }


    public List<MajorParClasseNiveauDren3Dto>  getListMajorParClasseNiveau(Long idEcole , String niveau,String libelleAnnee , String libelleTrimestre ){
        List<MajorParClasseNiveauDren3Dto> classeNiveauDtoList = new ArrayList<>();
        try {
            TypedQuery  q= em.createQuery("select new com.vieecoles.dto.MajorParClasseNiveauDren3Dto(o.niveau,o.libelleClasse,o.matricule,o.nom,o.prenoms,SUBSTRING(o.dateNaissance,1,4) ,o.sexe,o.appreciation,o.redoublant,o.moyGeneral,o.lv2,o.ordreNiveau,o.nationalite,o.affecte,o.numDecisionAffecte,o.rang,o.appreciation) from Bulletin o where  o.ecoleId =:idEcole and  o.niveau=:niveau   and o.libellePeriode=:periode and o.anneeLibelle=:annee order by o.moyGeneral desc ", MajorParClasseNiveauDren3Dto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .setMaxResults(3)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }

    public Double getMajorDto(Long idEcole , String niveau,String classe ,String libelleAnnee , String libelleTrimestre){
        Double classeNiveauDtoList ;
        try {
            TypedQuery<Double> q= (TypedQuery<Double>) em.createQuery("select MAX(o.moyGeneral) from Bulletin o where  o.ecoleId =:idEcole and  o.niveau=:niveau and o.libelleClasse=:libelleClasse and o.libellePeriode=:periode and o.anneeLibelle=:annee");
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                    .setParameter("niveau",niveau)
                    .setParameter("libelleClasse",classe)
                    .setParameter("annee", libelleAnnee)
                    .setParameter("periode", libelleTrimestre)
                    .getSingleResult() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return 0D ;
        }

    }



}
