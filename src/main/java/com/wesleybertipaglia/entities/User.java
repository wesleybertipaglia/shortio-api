package com.wesleybertipaglia.entities;

import com.wesleybertipaglia.enums.Role;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@MongoEntity(collection = "users")
public class User extends PanacheMongoEntity {
    public String name;
    public String email;
    public String password;
    public Role role;

    public User() {
    }

    public User(
            @NotEmpty(message = "Name is required") @Size(min = 3, message = "Name must be at least 3 characters long") String name,
            @NotEmpty(message = "Email is required") @Email(message = "Email must be valid") String email,
            @NotEmpty(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") String password,
            @NotNull(message = "Role is required") Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}