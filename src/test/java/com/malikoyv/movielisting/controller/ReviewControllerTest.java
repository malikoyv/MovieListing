package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.Review;
import com.malikoyv.movielisting.service.ReviewService;
import com.malikoyv.movielisting.service.UserService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ReviewController.class)
public class ReviewControllerTest {
    @MockBean
    private ReviewService reviewService;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getByMovieId_success() {
        List<Review> reviews = new ArrayList<>(List.of(
                new Review(new ObjectId()) // instantiate movieId
        ));

        when(reviewService.getByMovieId(reviews.getFirst().getMovieId())).thenReturn(Optional.of(reviews));
        ResponseEntity<List<Review>> response = reviewController.getByMovie(reviews.getFirst().getMovieId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
    }

    @Test
    void getByMovieId_failed(){
        ObjectId objectId = new ObjectId();
        when(reviewService.getByMovieId(objectId)).thenReturn(Optional.empty());
        ResponseEntity<List<Review>> response = reviewController.getByMovie(objectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void getByAuthorId_success(){
        List<Review> reviews = new ArrayList<>(List.of(
                new Review(new ObjectId(), 5.0)
        ));

        when(reviewService.getByAuthorId(reviews.getFirst().getAuthorId())).thenReturn(Optional.of(reviews));
        ResponseEntity<List<Review>> response = reviewController.getByAuthor(reviews.getFirst().getAuthorId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
    }

    @Test
    void getByAuthorId_failed(){
        ObjectId objectId = new ObjectId();
        when(reviewService.getByAuthorId(objectId)).thenReturn(Optional.empty());
        ResponseEntity<List<Review>> response = reviewController.getByAuthor(objectId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void addReview_success(){
        Review review = new Review(new ObjectId(), 9.1);

        when(reviewService.addReview(review)).thenReturn(review);
        ResponseEntity<Review> response = reviewController.createReview(review);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(review, response.getBody());
    }

    @Test
    void addReview_failed(){
        Review review = new Review(new ObjectId());

        when(reviewService.addReview(review)).thenReturn(null);
        ResponseEntity<Review> response = reviewController.createReview(review);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void updateReview_success() {
        Review review = new Review(new ObjectId());
        Review updated = new Review(review.get_id(), "perfect");

        when(reviewService.updateReview(review.get_id(), "perfect")).thenReturn(updated);
        ResponseEntity<Review> response = reviewController.updateReview(updated.get_id(), "perfect");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void updateReview_failed() {
        Review review = new Review(new ObjectId());

        when(reviewService.updateReview(review.get_id(), "perfect")).thenReturn(null);
        ResponseEntity<Review> response = reviewController.updateReview(review.get_id(), "perfect");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void deleteReview_success(){
        Review review = new Review(new ObjectId());

        when(reviewService.deleteReview(review.get_id())).thenReturn(true);
        ResponseEntity<HttpStatus> response = reviewController.deleteReview(review.get_id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteReview_failed(){
        Review review = new Review(new ObjectId());

        when(reviewService.deleteReview(review.get_id())).thenReturn(false);
        ResponseEntity<HttpStatus> response = reviewController.deleteReview(review.get_id());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
