package com.vieecoles.services.etats;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EleveAffecteParClasseAnnulleServices {
    @Inject
    EntityManager em;

    public List<eleveAffecteParClasseDto>  eleveAffecteParClasse(Long idEcole ,String libelleAnnee , String libelleTrimestre){
        int LongTableau;
        Integer orderNiv ;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee  " +
                "group by b.libelleClasse ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .setParameter("annee", libelleAnnee)
                .setParameter("periode", libelleTrimestre)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<eleveAffecteParClasseDto> resultatsListElevesDto = new ArrayList<>();


        for (int i=0; i< LongTableau;i++) {
            List<eleveAffecteParClasseDto> resultatsListEleves= new ArrayList<>();

            resultatsListEleves = getListEleveNonAffectParClassDto(idEcole,classeNiveauDtoList.get(i).getNiveau() ,libelleAnnee , libelleTrimestre);

            resultatsListElevesDto.addAll(resultatsListEleves);

        }

        return  resultatsListElevesDto ;
    }



    public  Integer getOrderNiveau(String niveau){
        Integer ordNiveau;
        try {
            ordNiveau = (Integer) em.createQuery("select distinct o.ordreNiveau from Bulletin o where  o.niveau=:niveau ")
                    .setParameter("niveau",niveau)
                    .getSingleResult();
        } catch (NoResultException e){
            return 0 ;
        }
        return  ordNiveau ;
    }


    public List<eleveAffecteParClasseDto> getListEleveNonAffectParClassDto(Long idEcole , String classe,String libelleAnnee , String libelleTrimestre){
        List<eleveAffecteParClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<eleveAffecteParClasseDto> q= em.createQuery("select new com.vieecoles.dto.eleveAffecteParClasseDto(o.libelleClasse,o.nomPrenomProfPrincipal,o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.nationalite,o.redoublant,o.affecte,o.numDecisionAffecte,o.moyAn,o.rang,o.appreciation,o.nomPrenomEducateur,o.ordreNiveau) from Bulletin o where  o.ecoleId=:idEcole and o.libelleClasse=:classe and o.affecte=:affecte and o.libellePeriode=:periode and o.anneeLibelle=:annee", eleveAffecteParClasseDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                                 .setParameter("classe",classe)
                                 .setParameter("affecte","AFFECTE")
                                    .setParameter("annee", libelleAnnee)
                                    .setParameter("periode", libelleTrimestre)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }


}
