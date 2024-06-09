package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Review;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.repos.ReviewRepository;
import com.malikoyv.movielisting.repos.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UserRepository userRepository;

    public Review addReview(Review review) {
        Optional<Review> existingReview = reviewRepository.findByAuthorIdAndMovieId(review.getAuthorId(), review.getMovieId());
        if (movieRepository.existsById(review.getMovieId().toString())
                && review.getRating() > 0 && review.getRating() <= 10 && existingReview.isEmpty()){
            return reviewRepository.save(review);
        }

        return null;
    }

    public Optional<List<Review>> getByMovieId(ObjectId movieId) {
        if (movieRepository.existsById(movieId.toString())){
            return reviewRepository.findByMovieId(movieId);
        }
        return Optional.empty();
    }

    public Optional<List<Review>> getByAuthorId(ObjectId authorId) {
        if (userRepository.findById(authorId.toString()).isPresent()){
            return reviewRepository.findByAuthorId(authorId);
        }
        return Optional.empty();
    }

    public Review updateReview(ObjectId id, String description) {
        Optional<Review> review = reviewRepository.findById(id.toString());
        if (review.isPresent()){
            review.get().setDescription(description);
            return reviewRepository.save(review.get());
        }
        return null;
    }

    public ResponseEntity<HttpStatus> deleteReview(ObjectId id){
        if (reviewRepository.existsById(id.toString())){
            reviewRepository.deleteById(id.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
