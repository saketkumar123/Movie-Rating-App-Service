package com.chaiaurjava.movie_catalog_service.services;

import com.chaiaurjava.movie_catalog_service.models.Rating;
import com.chaiaurjava.movie_catalog_service.models.UserRating;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class UserRatingInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "backendService", fallbackMethod = "getUserRatingFallback")
    @Bulkhead(name = "backendService", type = Bulkhead.Type.SEMAPHORE)
    public UserRating getUserRating(String userId){
        return restTemplate.getForObject("http://MOVIE-RATING-DATA-SERVICE/ratingsdata/users/"+userId, UserRating.class);
    }

    public UserRating getUserRatingFallback(String userId, Throwable th){
        UserRating userRating = new UserRating();
        List<Rating> ratings = Arrays.asList(new Rating(userId, 0));
        userRating.setRatings(ratings);
        return userRating;
    }
}
