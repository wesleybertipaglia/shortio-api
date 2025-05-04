package com.wesleybertipaglia.services;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.dtos.OrgDtos;
import com.wesleybertipaglia.entities.Org;
import com.wesleybertipaglia.mappers.OrgMapper;
import com.wesleybertipaglia.repositories.OrgRepository;
import com.wesleybertipaglia.helpers.SlugHelper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class OrgService {

    private static final String ORGANIZATION_NOT_FOUND = "Organization not found";
    private static final String ORG_NOT_FOUND_FOR_USER = "Organization not found for current user";

    private final OrgRepository orgRepository;
    private final AuthService authService;
    private final OrgMapper orgMapper;

    @Inject
    public OrgService(OrgRepository orgRepository,
            AuthService authService,
            OrgMapper orgMapper) {
        this.orgRepository = orgRepository;
        this.authService = authService;
        this.orgMapper = orgMapper;
    }

    @Transactional
    public void save(Org org) {
        orgRepository.persistOrUpdate(org);
    }

    public OrgDtos.Details get() {
        final var org = getByCurrentUser();
        return orgMapper.toDetailsDto(org);
    }

    public OrgDtos.Details getBySlug(String slug) {
        final var org = orgRepository.findBySlugOptional(slug)
                .orElseThrow(() -> new NotFoundException(ORGANIZATION_NOT_FOUND));

        return orgMapper.toDetailsDto(org);
    }

    public Org getByCurrentUser() {
        final var user = authService.getCurrentUser();
        return orgRepository.findByUserIdOptional(user.id)
                .orElseThrow(() -> new NotFoundException(ORG_NOT_FOUND_FOR_USER));
    }

    @Transactional
    public void create(ObjectId ownerId, String name) {
        final var slug = SlugHelper.generateRandomSlug(8);
        final var org = new Org(name, slug, ownerId);
        orgRepository.persist(org);
    }

    @Transactional
    public OrgDtos.Details update(@Valid OrgDtos.Update dto) {
        final var org = getByCurrentUser();
        authService.checkPermission("update", "org");

        if (dto.name() != null) {
            org.name = dto.name();
        }

        if (dto.slug() != null) {
            org.slug = dto.slug();
        }

        save(org);
        return orgMapper.toDetailsDto(org);
    }
}
