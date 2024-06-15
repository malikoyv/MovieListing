package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Review;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.repos.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> recommendMovies(ObjectId userId) {
        Optional<List<Review>> userReviews = reviewRepository.findByAuthorId(userId);
        if (userReviews.isEmpty()) {
            return Collections.emptyList();
        }

        Map<ObjectId, Double> movieRating = new HashMap<>();

        for (Review review : userReviews.get()) {
            List<Review> similarReviews = reviewRepository.findByMovieId(review.getMovieId()).orElse(Collections.emptyList());
            for (Review similarReview : similarReviews) {
                if (!similarReview.getAuthorId().equals(userId)){
                    movieRating.merge(similarReview.getMovieId(), similarReview.getRating(), Double::sum);
                }
            }
        }

        List<ObjectId> recommendedMovieIdsObject = movieRating.entrySet().stream()
                .sorted(Map.Entry.<ObjectId, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();

        List<String> recommendedMovieIdStrings = recommendedMovieIdsObject.stream()
                .map(ObjectId::toString)
                .toList();

        return movieRepository.findAllById(recommendedMovieIdStrings);
    }
}
