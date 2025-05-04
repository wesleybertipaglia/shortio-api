package com.wesleybertipaglia.repositories;

import java.util.Optional;

import com.wesleybertipaglia.entities.User;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheMongoRepository<User> {
    public Optional<User> findByEmailOptional(String email) {
        return Optional.ofNullable(find("email", email).firstResult());
    }
}
