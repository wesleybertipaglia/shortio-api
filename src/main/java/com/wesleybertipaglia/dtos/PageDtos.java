package com.wesleybertipaglia.dtos;

import java.util.List;

public class PageDtos {
    public record Pagination(int page, int size) {
    }

    public record Result<T>(List<T> items, Pagination pagination) {
    }
}
