package com.wesleybertipaglia.entities;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "orgs")
public class Org extends PanacheMongoEntity {
    public String slug;
    public String name;
    public ObjectId ownerId;
    public List<ObjectId> userIds = new ArrayList<>();

    public Org() {
    }

    public Org(String name, String slug, ObjectId ownerId) {
        this.name = name;
        this.slug = slug;
        this.ownerId = ownerId;
        this.userIds = new ArrayList<>() {
            {
                add(ownerId);
            }
        };
    }

    public Org(String name, String slug, ObjectId ownerId, List<ObjectId> userIds) {
        this.name = name;
        this.slug = slug;
        this.ownerId = ownerId;
        this.userIds = userIds;
    }
}
