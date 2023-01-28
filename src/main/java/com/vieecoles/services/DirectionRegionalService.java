package com.vieecoles.services;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

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
