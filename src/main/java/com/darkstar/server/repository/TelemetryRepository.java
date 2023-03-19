package com.darkstar.server.repository;

import com.darkstar.server.model.Telemetry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelemetryRepository implements PanacheRepository<Telemetry> {
}