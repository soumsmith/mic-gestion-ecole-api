package com.vieecoles.processors.dren3.services;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.TransfertsDto;
import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class TransfertsDren3Services {
    @Inject
    EntityManager em;

    public List<TransfertsDto>  transferts(Long idEcole ){
        int LongTableau;

        List<TransfertsDto> resultatsListEleves= new ArrayList<>();

        resultatsListEleves = getTransfertParNiveau(idEcole) ;

        return  resultatsListEleves ;
    }




    public List<TransfertsDto> getTransfertParNiveau(Long idEcole ){
        List<TransfertsDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<TransfertsDto> q= em.createQuery("select new com.vieecoles.dto.TransfertsDto(o.nom,o.prenoms,o.libelleClasse,o.redoublant,o.dateNaissance,o.redoublant,o.numDecisionAffecte,o.ecoleOrigine,o.niveau,o.matricule) from Bulletin o where  o.ecoleId =:idEcole  and o.transfert=:transfert ", TransfertsDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                                   .setParameter("transfert",true)
                                  .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }



}
