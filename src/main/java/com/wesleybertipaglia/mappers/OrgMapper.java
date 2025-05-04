package com.wesleybertipaglia.mappers;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.Org;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrgMapper {
    public OrgDtos.Summary toSummaryDto(Org org) {
        return new OrgDtos.Summary(org.id, org.name, org.slug);
    }

    public OrgDtos.Details toDetailsDto(Org org) {
        return new OrgDtos.Details(org.id, org.name, org.slug, org.ownerId);
    }

    public Org toEntity(ObjectId ownerId, OrgDtos.Create dto) {
        return new Org(dto.name(), dto.slug(), ownerId);
    }
}
