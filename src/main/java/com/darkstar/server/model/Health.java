package com.darkstar.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Health extends PanacheEntity {

    @Column(name = "reporting_system")
    private ReportingSystem reportingSystem;

    @Column(name = "status")
    private HealthStatus status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "errorCode")
    private String errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "errorDescription")
    private String errorDescription;

    @Column(name = "reportingTimestamp")
    private LocalDateTime reportingTimestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;
}