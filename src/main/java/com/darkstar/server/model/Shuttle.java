package com.darkstar.server.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Shuttle extends PanacheEntity {
    @Column(name= "name")
    public String name;

    @Column(name = "manufactured_date")
    public LocalDate manufacturedDate;

    @Column(name = "status")
    public ShuttleStatus status;

    @OneToMany(mappedBy = "shuttle", fetch = FetchType.LAZY)
    @JsonIgnore
    public List<Mission> missions = new ArrayList<>();
}
