package com.wesleybertipaglia.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.entities.Resource;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResourceRepository implements PanacheMongoRepository<Resource> {
    public Optional<Resource> findByIdOptional(String id) {
        return Optional.ofNullable(find("_id", new ObjectId(id)).firstResult());
    }

    public Optional<Resource> findBySlugOptional(String slug) {
        return Optional.ofNullable(find("slug", slug).firstResult());
    }

    public PanacheQuery<Resource> findByOrgId(ObjectId orgId) {
        return find("orgId", orgId);
    }
}