package com.vieecoles.ressource;


import com.vieecoles.dto.ecoleDto;
import com.vieecoles.dto.ecoleDto2;
import com.vieecoles.entities.profil;
import com.vieecoles.services.operations.ecoleService;
import com.vieecoles.services.profilService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
