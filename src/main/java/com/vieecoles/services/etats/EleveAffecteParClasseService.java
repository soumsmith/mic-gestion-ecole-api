package com.vieecoles.services.etats;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.eleveAffecteParClasseDto;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class EleveAffecteParClasseService {
    @Inject
    EntityManager em;
    public List<eleveAffecteParClasseDto> getIdentiteDto(Long idEcole ,String affecte) {
        List<eleveAffecteParClasseDto> detailsBull = new ArrayList<>() ;

        TypedQuery<eleveAffecteParClasseDto> q = em.createQuery( "SELECT new com.vieecoles.dto.eleveAffecteParClasseDto(b.libelleClasse,b.nomPrenomProfPrincipal,b.matricule,b.nom ,b.prenoms,b.sexe,b.dateNaissance,b.nationalite,b.redoublant,b.affecte,b.numDecisionAffecte,b.moyGeneral,b.rang,b.appreciation,b.nom_educateur ) from Bulletin b where b.ecoleId=:idEcole and b.affecte=:affecte", eleveAffecteParClasseDto.class);
        detailsBull = q.setParameter("idEcole", idEcole)
                      .setParameter("affecte", affecte)
                     . getResultList() ;
        return detailsBull ;
    }
}
