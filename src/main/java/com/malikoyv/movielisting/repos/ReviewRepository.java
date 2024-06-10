package com.malikoyv.movielisting.repos;

import com.malikoyv.movielisting.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    Optional<List<Review>> findByAuthorId(ObjectId authorId);
    Optional<List<Review>> findByMovieId(ObjectId movieId);
    Optional<Review> findByAuthorIdAndMovieId(ObjectId authorId, ObjectId movieId);
}
