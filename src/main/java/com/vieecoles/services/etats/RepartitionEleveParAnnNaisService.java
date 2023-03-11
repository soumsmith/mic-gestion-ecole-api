package com.vieecoles.services.etats;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.dto.RepartitionEleveParAnneNaiss;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class RepartitionEleveParAnnNaisService {
    @Inject
    EntityManager em;

    public List<RepartitionEleveParAnneNaiss> getIdentiteDto(Long idEcole) {
        List<RepartitionEleveParAnneNaiss> detailsBull = new ArrayList<>() ;
        TypedQuery<RepartitionEleveParAnneNaiss> q = em.createQuery( "SELECT new com.vieecoles.dto.RepartitionEleveParAnneNaiss(b.dateNaissance,b.sexe,b.niveau,cast(count(b.id) as int)  ) from Bulletin b  where b.ecoleId=:idEcole  group by b.dateNaissance ,b.niveau", RepartitionEleveParAnneNaiss.class);
        detailsBull = q.setParameter("idEcole", idEcole)
                      .getResultList() ;
        return detailsBull ;
    }





}
