package com.wesleybertipaglia.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.ForbiddenException;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.User;
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
    private final PermitService permitService;

    @Inject
    public AuthService(
            UserService userService,
            OrgService orgService,
            AuthMapper authMapper,
            PasswordProvider passwordProvider,
            SecurityIdentity securityIdentity,
            PermitService permitService) {
        this.userService = userService;
        this.orgService = orgService;
        this.authMapper = authMapper;
        this.passwordProvider = passwordProvider;
        this.securityIdentity = securityIdentity;
        this.permitService = permitService;
    }

    @Transactional
    public AuthDtos.Response signUp(@Valid AuthDtos.SignUp dto) {
        final var user = userService.create(authMapper.toEntity(dto));
        createUserOrg(user);
        return authMapper.toResponseDto(user);
    }

    @Transactional
    public AuthDtos.Response signIn(AuthDtos.SignIn dto) {
        final var user = userService.getEntityByEmailOptional(dto.email())
                .orElseThrow(() -> new UnauthorizedException(INVALID_CREDENTIALS));

        if (!passwordProvider.matches(dto.password(), user.password)) {
            throw new UnauthorizedException(INVALID_CREDENTIALS);
        }

        return authMapper.toResponseDto(user);
    }

    public User getCurrentUser() {
        final var id = securityIdentity.getPrincipal().getName();
        return userService.getEntityByIdOptional(new ObjectId(id))
                .orElseThrow(() -> new UnauthorizedException(INVALID_CREDENTIALS));
    }

    public void checkPermission(String action, String resource) {
        final var user = getCurrentUser();
        try {
            boolean isPermitted = permitService.checkPermission(user.id.toString(), action, resource);
            if (!isPermitted) {
                throw new ForbiddenException(FORBIDDEN_ACTION);
            }
        } catch (Exception e) {
            throw new ForbiddenException("Erro ao verificar permissões: " + e.getMessage());
        }
    }

    private void createUserOrg(User user) {
        final var orgName = user.name.split(" ")[0].toLowerCase() + "'s org";
        orgService.create(user.id, orgName);
    }
}
