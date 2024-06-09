package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.model.Review;
import com.malikoyv.movielisting.service.ReviewService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/getByMovie/{id}")
    public ResponseEntity<Optional<List<Review>>> getByMovie(@PathVariable("id") ObjectId movieId) {
        if (reviewService.getByMovieId(movieId).isPresent()) {
            return new ResponseEntity<>(reviewService.getByMovieId(movieId), HttpStatus.OK);
        }
        return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByAuthor/{id}")
    public ResponseEntity<Optional<List<Review>>> getByAuthor(@PathVariable("id") ObjectId authorId) {
        if (reviewService.getByAuthorId(authorId).isPresent()) {
            return new ResponseEntity<>(reviewService.getByAuthorId(authorId), HttpStatus.OK);
        }
        return new ResponseEntity<>(Optional.empty(), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addReview")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        if (reviewService.addReview(review) != null){
            return new ResponseEntity<>(reviewService.addReview(review), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/updateReview/{id}/{description}")
    public ResponseEntity<Review> updateReview(@PathVariable ObjectId id, @PathVariable String description) {
        if (reviewService.updateReview(id, description) != null) {
            return new ResponseEntity<>(reviewService.updateReview(id, description), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteReview/{id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable ObjectId id) {
        return reviewService.deleteReview(id);
    }
}
