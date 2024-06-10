package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Watchlist;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.repos.WatchlistRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository watchlistRepository;
    @Autowired
    private MovieRepository movieRepository;

    public List<Watchlist> getAllWatchlists() {
        return watchlistRepository.findAll();
    }

    public Optional<Movie> getMovie (ObjectId watchlistId) {
        Optional<Watchlist> watchlist = watchlistRepository.findById(watchlistId.toString());
        return movieRepository.findById(String.valueOf(watchlist.get().getMovieId()));
    }

    public Watchlist addToWatchlist(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public boolean deleteMovie(ObjectId id) {
        Optional<Watchlist> watchlist = watchlistRepository.findById(id.toString());
        if (watchlist.isPresent()){
            watchlistRepository.delete(watchlist.get());
            return true;
        }
        return false;
    }
}
