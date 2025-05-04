package com.wesleybertipaglia.mappers;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.User;
import com.wesleybertipaglia.enums.Role;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {
    public UserDtos.Summary toSummaryDto(User user) {
        return new UserDtos.Summary(user.id, user.name, user.email, user.role);
    }

    public UserDtos.Details toDetailsDto(User user) {
        return new UserDtos.Details(user.id, user.name, user.email, user.role);
    }

    public User toEntity(UserDtos.Create dto) {
        return new User(dto.name(), dto.email(), dto.password(), dto.role() != null ? dto.role() : Role.EMPLOYEE);
    }
}