package com.darkstar.server.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Mission extends PanacheEntity {
    public String name;
    public LocalDate startDate;
    public LocalDate endDate;
    public MissionStatus status;

    @ManyToOne
    public Shuttle shuttle;
}
