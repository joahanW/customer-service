package com.johanwork.dto;

import com.johanwork.domain.Genre;

import java.util.List;

public record CustomerDto(Long id,
                          String name,
                          Genre favoriteGenre,
                          List<MovieDto> recommendedMovies) {
}
