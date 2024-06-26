package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Review;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.repos.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;

    public Review addReview(Review review) {
        review.set_id(new ObjectId());
        Optional<Review> existingReview = reviewRepository.findByAuthorIdAndMovieId(review.getAuthorId(), review.getMovieId());
        Optional<Movie> movie = movieRepository.findById(review.getMovieId().toString());
        if (movieRepository.existsById(review.getMovieId().toString())
                && review.getRating() > 0 && review.getRating() <= 10 && existingReview.isEmpty()
                && movie.isPresent()) {
            movie.get().getReviewIds().add(review.get_id());
            movieRepository.save(movie.get());
            return reviewRepository.save(review);
        }
        return null;
    }

    public Optional<List<Review>> getByMovieId(ObjectId movieId) {
        return reviewRepository.findByMovieId(movieId);
    }

    public Optional<List<Review>> getByAuthorId(ObjectId authorId) {
        return reviewRepository.findByAuthorId(authorId);
    }

    public Review updateReview(ObjectId id, String description) {
        Optional<Review> review = reviewRepository.findById(id.toString());
        if (review.isPresent()){
            review.get().setDescription(description);
            return reviewRepository.save(review.get());
        }
        return null;
    }

    public boolean deleteReview(ObjectId id){
        Review review = reviewRepository.findById(id.toString()).orElse(null);
        if (review != null){
            movieRepository.findById(review.getMovieId().toString()).ifPresent(movie -> movie.getReviewIds().remove(id));
            reviewRepository.deleteById(id.toString());
            return true;
        }
        return false;
    }
}