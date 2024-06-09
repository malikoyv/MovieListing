package com.malikoyv.movielisting.repos;

import com.malikoyv.movielisting.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MovieRepository extends MongoRepository<Movie, String> {
    Optional<Movie> findByReview(double rating);
    Optional<Movie> findByName(String name);
    Optional<Movie> findByDirector(String director);
}
