package com.chaiaurjava.movie_catalog_service.controller;

import com.chaiaurjava.movie_catalog_service.models.CatalogItem;
import com.chaiaurjava.movie_catalog_service.models.Movie;
import com.chaiaurjava.movie_catalog_service.models.Rating;
import com.chaiaurjava.movie_catalog_service.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    public MovieCatalogController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId){



        // get All rated movie Ids
        UserRating ratings = restTemplate.getForObject("http://localhost:8082/ratingsdata/users/"+userId, UserRating.class);


        return ratings.getRatings(). stream().map(rating -> {
                // For each moview Id, call movie info service and get details
               Movie movie = restTemplate.getForObject("http://localhost:8081/movies/"+ rating.getMovieId(), Movie.class);

            /*Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/movies/"+ rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/

            // Put them all together
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());





    }
}
