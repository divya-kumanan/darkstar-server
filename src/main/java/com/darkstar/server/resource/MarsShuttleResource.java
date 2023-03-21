package com.darkstar.server.resource;

import com.darkstar.server.model.*;
import com.darkstar.server.repository.HealthRepository;
import com.darkstar.server.repository.MissionRepository;
import com.darkstar.server.repository.TelemetryRepository;
import com.darkstar.server.repository.ImageRepository;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Path("/api/mars-shuttle")
public class MarsShuttleResource {

    private static final Logger LOGGER = Logger.getLogger(MarsShuttleResource.class);

    @Inject
    TelemetryRepository telemetryRepository;

    @Inject
    ImageRepository imageRepository;

    @Inject
    HealthRepository healthRepository;

    @Inject
    MissionRepository missionRepository;

    @POST
    @Transactional
    @Path("/telemetry")
    public Response addTelemetry(Telemetry telemetry) {
        if (telemetry.getMission() == null) {
            throw new WebApplicationException("Telemetry details not provided in the request body", Response.Status.BAD_REQUEST);
        }
        Mission mission = Mission.findById(telemetry.getMission().id);
        if (mission == null) {
            throw new WebApplicationException("Mission with ID " + telemetry.getMission().id + " not found", Response.Status.NOT_FOUND);
        }
        if (telemetry.getAltitude() < 100 && telemetry.getVelocity() < 10) {
            mission.setStatus(MissionStatus.LANDED);
            mission.setEndDate(telemetry.getTimestamp().toLocalDate());
        } else if (telemetry.getVelocity() > 50) {
            mission.setStatus(MissionStatus.CRASHED);
            mission.setEndDate(telemetry.getTimestamp().toLocalDate());
        } else {
            mission.setStatus(MissionStatus.IN_PROGRESS);
        }
        telemetry.setMission(mission);
        telemetryRepository.persist(telemetry);
        missionRepository.persist(mission);
        LOGGER.info("Telemetry published for the mission " + mission.id);
        return Response.created(URI.create("/api/missions/" + mission.id)).build();
    }

    @POST
    @Transactional
    @Path("/images")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response addImage(InputStream inputStream, @QueryParam("missionId") Long missionId) {
        Mission mission = missionRepository.findById(missionId);
        if (mission == null) {
            throw new WebApplicationException("Mission with ID " + missionId + " not found", Response.Status.NOT_FOUND);
        }
        Image image = new Image();
        image.setMission(mission);
        try {
            image.setData(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new WebApplicationException("Issue in uploading the image");
        }
        LocalDateTime localDateTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        image.setTimestamp(localDateTime);
        imageRepository.persist(image);
        LOGGER.info("Image saved for the mission " + mission.id);
        return Response.created(URI.create("/api/missions/" + mission.id)).build();
    }

    @POST
    @Transactional
    @Path("/health")
    public Response addHealth(Health health) {
        if (health.getMission() == null) {
            throw new WebApplicationException("Health details not provided in the request body", Response.Status.BAD_REQUEST);
        }
        Mission mission = missionRepository.findById(health.getMission().id);
        if (mission == null) {
            throw new WebApplicationException("Mission not found with id: " + health.getMission().id, Response.Status.NOT_FOUND);
        }
        if (health.getStatus().equals(HealthStatus.DOWN) || health.getStatus().equals(HealthStatus.OUT_OF_SERVICE)) {
            mission.setStatus(MissionStatus.CRASHED);
            mission.setEndDate(health.getReportingTimestamp().toLocalDate());
        }
        health.setMission(mission);
        healthRepository.persist(health);
        missionRepository.persist(mission);
        LOGGER.info("Health status published for the mission " + mission.id);
        return Response.created(URI.create("/api/missions/" + mission.id)).build();
    }
}