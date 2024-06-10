package com.malikoyv.movielisting.repos;

import com.malikoyv.movielisting.model.Watchlist;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WatchlistRepository extends MongoRepository<Watchlist, String> {

}
