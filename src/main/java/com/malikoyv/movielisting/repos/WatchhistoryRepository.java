package com.malikoyv.movielisting.repos;

import com.malikoyv.movielisting.model.Watchhistory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WatchhistoryRepository extends MongoRepository<Watchhistory, String> {
    List<Watchhistory> findByUserId(ObjectId userId);
}
