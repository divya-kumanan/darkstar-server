package com.darkstar.server.repository;

import javax.enterprise.context.ApplicationScoped;
import com.darkstar.server.model.Image;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ImageRepository implements PanacheRepository<Image> {
}