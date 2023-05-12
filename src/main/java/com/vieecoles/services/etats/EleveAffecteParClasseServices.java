package com.vieecoles.services.etats;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;
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

    public List<eleveAffecteParClasseDto>  eleveAffecteParClasse(Long idEcole){
        int LongTableau;
        Integer orderNiv ;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.libelleClasse) from Bulletin b  where b.ecoleId =:idEcole  " +
                "group by b.libelleClasse ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<eleveAffecteParClasseDto> resultatsListElevesDto = new ArrayList<>();


        for (int i=0; i< LongTableau;i++) {
            List<eleveAffecteParClasseDto> resultatsListEleves= new ArrayList<>();

            resultatsListEleves = getListEleveNonAffectParClassDto(idEcole,classeNiveauDtoList.get(i).getNiveau());

            resultatsListElevesDto.addAll(resultatsListEleves);

        }

        return  resultatsListElevesDto ;
    }



    public  Integer getOrderNiveau(String niveau){
        Integer ordNiveau;
        try {
            ordNiveau = (Integer) em.createQuery("select distinct cast(o.ordre_niveau as integer ) from Bulletin o where  o.niveau=:niveau ")
                    .setParameter("niveau",niveau)
                    .getSingleResult();
        } catch (NoResultException e){
            return 0 ;
        }
        return  ordNiveau ;
    }


    public List<eleveAffecteParClasseDto> getListEleveNonAffectParClassDto(Long idEcole , String classe){
        List<eleveAffecteParClasseDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<eleveAffecteParClasseDto> q= em.createQuery("select new com.vieecoles.dto.eleveAffecteParClasseDto(o.libelleClasse,o.nomPrenomProfPrincipal,o.matricule,o.nom,o.prenoms,o.sexe,o.dateNaissance,o.nationalite,o.redoublant,o.affecte,o.numDecisionAffecte,o.moyGeneral,o.rang,o.appreciation,o.nomPrenomEducateur,o.ordre_niveau) from Bulletin o where  o.ecoleId=:idEcole and o.libelleClasse=:classe and o.affecte=:affecte", eleveAffecteParClasseDto.class);
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
