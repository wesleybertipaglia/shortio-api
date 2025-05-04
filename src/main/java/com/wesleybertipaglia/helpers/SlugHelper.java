package com.wesleybertipaglia.helpers;

import java.util.UUID;

import jakarta.inject.Singleton;

@Singleton
public class SlugHelper {
    public static String generateRandomSlug(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}
