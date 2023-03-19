package com.darkstar.server.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
public class Mission extends PanacheEntity {
    @Column(name= "name")
    public String name;
    @Column(name= "start_date")
    public LocalDate startDate;
    @Column(name= "end_date")
    public LocalDate endDate;
    @Column(name= "status")
    public MissionStatus status;

    @ManyToOne(fetch= FetchType.LAZY)
    public Shuttle shuttle;
}
