package com.vieecoles.ressource;


import com.vieecoles.dao.entities.profil;
import com.vieecoles.dto.ecoleDto;
import com.vieecoles.dto.ecoleDto2;
import com.vieecoles.services.operations.ecoleService;
import com.vieecoles.services.profilService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/connecte/ecole")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ecoleRessource {
    @Inject
    ecoleService ecoleService ;
    @GET
    public List<ecoleDto2> list() {
        return ecoleService.findAllecoleDto2() ;
    }




}
