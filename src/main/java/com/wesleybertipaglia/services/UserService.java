package com.wesleybertipaglia.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.entities.User;
import com.wesleybertipaglia.mappers.UserMapper;
import com.wesleybertipaglia.repositories.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String EMAIL_ALREADY_IN_USE = "Email already in use";

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Inject
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDtos.Summary> list(Integer page, Integer size) {
        return userRepository.findAll()
                .page(page, size)
                .stream()
                .map(userMapper::toSummaryDto)
                .toList();
    }

    public Optional<User> getByIdOptional(ObjectId id) {
        return userRepository.findByIdOptional(id);
    }

    public User getById(ObjectId id) {
        return getByIdOptional(id)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public Optional<User> getByEmailOptional(String email) {
        return userRepository.findByEmailOptional(email);
    }

    public User getByEmail(String email) {
        return getByEmailOptional(email)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public UserDtos.Details getDetailsById(ObjectId id) {
        return userMapper.toDetailsDto(getById(id));
    }

    @Transactional
    public User create(UserDtos.Create dto) {
        validateEmailUniqueness(dto.email());
        var user = userMapper.toEntity(dto);
        userRepository.persist(user);
        return user;
    }

    @Transactional
    public User update(ObjectId id, @Valid UserDtos.Update dto) {
        final User user = getById(id);

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

        return user;
    }

    @Transactional
    public void delete(ObjectId id) {
        final User user = getById(id);
        userRepository.delete(user);
    }

    private void validateEmailUniqueness(String email) {
        if (getByEmailOptional(email).isPresent()) {
            throw new IllegalArgumentException(EMAIL_ALREADY_IN_USE);
        }
    }
}
