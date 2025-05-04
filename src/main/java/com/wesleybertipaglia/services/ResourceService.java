package com.wesleybertipaglia.services;

import com.wesleybertipaglia.dtos.PageDtos;
import com.wesleybertipaglia.dtos.ResourceDtos;
import com.wesleybertipaglia.entities.Resource;
import com.wesleybertipaglia.enums.Role;
import com.wesleybertipaglia.mappers.ResourceMapper;
import com.wesleybertipaglia.repositories.ResourceRepository;
import com.wesleybertipaglia.helpers.SlugHelper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ResourceService {

    private static final String RESOURCE_NOT_FOUND = "Resource not found";
    private static final String FORBIDDEN_ACCESS = "You do not have permission to access this resource";

    private final ResourceRepository resourceRepository;
    private final OrgService orgService;
    private final AuthService authService;
    private final ResourceMapper resourceMapper;

    @Inject
    public ResourceService(ResourceRepository resourceRepository, OrgService orgService,
            AuthService authService, ResourceMapper resourceMapper) {
        this.resourceRepository = resourceRepository;
        this.orgService = orgService;
        this.authService = authService;
        this.resourceMapper = resourceMapper;
    }

    public void save(Resource resource) {
        resourceRepository.persistOrUpdate(resource);
    }

    public PageDtos.Result<ResourceDtos.Summary> list(Integer page, Integer size) {
        final var org = orgService.getByCurrentUser();
        final var query = resourceRepository.findByOrgId(org.id).page(page, size);
        final var items = query.list().stream().map(resourceMapper::toSummaryDto).toList();
        final var pagination = new PageDtos.Pagination(page, size);

        return new PageDtos.Result<>(items, pagination);
    }

    public ResourceDtos.Details getById(String id) {
        final var resource = resourceRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        checkPermission(resource);
        return resourceMapper.toDetailsDto(resource);
    }

    public ResourceDtos.Details getBySlug(String slug) {
        final var resource = resourceRepository.findBySlugOptional(slug)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        return resourceMapper.toDetailsDto(resource);
    }

    @Transactional
    public ResourceDtos.Details create(@Valid ResourceDtos.Create dto) {
        authService.checkPermission(Role.ADMIN, Role.OWNER);
        final var org = orgService.getByCurrentUser();
        final var slug = SlugHelper.generateRandomSlug(8);
        final var resource = resourceMapper.toEntity(org.id, slug, dto);

        save(resource);
        return resourceMapper.toDetailsDto(resource);
    }

    @Transactional
    public ResourceDtos.Details update(String id, @Valid ResourceDtos.Update dto) {
        final var resource = resourceRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        checkPermission(resource);

        if (dto.url() != null) {
            resource.url = dto.url();
        }

        save(resource);
        return resourceMapper.toDetailsDto(resource);
    }

    @Transactional
    public void delete(String id) {
        final var resource = resourceRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(RESOURCE_NOT_FOUND));

        checkPermission(resource);
        resourceRepository.delete(resource);
    }

    private void checkPermission(Resource resource) {
        authService.checkPermission(Role.ADMIN, Role.OWNER);
        final var org = orgService.getByCurrentUser();

        if (!resource.orgId.equals(org.id)) {
            throw new ForbiddenException(FORBIDDEN_ACCESS);
        }
    }
}
