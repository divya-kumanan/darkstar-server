package com.darkstar.server.repository;

import com.darkstar.server.model.Shuttle;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShuttleRepository implements PanacheRepository<Shuttle> {
}
