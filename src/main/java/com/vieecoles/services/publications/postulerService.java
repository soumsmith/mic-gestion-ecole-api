package com.vieecoles.services.publications;

import com.vieecoles.dto.publicationDto;
import com.vieecoles.entities.niveau_etude_has_publication;
import com.vieecoles.entities.postuler;
import com.vieecoles.entities.publication;
import com.vieecoles.projection.publicationSelectDto;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class postulerService implements PanacheRepositoryBase<postuler, Long> {
    @Inject
    EntityManager em;



@Transactional
     public void creerPostuler(postuler post) {
    postuler myPost =new postuler() ;
    myPost.setPublication_id(post.getPublication_id());
    myPost.setSous_attent_personn_sous_attent_personnid(post.getSous_attent_personn_sous_attent_personnid());
    myPost.setDate_creation(LocalDate.now());
    //myPost.setCode(post.getId().toString());
    myPost.persist();
      //return  myPost.getId() ;
     }



}
