package com.darkstar.server.resource;
import com.darkstar.server.model.Mission;
import com.darkstar.server.model.Shuttle;
import com.darkstar.server.repository.MissionRepository;
import com.darkstar.server.repository.ShuttleRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/api/missions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MissionResource {

    private static final Logger LOGGER = Logger.getLogger(MissionResource.class);

    @Inject
    MissionRepository missionRepository;

    @Inject
    ShuttleRepository shuttleRepository;

    @GET
    public List<Mission> getAllMissions() {
        return missionRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Mission getMissionById(@PathParam("id") Long id) {
        Mission mission = missionRepository.findById(id);
        if (mission == null) {
            throw new NotFoundException();
        }
        return mission;
    }

    @POST
    @Transactional
    public Response addMission(Mission mission) {
        Shuttle shuttle = shuttleRepository.findById(mission.shuttle.id);
        if (shuttle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Shuttle with id " + mission.shuttle.id + " not found.").build();
        }
        missionRepository.persist(mission);
        LOGGER.info("Created mission " + mission.id);
        return Response.created(URI.create("/api/missions/" + mission.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMission(@PathParam("id") Long id, Mission mission) {
        Mission entity = missionRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Mission with id of " + id + " does not exist.", Response.Status.NOT_FOUND);
        }
        entity.setName(mission.getName());
        entity.setStartDate(mission.getStartDate());
        entity.setEndDate(mission.getEndDate());
        entity.setStatus(mission.getStatus());
        entity.setShuttle(mission.getShuttle());
        missionRepository.persist(entity);
        LOGGER.info("Updated mission " + id);
        return Response.ok(entity).build();
    }
}