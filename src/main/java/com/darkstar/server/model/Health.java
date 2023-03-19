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
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "error_code")
    private String errorCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "error_description")
    private String errorDescription;

    @Column(name = "reporting_timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shuttle_id", nullable = false)
    private Shuttle shuttle;
}