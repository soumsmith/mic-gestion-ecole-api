package com.vieecoles.services.operations;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import com.vieecoles.entities.operations.ville;

import java.net.URI;
import java.util.List;


@ApplicationScoped
public class villeService implements PanacheRepositoryBase<ville,Long > {
    @Inject
    EntityManager em;

    public  List<ville> findAllVilles(){
        return  em.createQuery("select o from ville o join fetch o.Direction_regionale").getResultList() ;

    }
    public  List<ville> findVilleByDirect(Long idtyp){
        return    em.createQuery("select o from ville o join fetch o.myDirection_regionale h where h.id =:idtyp")
                .setParameter("idtyp",idtyp)
                .getResultList();

    }



}
