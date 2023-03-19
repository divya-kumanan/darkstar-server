package com.darkstar.server.repository;

import com.darkstar.server.model.Health;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HealthRepository implements PanacheRepository<Health> {
}