package com.vieecoles.services.etats;

import com.vieecoles.dto.BoursierDto;
import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.eleveNonAffecteParClasseDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EleveNonAffecteParClasseServices {
    @Inject
    EntityManager em;

    public List<eleveNonAffecteParClasseDto>  eleveNonAffecteParClasse(Long idEcole ,String libelleAnnee , String libelleTrimestre){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole and b.libellePeriode=:periode and b.anneeLibelle=:annee " +
                "group by b.libelleClasse ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<eleveNonAffecteParClasseDto> resultatsListElevesDto = new ArrayList<>();


        for (int i=0; i< LongTableau;i++) {
            List<eleveNonAffecteParClasseDto> resultatsListEleves= new ArrayList<>();

            resultatsListEleves = getListEleveNonAffectParClassDto(idEcole,classeNiveauDtoList.get(i).getNiveau(),libelleAnnee , libelleTrimestre);

            resultatsListElevesDto.addAll(resultatsListEleves);

        }

        return  resultatsListElevesDto ;
    }






    public List<eleveNonAffecteParClasseDto> getListEleveNonAffectParClassDto(Long idEcole , String classe ,String libelleAnnee , String libelleTrimestre){
        List<eleveNonAffecteParClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<eleveNonAffecteParClasseDto> q= em.createQuery("select new com.vieecoles.dto.eleveNonAffecteParClasseDto(o.libelleClasse,o.nomPrenomProfPrincipal,o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.nationalite,o.redoublant,o.affecte,o.numDecisionAffecte,o.moyGeneral,o.rang,o.appreciation,o.nomPrenomEducateur,o.ordreNiveau) from Bulletin o where  o.ecoleId=:idEcole and o.libelleClasse=:classe and o.affecte=:affecte and o.libellePeriode=:periode and o.anneeLibelle=:annee order by o.nom ,o.prenoms asc", eleveNonAffecteParClasseDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                                 .setParameter("classe",classe)
                                 .setParameter("affecte","NON_AFFECTE")
                                .setParameter("annee", libelleAnnee)
                                .setParameter("periode", libelleTrimestre)
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }


}
