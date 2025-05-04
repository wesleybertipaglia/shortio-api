package com.wesleybertipaglia.repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.entities.User;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {
    public Optional<User> findByEmailOptional(String email) {
        return Optional.ofNullable(find("email", email).firstResult());
    }

    public PanacheQuery<User> findByIds(List<ObjectId> ids) {
        return find("_id in ?1", ids);
    }
}
