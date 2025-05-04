package com.wesleybertipaglia.dtos;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.enums.Role;

import jakarta.validation.constraints.*;

public class UserDtos {

        private static final String REQUIRED = " is required";
        private static final String MIN_NAME_LENGTH = "Name must be at least 3 characters long";
        private static final String MIN_PASSWORD_LENGTH = "Password must be at least 8 characters long";
        private static final String VALID_EMAIL = "Email must be valid";

        public record Summary(
                        @NotNull(message = "Id" + REQUIRED) ObjectId id,
                        @NotEmpty(message = "Name" + REQUIRED) @Size(min = 3, message = MIN_NAME_LENGTH) String name,
                        @NotEmpty(message = "Email" + REQUIRED) @Email(message = VALID_EMAIL) String email,
                        @NotNull(message = "Role" + REQUIRED) Role role) {
        }

        public record Details(
                        @NotNull(message = "Id" + REQUIRED) ObjectId id,
                        @NotEmpty(message = "Name" + REQUIRED) @Size(min = 3, message = MIN_NAME_LENGTH) String name,
                        @NotEmpty(message = "Email" + REQUIRED) @Email(message = VALID_EMAIL) String email,
                        @NotNull(message = "Role" + REQUIRED) Role role) {
        }

        public record Create(
                        @NotEmpty(message = "Name" + REQUIRED) @Size(min = 3, message = MIN_NAME_LENGTH) String name,
                        @NotEmpty(message = "Email" + REQUIRED) @Email(message = VALID_EMAIL) String email,
                        @NotEmpty(message = "Password"
                                        + REQUIRED) @Size(min = 8, message = MIN_PASSWORD_LENGTH) String password,
                        @NotNull(message = "Role" + REQUIRED) Role role) {
        }

        public record Update(
                        @Size(min = 3, message = MIN_NAME_LENGTH) String name,
                        @Email(message = VALID_EMAIL) String email,
                        @Size(min = 8, message = MIN_PASSWORD_LENGTH) String password,
                        Role role) {
        }

        public record ChangePassword(
                        @NotEmpty(message = "Current password" + REQUIRED) String currentPassword,
                        @NotEmpty(message = "New password"
                                        + REQUIRED) @Size(min = 8, message = MIN_PASSWORD_LENGTH) String newPassword) {
        }
}