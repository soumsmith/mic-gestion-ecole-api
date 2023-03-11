package com.vieecoles.services.etats;

import com.vieecoles.dto.MajorParClasseNiveauDto;
import com.vieecoles.dto.eleveNonAffecteParClasseDto;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MajorParClasseNiveauService {
    @Inject
    EntityManager em;


    public List<MajorParClasseNiveauDto> getMajorClasse(Long idEcole) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String date = "16/08/2016";

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<MajorParClasseNiveauDto> detailsBull = new ArrayList<>() ;

        TypedQuery<MajorParClasseNiveauDto> q = em.createQuery( "SELECT new com.vieecoles.dto.MajorParClasseNiveauDto(b.niveau,b.libelleClasse,b.matricule,b.nom,b.prenoms,b.dateNaissance,b.sexe,b.nature,b.redoublant,MAX(b.moyGeneral) ,b.lv2 ) from Bulletin b where b.ecoleId=:idEcole group by b.libelleClasse ,b.niveau ", MajorParClasseNiveauDto.class);
        detailsBull = q.setParameter("idEcole", idEcole)
                       . getResultList() ;
        return detailsBull ;
    }



}
