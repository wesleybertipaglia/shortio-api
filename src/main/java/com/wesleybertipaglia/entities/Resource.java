package com.wesleybertipaglia.entities;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@MongoEntity(collection = "resources")
public class Resource extends PanacheMongoEntity {
    public String url;
    public String slug;
    public ObjectId orgId;

    public Resource() {
    }

    public Resource(
            @NotEmpty(message = "Url is required") @Size(min = 8, message = "Url must be at least 8 characters long") String url,
            @NotEmpty(message = "Slug is required") @Size(min = 8, message = "Slug must be at least 8 characters long") String slug,
            @NotNull ObjectId orgId) {
        this.url = url;
        this.slug = slug;
        this.orgId = orgId;
    }
}
