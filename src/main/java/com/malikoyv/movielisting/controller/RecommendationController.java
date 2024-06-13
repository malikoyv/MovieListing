package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.service.RecommendationService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/getRecommendation/{userId}")
    public ResponseEntity<List<Movie>> getRecommendations(@PathVariable("userId") ObjectId id) {
        List<Movie> recommendations = recommendationService.recommendMovies(id);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }
}
