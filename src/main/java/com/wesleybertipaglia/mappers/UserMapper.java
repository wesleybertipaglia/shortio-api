package com.wesleybertipaglia.mappers;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.User;
import com.wesleybertipaglia.enums.Role;
import com.wesleybertipaglia.providers.PasswordProvider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserMapper {
    private final PasswordProvider passwordProvider;

    @Inject
    public UserMapper(PasswordProvider passwordProvider) {
        this.passwordProvider = passwordProvider;
    }

    public UserDtos.Summary toSummaryDto(User user) {
        return new UserDtos.Summary(user.id, user.name, user.email, user.role);
    }

    public UserDtos.Details toDetailsDto(User user) {
        return new UserDtos.Details(user.id, user.name, user.email, user.role);
    }

    public User toEntity(UserDtos.Create dto) {
        var role = dto.role() != null ? dto.role() : Role.EMPLOYEE;
        var password = passwordProvider.hash(dto.password());

        return new User(dto.name(), dto.email(), password, role);
    }
}