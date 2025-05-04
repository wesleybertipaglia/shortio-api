package com.wesleybertipaglia.mappers;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.User;
import com.wesleybertipaglia.enums.Role;
import com.wesleybertipaglia.providers.JwtProvider;
import com.wesleybertipaglia.providers.PasswordProvider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthMapper {
    @Inject
    JwtProvider jwtProvider;

    @Inject
    PasswordProvider passwordProvider;

    @Inject
    UserMapper userMapper;

    public AuthDtos.Response toResponseDto(User user) {
        var token = jwtProvider.generateToken(user);
        return new AuthDtos.Response(token, userMapper.toDetailsDto(user));
    }

    public User toEntity(AuthDtos.SignUp dto) {
        return new User(dto.name(), dto.email(), passwordProvider.hash(dto.password()), Role.OWNER);
    }
}