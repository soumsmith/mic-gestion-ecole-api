package com.vieecoles.ressource;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
    @Override
    public Response toResponse(RuntimeException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
    }
}
