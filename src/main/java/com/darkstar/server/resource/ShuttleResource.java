package com.darkstar.server.resource;

import com.darkstar.server.model.Shuttle;
import com.darkstar.server.repository.ShuttleRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/shuttles")
@Transactional
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShuttleResource {
    private static final Logger LOGGER = Logger.getLogger(ShuttleResource.class);

    @Inject
    ShuttleRepository shuttleRepository;

    @GET
    public List<Shuttle> getShuttles() {
        return shuttleRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Shuttle getShuttleById(@PathParam("id") Long id) {
        Shuttle shuttle = shuttleRepository.findById(id);
        if(shuttle == null){
            throw new NotFoundException();
        }
        return shuttle;
    }

    @POST
    public Response createShuttle(Shuttle shuttle) {
        shuttle.persist();
        LOGGER.info("Created shuttle " + shuttle.id);
        return Response.created(URI.create("/api/shuttles/" + shuttle.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateShuttle(@PathParam("id") Long id, Shuttle shuttle) {
        Shuttle entity = shuttleRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Shuttle with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
        }
        entity.setName(shuttle.getName());
        entity.setManufacturedDate(shuttle.getManufacturedDate());
        entity.setStatus(shuttle.getStatus());
        entity.setMissions(shuttle.getMissions());
        shuttleRepository.persist(entity);
        LOGGER.info("Updated shuttle " + id);
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteShuttle(@PathParam("id") Long id) {
        Shuttle entity = shuttleRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Shuttle with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
        }
        shuttleRepository.delete(entity);
        LOGGER.info("Deleted shuttle " + id);
        return Response.noContent().build();
    }
}