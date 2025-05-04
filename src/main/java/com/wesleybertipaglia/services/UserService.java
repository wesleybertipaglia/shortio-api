package com.wesleybertipaglia.services;

import java.util.Optional;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.Org;
import com.wesleybertipaglia.entities.User;
import com.wesleybertipaglia.mappers.UserMapper;
import com.wesleybertipaglia.repositories.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;

@ApplicationScoped
public class UserService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String EMAIL_ALREADY_IN_USE = "Email already in use";
    private static final String USER_NOT_IN_ORG = "User not found in organization";
    private static final String CANNOT_DELETE_OWNER = "You cannot delete the organization owner";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthService authService;
    private final OrgService orgService;
    private final PermitService permitService;

    @Inject
    public UserService(UserRepository userRepository, UserMapper userMapper, AuthService authService,
            OrgService orgService, PermitService permitService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.authService = authService;
        this.orgService = orgService;
        this.permitService = permitService;
    }

    public PageDtos.Result<UserDtos.Summary> list(Integer page, Integer size) {
        var org = orgService.getByCurrentUser();
        var query = userRepository.findByIds(org.userIds).page(page, size);
        var items = query.list().stream().map(userMapper::toSummaryDto).toList();
        var pagination = new PageDtos.Pagination(page, size);
        return new PageDtos.Result<>(items, pagination);
    }

    public Optional<User> getEntityByIdOptional(ObjectId id) {
        return userRepository.findByIdOptional(id);
    }

    public User getEntityById(ObjectId id) {
        return getEntityByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public Optional<User> getEntityByEmailOptional(String email) {
        return userRepository.findByEmailOptional(email);
    }

    public User getEntityByEmail(String email) {
        return getEntityByEmailOptional(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public UserDtos.Details getById(ObjectId id) {
        return userMapper.toDetailsDto(getEntityById(id));
    }

    public UserDtos.Details getByEmail(String email) {
        return userMapper.toDetailsDto(getEntityByEmail(email));
    }

    @Transactional
    public User create(User user) {
        try {
            validateEmailUniqueness(user.email);
            userRepository.persist(user);
            permitService.createUser(userMapper.toDetailsDto(user));

            return user;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @Transactional
    public User update(ObjectId id, @Valid UserDtos.Update dto) {
        try {
            final User user = getEntityById(id);

            if (dto.email() != null && !dto.email().equals(user.email)) {
                validateEmailUniqueness(dto.email());
                user.email = dto.email();
            }
            if (dto.name() != null) {
                user.name = dto.name();
            }
            if (dto.password() != null) {
                user.password = dto.password();
            }
            if (dto.role() != null) {
                user.role = dto.role();
                permitService.assignRole(userMapper.toDetailsDto(user));
            }

            return user;
        } catch (Exception e) {
            System.err.println("Error sync user in Permit.io: " + e.getMessage());
            throw new InternalServerErrorException("");
        }
    }

    @Transactional
    public void delete(ObjectId id) {
        final User user = getEntityById(id);
        userRepository.delete(user);
    }

    @Transactional
    public UserDtos.Details createOrgUser(@Valid UserDtos.Create dto) {
        authService.checkPermission("create", "user");
        validateEmailUniqueness(dto.email());

        final var org = orgService.getByCurrentUser();
        final var user = create(userMapper.toEntity(dto));

        org.userIds.add(user.id);
        orgService.save(org);

        return userMapper.toDetailsDto(user);
    }

    @Transactional
    public UserDtos.Details updateUserOrg(ObjectId id, UserDtos.Update dto) {
        authService.checkPermission("update", "user");
        final var org = orgService.getByCurrentUser();

        validateUserInOrg(org, id);

        final var user = update(id, dto);
        orgService.save(org);

        return userMapper.toDetailsDto(user);
    }

    @Transactional
    public void deleteOrgUser(ObjectId id) {
        authService.checkPermission("delete", "user");
        final var org = orgService.getByCurrentUser();

        validateUserInOrg(org, id);

        if (org.ownerId.equals(id)) {
            throw new ForbiddenException(CANNOT_DELETE_OWNER);
        }

        delete(id);
        org.userIds.remove(id);
        orgService.save(org);
    }

    private void validateEmailUniqueness(String email) {
        if (getEntityByEmailOptional(email).isPresent()) {
            throw new IllegalArgumentException(EMAIL_ALREADY_IN_USE);
        }
    }

    private void validateUserInOrg(Org org, ObjectId userId) {
        if (!org.userIds.contains(userId)) {
            throw new NotFoundException(USER_NOT_IN_ORG);
        }
    }
}
