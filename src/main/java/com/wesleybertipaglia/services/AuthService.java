package com.wesleybertipaglia.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;

import java.util.Arrays;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.User;
import com.wesleybertipaglia.enums.Role;
import com.wesleybertipaglia.mappers.AuthMapper;
import com.wesleybertipaglia.providers.PasswordProvider;
import io.quarkus.security.UnauthorizedException;
import io.quarkus.security.identity.SecurityIdentity;

@ApplicationScoped
public class AuthService {

    private static final String INVALID_CREDENTIALS = "Invalid credentials";
    private static final String FORBIDDEN_ACTION = "You don't have permission to perform this action";

    private final UserService userService;
    private final OrgService orgService;
    private final AuthMapper authMapper;
    private final PasswordProvider passwordProvider;
    private final SecurityIdentity securityIdentity;

    @Inject
    public AuthService(
            UserService userService,
            OrgService orgService,
            AuthMapper authMapper,
            PasswordProvider passwordProvider,
            SecurityIdentity securityIdentity) {
        this.userService = userService;
        this.orgService = orgService;
        this.authMapper = authMapper;
        this.passwordProvider = passwordProvider;
        this.securityIdentity = securityIdentity;
    }

    @Transactional
    public AuthDtos.Response signUp(@Valid AuthDtos.SignUp dto) {
        final var user = userService.create(authMapper.toEntity(dto));
        createUserOrg(user);
        return authMapper.toResponseDto(user);
    }

    @Transactional
    public AuthDtos.Response signIn(AuthDtos.SignIn dto) {
        final var user = userService.getByEmailOptional(dto.email())
                .orElseThrow(() -> new UnauthorizedException(INVALID_CREDENTIALS));

        if (!passwordProvider.matches(dto.password(), user.password)) {
            throw new UnauthorizedException(INVALID_CREDENTIALS);
        }

        return authMapper.toResponseDto(user);
    }

    public User getCurrentUser() {
        final var id = securityIdentity.getPrincipal().getName();
        return userService.getByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new UnauthorizedException(INVALID_CREDENTIALS));
    }

    public void checkPermission(Role... allowedRoles) {
        final var user = getCurrentUser();
        boolean hasPermission = Arrays.stream(allowedRoles)
                .anyMatch(role -> role.equals(user.role));

        if (!hasPermission) {
            throw new ForbiddenException(FORBIDDEN_ACTION);
        }
    }

    private void createUserOrg(User user) {
        final var orgName = user.name.split(" ")[0].toLowerCase() + "'s org";
        orgService.create(user.id, orgName);
    }
}
