package com.vieecoles.services.etats;

import com.vieecoles.dto.eleveAffecteParClasseDto;
import com.vieecoles.dto.eleveNonAffecteParClasseDto;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class EleveNonAffecteParClasseService {
    @Inject
    EntityManager em;
    public List<eleveNonAffecteParClasseDto> getIdentiteDto(Long idEcole , String affecte) {
        List<eleveNonAffecteParClasseDto> detailsBull = new ArrayList<>() ;

        TypedQuery<eleveNonAffecteParClasseDto> q = em.createQuery( "SELECT new com.vieecoles.dto.eleveAffecteParClasseDto(b.libelleClasse,b.nomPrenomProfPrincipal,b.matricule,b.nom ,b.prenoms,b.sexe,b.dateNaissance,b.nationalite,b.redoublant,b.affecte,b.numDecisionAffecte,b.moyGeneral,b.rang,b.appreciation,b.nom_educateur ) from Bulletin b where b.ecoleId=:idEcole and b.affecte=:affecte", eleveNonAffecteParClasseDto.class);
        detailsBull = q.setParameter("idEcole", idEcole)
                      .setParameter("affecte", affecte)
                     . getResultList() ;
        return detailsBull ;
    }
}
