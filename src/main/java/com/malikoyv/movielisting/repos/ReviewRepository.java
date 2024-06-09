package com.malikoyv.movielisting.repos;

import com.malikoyv.movielisting.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<Review> findByAuthorId(ObjectId authorId);
    Optional<Review> findByMovieId(ObjectId movieId);
}
