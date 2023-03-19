package com.darkstar.server.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Data
public class Telemetry extends PanacheEntity {
    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "altitude")
    private double altitude;

    @Column(name = "velocity")
    private double velocity;

    @Column(name = "systemStatus")
    private String systemStatus;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}