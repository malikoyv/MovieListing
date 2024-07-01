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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
    private Movie movie;
    private ObjectId movieId;
    private ObjectId authorId;
    private final ObjectId reviewId = new ObjectId();

    @BeforeEach
    void setUp() {
        movieId = new ObjectId();
        authorId = new ObjectId();
        review = new Review();
        review.set_id(reviewId);
        review.setMovieId(movieId);
        review.setAuthorId(authorId);
        review.setRating(8);

        movie = new Movie();
        movie.set_id(movieId);
        movie.setReviewIds(new ArrayList<>());
    }

    @Test
    void addReview_Success() {
        when(movieRepository.existsById(movieId.toString())).thenReturn(true);
        when(reviewRepository.findByAuthorIdAndMovieId(authorId, movieId)).thenReturn(Optional.empty());
        when(movieRepository.findById(movieId.toString())).thenReturn(Optional.of(movie));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review result = reviewService.addReview(review);

        assertNotNull(result);
        verify(movieRepository).save(movie);
        verify(reviewRepository).save(review);
        assertTrue(movie.getReviewIds().contains(review.get_id()));
    }

    @Test
    void addReview_Failure_MovieNotExist() {
        when(movieRepository.existsById(movieId.toString())).thenReturn(false);

        Review result = reviewService.addReview(review);

        assertNull(result);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void getByMovieId_Success() {
        List<Review> reviews = List.of(review);
        when(reviewRepository.findByMovieId(movieId)).thenReturn(Optional.of(reviews));

        Optional<List<Review>> result = reviewService.getByMovieId(movieId);

        assertTrue(result.isPresent());
        assertEquals(reviews, result.get());
    }

    @Test
    void getByAuthorId_Success() {
        List<Review> reviews = List.of(review);
        when(reviewRepository.findByAuthorId(authorId)).thenReturn(Optional.of(reviews));

        Optional<List<Review>> result = reviewService.getByAuthorId(authorId);

        assertTrue(result.isPresent());
        assertEquals(reviews, result.get());
    }

    @Test
    void updateReview_Success() {
        String newDescription = "Updated description";
        review.setDescription("Old description");
        when(reviewRepository.findById(String.valueOf(reviewId))).thenReturn(Optional.ofNullable(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review result = reviewService.updateReview(reviewId, newDescription);

        assertNotNull(result);
        assertEquals(newDescription, result.getDescription());
        verify(reviewRepository).save(review);
    }

    @Test
    void updateReview_Failure_ReviewNotFound() {
        when(reviewRepository.findById(any())).thenReturn(Optional.empty());

        Review result = reviewService.updateReview(new ObjectId(), "New description");

        assertNull(result);
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    void deleteReview_Success() {
        when(reviewRepository.findById(reviewId.toString())).thenReturn(Optional.of(review));
        when(movieRepository.findById(movieId.toString())).thenReturn(Optional.of(movie));

        boolean result = reviewService.deleteReview(reviewId);

        assertTrue(result);
        verify(reviewRepository).deleteById(reviewId.toString());
        verify(movieRepository).findById(movieId.toString());
        assertFalse(movie.getReviewIds().contains(reviewId));
    }

    @Test
    void deleteReview_Failure_ReviewNotFound() {
        when(reviewRepository.findById(any())).thenReturn(Optional.empty());

        boolean result = reviewService.deleteReview(new ObjectId());

        assertFalse(result);
        verify(reviewRepository, never()).deleteById(any());
        verify(movieRepository, never()).save(any());
    }
}