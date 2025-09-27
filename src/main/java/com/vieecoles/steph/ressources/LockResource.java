package com.vieecoles.steph.ressources;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;

import com.vieecoles.steph.dto.LockResponseDto;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.LockedConcept;
import com.vieecoles.steph.services.LockService;

@Path("/locks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LockResource {

    @Inject
    LockService lockService;

    @GET
    @Path("/{type}/{conceptId}")
    public Map<String, Boolean> isLocked(@PathParam("conceptId") String conceptId, @PathParam("type") String type) {
        return Map.of("locked", lockService.isLocked(conceptId, type));
    }

    @POST
    @Path("/{type}/{conceptId}/lock")
    public LockResponseDto lock(@PathParam("conceptId") String conceptId,
                             @PathParam("type") String type,
                             @QueryParam("actor") String actor) {
        return lockService.lock(conceptId, type, actor);
    }

    @POST
    @Path("/{type}/{conceptId}/unlock")
    public LockResponseDto unlock(@PathParam("conceptId") String conceptId,
                               @PathParam("type") String type,
                               @QueryParam("actor") String actor) {
        return lockService.unlock(conceptId, type, actor);
    }

    @GET
    @Path("/locked")
    public List<LockedConcept> listLocked() {
        return lockService.listByStatus(Constants.LOCKED);
    }

    @GET
    @Path("/unlocked")
    public List<LockedConcept> listUnlocked() {
        return lockService.listByStatus(Constants.UNLOCKED);
    }

    @GET
    @Path("/history/{conceptId}")
    public List<LockedConcept> getHistory(@PathParam("conceptId") String conceptId) {
        return lockService.getHistory(conceptId);
    }

    @GET
    public List<LockedConcept> listAllCurrent() {
        return lockService.listAllCurrent();
    }
}
