package com.vieecoles.services.etats;

import com.vieecoles.dto.IdentiteEtatDto;
import com.vieecoles.projection.BulletinSelectDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
@ApplicationScoped
public class IdentiteEtatService {
    @Inject
    EntityManager em;

    public List<IdentiteEtatDto> getIdentiteDto(Long idEcole) {
        List<IdentiteEtatDto> detailsBull = new ArrayList<>() ;
        TypedQuery<IdentiteEtatDto> q = em.createQuery( "SELECT new com.vieecoles.dto.IdentiteEtatDto(e.etab_denomination,e.etab_num_decision_ouverture,e.etab_code_etablissement,e.etab_situation_geographique,e.etab_adresse,e.etab_telephone,e.etab_fax,e.etab_email,f.fon_nomPrenoms,f.fon_adresse,f.fon_telephone,f.fon_cellulaire,f.fon_email,d.direct_nom_prenom,d.direct_adresse,d.direct_telephone,d.direct_cellulaire,d.emailDirecteurEtude,d.autoriSatDirecteurEtude ) from r_directeur_des_etudes d , r_info_etablissement e , r_info_fondateur f where d.direct_id_ecole =:idEcole and e.etab_id_ecole=:idEcole and f.fon_id_ecole =:idEcole ", IdentiteEtatDto.class);
        detailsBull = q.setParameter("idEcole", idEcole). getResultList() ;
        return detailsBull ;
    }





}
