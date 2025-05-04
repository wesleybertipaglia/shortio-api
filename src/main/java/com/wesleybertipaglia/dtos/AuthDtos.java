package com.wesleybertipaglia.dtos;

import java.time.Instant;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthDtos {
        public record SignIn(
                        @NotEmpty(message = "Email is required") @Email(message = "Email must be valid") String email,
                        @NotEmpty(message = "Password is required") String password) {
        }

        public record SignUp(
                        @NotEmpty(message = "Name is required") @Size(min = 3, message = "Name must be at least 3 characters long") String name,
                        @NotEmpty(message = "Email is required") @Email(message = "Email must be valid") String email,
                        @NotEmpty(message = "Password is required") @Size(min = 8, message = "Password must be at least 8 characters long") String password) {
        }

        public record Response(
                        @NotNull Token token,
                        @NotNull UserDtos.Details user) {
        }

        public record Token(
                        @NotEmpty(message = "Token type is required") String type,
                        @NotEmpty(message = "Token content is required") String content,
                        @NotNull Instant expiration) {
        }
}