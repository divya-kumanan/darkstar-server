package com.darkstar.server.repository;

import com.darkstar.server.model.Mission;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MissionRepository implements PanacheRepository<Mission> {
}
