package com.darkstar.server.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Image extends PanacheEntity {

    @NotNull
    @Column(name = "data", columnDefinition = "BINARY VARYING(109870)")
    private byte[] data;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shuttle_id", nullable = false)
    private Shuttle shuttle;
}