package com.wesleybertipaglia.services;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.mappers.UserMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@ApplicationScoped
public class ProfileService {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Inject
    public ProfileService(AuthService authService,
            UserService userService,
            UserMapper userMapper) {
        this.authService = authService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public UserDtos.Details get() {
        final var user = authService.getCurrentUser();
        return userMapper.toDetailsDto(user);
    }

    @Transactional
    public UserDtos.Details update(@Valid UserDtos.Update dto) {
        final var user = authService.getCurrentUser();
        final var updatedUser = userService.update(user.id, dto);
        return userMapper.toDetailsDto(updatedUser);
    }

    @Transactional
    public void delete() {
        final var user = authService.getCurrentUser();
        userService.delete(user.id);
    }
}
