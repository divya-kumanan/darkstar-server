package com.darkstar.server;

import com.darkstar.server.model.MarsShuttleErrorResponse;
import io.quarkus.security.UnauthorizedException;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DarkstarExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(DarkstarExceptionHandler.class);

    @Override
    public Response toResponse(Throwable e) {
        LOG.error("Unhandled exception caught", e);

        if (e instanceof ConstraintViolationException) {
            MarsShuttleErrorResponse errorResponse = new MarsShuttleErrorResponse("Validation failed ",
                    Response.Status.BAD_REQUEST.getStatusCode());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }

        if(e instanceof NotFoundException){
            MarsShuttleErrorResponse errorResponse = new MarsShuttleErrorResponse(e.getMessage(),
                    Response.Status.NOT_FOUND.getStatusCode());
            return Response.status(Response.Status.NOT_FOUND).entity(errorResponse).build();
        }

        if (e instanceof UnauthorizedException) {
            MarsShuttleErrorResponse errorResponse = new MarsShuttleErrorResponse(e.getMessage(),
                    Response.Status.UNAUTHORIZED.getStatusCode());
            return Response.status(Response.Status.UNAUTHORIZED).entity(errorResponse).build();
        }

        if(e instanceof WebApplicationException){
            int status = ((WebApplicationException) e).getResponse().getStatus();
            MarsShuttleErrorResponse errorResponse = new MarsShuttleErrorResponse(e.getMessage(),status);
            return Response.status(status).entity(errorResponse).build();
        }

        MarsShuttleErrorResponse errorResponse = new MarsShuttleErrorResponse("Internal server error",
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    }
}