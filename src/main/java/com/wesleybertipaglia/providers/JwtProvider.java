package com.wesleybertipaglia.providers;

import io.smallrye.jwt.build.Jwt;

import java.time.Instant;
import java.util.Set;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.wesleybertipaglia.dtos.AuthDtos;
import com.wesleybertipaglia.entities.User;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
public class JwtProvider {
    @Inject
    @ConfigProperty(name = "smallrye.jwt.new-token.issuer")
    String issuer;

    @Inject
    @ConfigProperty(name = "smallrye.jwt.new-token.lifespan")
    Long expiration;

    public AuthDtos.Token generateToken(User user) {
        var expiresAt = Instant.now().plusSeconds(expiration);

        var token = Jwt.issuer(issuer).subject(user.id.toString())
                .groups(Set.of(user.role.toString())).expiresAt(expiresAt).sign();

        return new AuthDtos.Token("Bearer", token, expiresAt);
    }
}
