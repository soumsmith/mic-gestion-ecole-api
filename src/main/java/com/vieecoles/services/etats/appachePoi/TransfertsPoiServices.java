package com.vieecoles.services.etats.appachePoi;

import com.vieecoles.dto.NiveauDto;
import com.vieecoles.dto.TransfertsDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TransfertsPoiServices {
    @Inject
    EntityManager em;

    public List<TransfertsDto>  transferts(Long idEcole ,String niveau ){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Transferts b  where b.idEcole =:idEcole and b.niveau=:niveau " +
                "group by b.niveau ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
                               .setParameter("niveau", niveau)
                               .getResultList() ;

        LongTableau= classeNiveauDtoList.size();
        List<TransfertsDto> resultatsListElevesDto = new ArrayList<>();


        for (int i=0; i< LongTableau;i++) {
            List<TransfertsDto> resultatsListEleves= new ArrayList<>();

            resultatsListEleves = getTransfertParNiveau(idEcole,classeNiveauDtoList.get(i).getNiveau()) ;

            resultatsListElevesDto.addAll(resultatsListEleves);

        }

        return  resultatsListElevesDto ;
    }




    public List<TransfertsDto> getTransfertParNiveau(Long idEcole , String niveau){
        List<TransfertsDto> classeNiveauDtoList = new ArrayList<>() ;
        try {
            TypedQuery<TransfertsDto> q= em.createQuery("select new com.vieecoles.dto.TransfertsDto(o.nom,o.prenoms,o.libelleClasse,o.redoublant,o.dateNaissance,o.redoublant,o.numDecisionAffecte,o.ecoleOrigine,o.niveau,o.matricule) from Bulletin o where  o.ecoleId =:idEcole and  o.niveau=:niveau and o.transfert=:transfert ", TransfertsDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                                  .setParameter("niveau",niveau)
                                   .setParameter("transfert",true)
                                  .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }



}
