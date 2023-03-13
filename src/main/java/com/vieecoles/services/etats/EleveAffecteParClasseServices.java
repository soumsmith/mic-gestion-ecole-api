package com.vieecoles.services.etats;

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
public class EleveAffecteParClasseServices {
    @Inject
    EntityManager em;

    public List<eleveNonAffecteParClasseDto>  eleveAffecteParClasse(Long idEcole){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole  " +
                "group by b.libelleClasse ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<eleveNonAffecteParClasseDto> resultatsListElevesDto = new ArrayList<>();


        for (int i=0; i< LongTableau;i++) {
            List<eleveNonAffecteParClasseDto> resultatsListEleves= new ArrayList<>();

            resultatsListEleves = getListEleveNonAffectParClassDto(idEcole,classeNiveauDtoList.get(i).getNiveau());

            resultatsListElevesDto.addAll(resultatsListEleves);

        }

        return  resultatsListElevesDto ;
    }






    public List<eleveNonAffecteParClasseDto> getListEleveNonAffectParClassDto(Long idEcole , String classe){
        List<eleveNonAffecteParClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<eleveNonAffecteParClasseDto> q= em.createQuery("select new com.vieecoles.dto.eleveNonAffecteParClasseDto(o.libelleClasse,o.nomPrenomProfPrincipal,o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.nationalite,o.redoublant,o.affecte,o.num_decision_affecte,o.moyGeneral,o.rang,o.appreciation,o.nom_educateur) from Bulletin o where  o.ecoleId=:idEcole and o.libelleClasse=:classe and o.affecte=:affecte", eleveNonAffecteParClasseDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                                 .setParameter("classe",classe)
                                 .setParameter("affecte","AFFECTE")
                    .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }


}
