package com.wesleybertipaglia.mappers;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.dtos.ResourceDtos;
import com.wesleybertipaglia.entities.Resource;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResourceMapper {

    public ResourceDtos.Summary toSummaryDto(Resource resource) {
        return new ResourceDtos.Summary(resource.id, resource.url, resource.slug);
    }

    public ResourceDtos.Details toDetailsDto(Resource resource) {
        return new ResourceDtos.Details(resource.id, resource.url, resource.slug);
    }

    public Resource toEntity(ObjectId orgId, String slug, ResourceDtos.Create resourceDto) {
        return new Resource(resourceDto.url(), slug, orgId);
    }
}