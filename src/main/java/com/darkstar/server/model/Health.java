package com.darkstar.server.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class Health extends PanacheEntity {
    @NotNull
    @Column(name = "reporting_system")
    private String reportingSystem;

    @NotNull
    @Column(name = "status")
    private String status;

    @Column(name = "error_code")
    private String errorCode;

    @Column(name = "error_description")
    private String errorDescription;

    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}