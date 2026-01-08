package com.chaiaurjava.movie_rating_data_service.controller;

import com.chaiaurjava.movie_rating_data_service.models.Rating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingController {

    @GetMapping("/{movieId}")
    private Rating getRating(@PathVariable String movieId){
        return new Rating(movieId, 4);
    }
}
