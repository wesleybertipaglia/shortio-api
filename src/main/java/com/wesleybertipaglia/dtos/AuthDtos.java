package com.wesleybertipaglia.dtos;

import java.time.Instant;

import jakarta.validation.constraints.*;

public class AuthDtos {

        private static final String REQUIRED = " is required";
        private static final String MIN_NAME_LENGTH = "Name must be at least 3 characters long";
        private static final String VALID_EMAIL = "Email must be valid";
        private static final String MIN_PASSWORD_LENGTH = "Password must be at least 8 characters long";

        public record SignIn(
                        @NotEmpty(message = "Email" + REQUIRED) @Email(message = VALID_EMAIL) String email,
                        @NotEmpty(message = "Password" + REQUIRED) String password) {
        }

        public record SignUp(
                        @NotEmpty(message = "Name" + REQUIRED) @Size(min = 3, message = MIN_NAME_LENGTH) String name,
                        @NotEmpty(message = "Email" + REQUIRED) @Email(message = VALID_EMAIL) String email,
                        @NotEmpty(message = "Password"
                                        + REQUIRED) @Size(min = 8, message = MIN_PASSWORD_LENGTH) String password) {
        }

        public record Response(
                        @NotNull Token token,
                        @NotNull UserDtos.Details user) {
        }

        public record Token(
                        @NotEmpty(message = "Token type" + REQUIRED) String type,
                        @NotEmpty(message = "Token content" + REQUIRED) String content,
                        @NotNull(message = "Expiration" + REQUIRED) Instant expiration) {
        }
}
