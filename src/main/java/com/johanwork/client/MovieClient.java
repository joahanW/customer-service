package com.johanwork.client;

import com.johanwork.domain.Genre;
import com.johanwork.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieClient {

    private final RestClient restClient;

    public List<MovieDto> getMovies(Genre genre){
        log.info("genre: {}", genre);
        List<MovieDto> res = this.restClient.get()
                .uri("/api/movies/{genre}", genre)
                .retrieve()
                .body(new ParameterizedTypeReference<List<MovieDto>>() {
                });
        log.info("received movies : {}", res);
        return res;
    }

}
