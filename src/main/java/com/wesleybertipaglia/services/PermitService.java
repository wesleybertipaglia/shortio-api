package com.wesleybertipaglia.services;

import java.io.IOException;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.wesleybertipaglia.dtos.UserDtos;

import io.permit.sdk.Permit;
import io.permit.sdk.PermitConfig;
import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.api.PermitContextError;
import io.permit.sdk.enforcement.Resource;
import io.permit.sdk.enforcement.User;
import io.permit.sdk.openapi.models.UserCreate;
import io.permit.sdk.openapi.models.UserRead;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PermitService {

    @ConfigProperty(name = "permit.api.key")
    String apiKey;

    @ConfigProperty(name = "permit.api.url")
    String apiUrl;

    @ConfigProperty(name = "permit.debug", defaultValue = "false")
    boolean debug;

    private Permit permit;

    private static final String TENANT = "default";

    @PostConstruct
    void init() {
        this.permit = new Permit(new PermitConfig.Builder(apiKey).withPdpAddress(apiUrl).withDebugMode(debug).build());
    }

    public void createUser(UserDtos.Details user)
            throws IOException, PermitApiError, PermitContextError {
        syncUser(user);
        assignRole(user);
    }

    public UserRead syncUser(UserDtos.Details user)
            throws IOException, PermitApiError, PermitContextError {
        UserCreate userCreate = new UserCreate(user.id().toString()).withEmail(user.email());
        return permit.api.users.sync(userCreate).getResult();
    }

    public boolean checkPermission(String userId, String action, String resourceType)
            throws IOException, PermitApiError, PermitContextError {
        User user = User.fromString(userId);
        Resource resource = new Resource.Builder(resourceType).build();
        return permit.check(user, action, resource);
    }

    public void assignRole(UserDtos.Details user)
            throws IOException, PermitApiError, PermitContextError {
        String role = user.role().name().toLowerCase();
        permit.api.users.assignRole(user.id().toString(), role, TENANT);
    }
}