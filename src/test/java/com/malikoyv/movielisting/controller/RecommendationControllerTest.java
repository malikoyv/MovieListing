package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Review;
import com.malikoyv.movielisting.service.RecommendationService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RecommendationController.class)
public class RecommendationControllerTest {
    @MockBean
    private RecommendationService service;
    @MockBean
    private JwtService jwtService;
    @InjectMocks
    private RecommendationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRecommendation_success() {
        List<Movie> movies = new ArrayList<>(List.of(
                new Movie(new ObjectId(), "Description 1"),
                new Movie(new ObjectId(), "Description 2")
        ));
        ObjectId id = new ObjectId();
        when(service.recommendMovies(id)).thenReturn(movies);
        ResponseEntity<List<Movie>> response = controller.getRecommendations(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    void getRecommendation_failure() {
        List<Movie> movies = new ArrayList<>();
        ObjectId id = new ObjectId();
        when(service.recommendMovies(id)).thenReturn(movies);
        ResponseEntity<List<Movie>> response = controller.getRecommendations(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

}
