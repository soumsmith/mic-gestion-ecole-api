package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

import com.vieecoles.entities.Direction_regionale;
import com.vieecoles.entities.objet;
import com.vieecoles.entities.operations.ville;

import java.net.URI;
import java.util.List;


@ApplicationScoped
public class DirectionRegionalService implements PanacheRepositoryBase<Direction_regionale,Long > {
    @Inject
    EntityManager em;

    public  List<Direction_regionale> findAllDirection_regionales(){
        return  em.createQuery("select o from Direction_regionale o join fetch o.pays").getResultList() ;

    }
    public  List<Direction_regionale> findDirectByIdPays(Long idtyp){
        return    em.createQuery("select o from Direction_regionale o join fetch o.pays h where h.paysid =:typeObj")
                .setParameter("typeObj",idtyp)
                .getResultList();

    }



}
