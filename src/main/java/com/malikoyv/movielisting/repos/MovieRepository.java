package com.malikoyv.movielisting.repos;

import com.malikoyv.movielisting.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {

}
