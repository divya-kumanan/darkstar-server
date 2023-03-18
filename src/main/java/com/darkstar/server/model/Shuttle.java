package com.darkstar.server.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shuttle extends PanacheEntity {
    public String name;
    public LocalDate manufacturedDate;
    public ShuttleStatus status;

    @OneToMany(mappedBy = "shuttle", fetch = FetchType.EAGER)
    public List<Mission> missions = new ArrayList<>();
}
