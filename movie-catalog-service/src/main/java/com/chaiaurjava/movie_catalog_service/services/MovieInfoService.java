package com.chaiaurjava.movie_catalog_service.services;

import com.chaiaurjava.movie_catalog_service.models.CatalogItem;
import com.chaiaurjava.movie_catalog_service.models.Movie;
import com.chaiaurjava.movie_catalog_service.models.Rating;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    @Autowired
    private RestTemplate restTemplate;
    @CircuitBreaker(name = "backendService", fallbackMethod = "getMovieCatalogFallback")
    @Bulkhead(name = "backendService", type = Bulkhead.Type.SEMAPHORE)
    public CatalogItem getMovieCatalog(Rating rating){
        Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/"+ rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getMovieCatalogFallback(Rating rating, Throwable th){
        return new CatalogItem("Movie not found", "", rating.getRating());
    }
}
