package com.wesleybertipaglia.providers;

import io.quarkus.elytron.security.common.BcryptUtil;

import jakarta.inject.Singleton;

@Singleton
public class PasswordProvider {
    public String hash(String rawPassword) {
        return BcryptUtil.bcryptHash(rawPassword);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return BcryptUtil.matches(rawPassword, hashedPassword);
    }
}
