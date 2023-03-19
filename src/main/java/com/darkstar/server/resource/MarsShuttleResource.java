package com.darkstar.server.resource;

import com.darkstar.server.model.Health;
import com.darkstar.server.model.Image;
import com.darkstar.server.model.Telemetry;
import com.darkstar.server.repository.HealthRepository;
import com.darkstar.server.repository.TelemetryRepository;
import com.darkstar.server.repository.ImageRepository;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
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

    @POST
    @Transactional
    @Path("/telemetry")
    public void addTelemetry(Telemetry telemetry) {
        telemetryRepository.persist(telemetry);
    }

    @POST
    @Transactional
    @Path("/images")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Counted(name = "countAddImage", description = "Counts how many times the addImage method has been invoked")
    public void addImage(InputStream inputStream) {
        Image image = new Image();
        try {
            image.setData(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new WebApplicationException("Issue in uploading the image");
        }
        LocalDateTime localDateTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        image.setTimestamp(localDateTime);
        imageRepository.persist(image);
        LOGGER.info("Image saved successfully with ID: " + image.id);
    }

    @POST
    @Transactional
    @Path("/health")
    public void addHealth(Health health) {
        LOGGER.info("Entered here");
        healthRepository.persist(health);
        LOGGER.info("Health saved successfully with ID: " + health.id);
    }
}