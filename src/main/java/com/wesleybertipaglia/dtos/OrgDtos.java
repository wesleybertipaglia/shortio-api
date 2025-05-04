package com.wesleybertipaglia.dtos;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.*;

public class OrgDtos {

        private static final String REQUIRED = " is required";
        private static final String MIN_SIZE_3 = " must be at least 3 characters long";

        public record Summary(
                        @NotNull(message = "Id" + REQUIRED) ObjectId id,
                        @NotEmpty(message = "Name" + REQUIRED) String name,
                        @NotEmpty(message = "Slug" + REQUIRED) String slug) {
        }

        public record Details(
                        @NotNull(message = "Id" + REQUIRED) ObjectId id,
                        @NotEmpty(message = "Name" + REQUIRED) String name,
                        @NotEmpty(message = "Slug" + REQUIRED) String slug,
                        @NotNull(message = "Owner Id" + REQUIRED) ObjectId ownerId) {
        }

        public record Create(
                        @NotEmpty(message = "Name" + REQUIRED) @Size(min = 3, message = "Name"
                                        + MIN_SIZE_3) String name,
                        @NotEmpty(message = "Slug" + REQUIRED) @Size(min = 3, message = "Slug"
                                        + MIN_SIZE_3) String slug) {
        }

        public record Update(
                        @Size(min = 3, message = "Name" + MIN_SIZE_3) String name,
                        @Size(min = 3, message = "Slug" + MIN_SIZE_3) String slug) {
        }
}
