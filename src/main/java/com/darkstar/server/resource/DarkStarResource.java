package com.darkstar.server.resource;

import com.darkstar.server.model.Health;
import com.darkstar.server.model.Image;
import com.darkstar.server.model.Telemetry;
import com.darkstar.server.repository.HealthRepository;
import com.darkstar.server.repository.TelemetryRepository;
import com.darkstar.server.repository.ImageRepository;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api/mars-shuttle")
public class DarkStarResource {

    @Inject
    TelemetryRepository telemetryRepository;

    @Inject
    ImageRepository imageRespository;

    @Inject
    HealthRepository healthRepository;

    @POST
    @Transactional
    @Path("/telemetry")
    public void addTelemetry(Telemetry telemetry) {
        telemetryRepository.persist(telemetry);
    }

    @POST
    @Transactional
    @Path("/images")
    public void addImage(Image image) {
        imageRespository.persist(image);
    }

    @POST
    @Transactional
    @Path("/health")
    public void addHealth(Health health) {
        healthRepository.persist(health);
    }
}