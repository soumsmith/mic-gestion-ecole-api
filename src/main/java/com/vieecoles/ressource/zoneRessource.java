package com.vieecoles.ressource;


import com.vieecoles.entities.Zone;
import com.vieecoles.services.zoneService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/zone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class zoneRessource {
    @Inject
    zoneService zonService ;
    @GET
    public List<Zone> list() {
        return zonService.getListzone();
    }

    @GET
    @Path("/{id}")
    public Zone get(@PathParam("id") Long id) {
        return zonService.findById(id);
    }

    @GET
    @Path("commune/{idCommune}")
    public List<Zone> getZoneByCommune(@PathParam("idCommune") Long idCommune) {
        return zonService.findZoneByCommune(idCommune) ;
    }


    @GET
    @Path("/soumm-test")
    public String get() {
        return "soumTestok";
    }

    @POST
    @Transactional
    public Response create(Zone zon) {
        return zonService.createzone(zon);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public Zone update(@PathParam("id") Long id, Zone zon) {
    return  zonService.updatezone(id,zon);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        zonService.deletezone(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<Zone> search(@PathParam("libelle") String libelle) {
        return zonService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return zonService.count();
    }


}
