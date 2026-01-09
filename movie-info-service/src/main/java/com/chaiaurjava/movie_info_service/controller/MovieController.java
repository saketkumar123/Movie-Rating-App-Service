package com.chaiaurjava.movie_info_service.controller;

import com.chaiaurjava.movie_info_service.models.Movie;
import com.chaiaurjava.movie_info_service.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable String movieId){

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<MovieSummary> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/movie/"+movieId,
                HttpMethod.GET,
                entity,
                MovieSummary.class
        );

        MovieSummary movieSummary = response.getBody();

        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
    }
}
