package com.darkstar.server;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import io.quarkus.security.UnauthorizedException;

@Provider
public class DarkstarExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(DarkstarExceptionHandler.class);

    @Override
    public Response toResponse(Throwable e) {
        LOG.error("Unhandled exception caught", e);

        if (e instanceof ConstraintViolationException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Validation failed "+ e.getMessage())
                    .build();
        }

        if(e instanceof NotFoundException){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        if (e instanceof UnauthorizedException) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized access").build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
    }
}

