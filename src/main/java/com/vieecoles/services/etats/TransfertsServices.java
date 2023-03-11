package com.vieecoles.services.etats;

import com.vieecoles.dto.BoursierDto;
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
public class TransfertsServices {
    @Inject
    EntityManager em;

    public List<TransfertsDto>  transferts(Long idEcole){
        int LongTableau;

        List<NiveauDto> classeNiveauDtoList = new ArrayList<>() ;
        TypedQuery<NiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.NiveauDto(b.niveau) from Transferts b  where b.idEcole =:idEcole  " +
                "group by b.niveau ", NiveauDto.class);
        classeNiveauDtoList = q.setParameter("idEcole", idEcole)
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
            TypedQuery<TransfertsDto> q= em.createQuery("select new com.vieecoles.dto.TransfertsDto(o.nom,o.prenoms,o.classe,o.redoublant,o.dateNaissance,o.nredoublant,o.decision,o.etablissementOrigine,o.niveau) from Transferts o where  o.idEcole =:idEcole and  o.niveau=:niveau ", TransfertsDto.class);
            classeNiveauDtoList = q.setParameter("idEcole",idEcole)
                                  .setParameter("niveau",niveau)
                                  .getResultList() ;
            return classeNiveauDtoList ;
        } catch (NoResultException e){
            return null ;
        }

    }



}
