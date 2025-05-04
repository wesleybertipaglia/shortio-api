package com.wesleybertipaglia.dtos;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.*;

public class ResourceDtos {

        private static final String REQUIRED = " is required";
        private static final String MIN_SIZE_8 = " must be at least 8 characters long";
        private static final String MIN_SIZE_5 = " must be at least 5 characters long";

        public record Summary(
                        @NotNull(message = "Id" + REQUIRED) ObjectId id,
                        @NotEmpty(message = "Url" + REQUIRED) @Size(min = 8, message = "Url" + MIN_SIZE_8) String url,
                        @NotEmpty(message = "Slug" + REQUIRED) @Size(min = 8, message = "Slug"
                                        + MIN_SIZE_8) String slug) {
        }

        public record Details(
                        @NotNull(message = "Id" + REQUIRED) ObjectId id,
                        @NotEmpty(message = "Url" + REQUIRED) @Size(min = 8, message = "Url" + MIN_SIZE_8) String url,
                        @NotEmpty(message = "Slug" + REQUIRED) @Size(min = 8, message = "Slug"
                                        + MIN_SIZE_8) String slug) {
        }

        public record Create(
                        @NotEmpty(message = "Url" + REQUIRED) @Size(min = 8, message = "Url" + MIN_SIZE_8) String url) {
        }

        public record Update(
                        @Size(min = 5, message = "Url" + MIN_SIZE_5) String url) {
        }
}
