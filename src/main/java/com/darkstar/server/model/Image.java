package com.darkstar.server.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class Image extends PanacheEntity {

    @NotNull
    @Column(name = "data")
    private byte[] data;

    @NotNull
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}