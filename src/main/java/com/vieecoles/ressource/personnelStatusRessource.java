package com.vieecoles.ressource;


import com.vieecoles.entities.personnel_status;
import com.vieecoles.services.personnel_statusService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/personnel-status")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class personnelStatusRessource {
    @Inject
    personnel_statusService persStatService ;
    @GET
    public List<personnel_status> list() {
        return persStatService.getListpersonnelStatus();
    }

    @GET
    @Path("/{id}")
    public personnel_status get(@PathParam("id") Long id) {
        return persStatService.findById(id);
    }
    @POST
    @Transactional
    public Response create(personnel_status zon) {
        return persStatService.createpersonnelStatus(zon);
    }


    @PUT
    @Path("/{id}")
    @Transactional
    public personnel_status update(@PathParam("id") Long id, personnel_status zon) {
    return  persStatService.updatepersonnelStatus(id,zon);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        persStatService.deletepersonnStatu(id);
    }

    @GET
    @Path("/search/{libelle}")
    public List<personnel_status> search(@PathParam("libelle") String libelle) {
        return persStatService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return persStatService.count();
    }


}
