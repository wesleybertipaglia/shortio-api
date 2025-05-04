package com.wesleybertipaglia.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.entities.Org;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrgRepository implements PanacheMongoRepository<Org> {
    public Optional<Org> findBySlugOptional(String slug) {
        return Optional.ofNullable(find("slug", slug).firstResult());
    }

    public Optional<Org> findByUserIdOptional(ObjectId userId) {
        return Optional.ofNullable(find("userIds", userId).firstResult());
    }
}
