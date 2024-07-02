package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Review;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.repos.ReviewRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    private ObjectId userId;
    private ObjectId movieId1;
    private ObjectId movieId2;
    private ObjectId movieId3;

    @BeforeEach
    void setUp() {
        userId = new ObjectId();
        movieId1 = new ObjectId();
        movieId2 = new ObjectId();
        movieId3 = new ObjectId();
    }

    @Test
    void recommendMovies_Success() {
        Review userReview1 = new Review();
        userReview1.setAuthorId(userId);
        userReview1.setMovieId(movieId1);

        Review userReview2 = new Review();
        userReview2.setAuthorId(userId);
        userReview2.setMovieId(movieId2);

        Review otherReview1 = new Review();
        otherReview1.setAuthorId(new ObjectId());
        otherReview1.setMovieId(movieId3);
        otherReview1.setRating(8.0);

        Review otherReview2 = new Review();
        otherReview2.setAuthorId(new ObjectId());
        otherReview2.setMovieId(movieId3);
        otherReview2.setRating(9.0);

        Movie movie1 = new Movie();
        movie1.set_id(movieId1);

        Movie movie2 = new Movie();
        movie2.set_id(movieId2);

        Movie movie3 = new Movie();
        movie3.set_id(movieId3);

        when(reviewRepository.findByAuthorId(userId)).thenReturn(Optional.of(Arrays.asList(userReview1, userReview2)));
        when(reviewRepository.findByMovieId(movieId1)).thenReturn(Optional.of(Arrays.asList(userReview1, otherReview1)));
        when(reviewRepository.findByMovieId(movieId2)).thenReturn(Optional.of(Arrays.asList(userReview2, otherReview2)));
        when(movieRepository.findAllById(List.of(movieId3.toString()))).thenReturn(List.of(movie3));

        List<Movie> recommendedMovies = recommendationService.recommendMovies(userId);

        assertEquals(1, recommendedMovies.size());
        assertEquals(movieId3, recommendedMovies.getFirst().get_id());
    }

    @Test
    void recommendMovies_failed(){
        when(reviewRepository.findByAuthorId(userId)).thenReturn(Optional.empty());
        List<Movie> recommendedMovies = recommendationService.recommendMovies(userId);
        assertEquals(0, recommendedMovies.size());
    }
}