package com.vieecoles.services;

import com.vieecoles.entities.type_objet;
import com.vieecoles.entities.type_offre;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class type_offreService implements PanacheRepositoryBase<type_offre, Long> {

   public List<type_offre> getListtype_offre(){
       return  type_offre.listAll();
   }



}
