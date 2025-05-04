package com.wesleybertipaglia.dtos;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDtos {
        public record Summary(
                        @NotNull(message = "Id is required") ObjectId id,
                        @NotEmpty(message = "Name is required") @Size(min = 3, message = "Name must be at least 3 characters long") String name,
                        @NotEmpty(message = "Email is required") @Email(message = "Email must be valid") String email,
                        @NotNull(message = "Role is required") Role role) {
        }

        public record Details(
                        @NotNull(message = "Id is required") ObjectId id,
                        @NotEmpty(message = "Name is required") @Size(min = 3, message = "Name must be at least 3 characters long") String name,
                        @NotEmpty(message = "Email is required") @Email(message = "Email must be valid") String email,
                        @NotNull(message = "Role is required") Role role) {
        }

        public record Create(
                        @NotEmpty(message = "Name is required") @Size(min = 3, message = "Name must be at least 3 characters long") String name,
                        @NotEmpty(message = "Email is required") @Email(message = "Email must be valid") String email,
                        @NotEmpty(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") String password,
                        Role role) {
        }

        public record Update(
                        @Size(min = 3, message = "Name must be at least 3 characters long") String name,
                        @Email(message = "Email must be valid") String email,
                        @Size(min = 8, message = "Password must be at least 8 characters long") String password,
                        Role role) {
        }
}
