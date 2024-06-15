package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.controller.WatchlistController;
import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Watchlist;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.repos.WatchlistRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository watchlistRepository;
    @Autowired
    private MovieRepository movieRepository;

    public Watchlist createWatchlist(Watchlist watchlist) {
        return watchlistRepository.save(watchlist);
    }

    public List<Watchlist> getAllWatchlists() {
        return watchlistRepository.findAll();
    }

    public Set<Optional<Movie>> getMovies (ObjectId watchlistId) {
        Optional<Watchlist> watchlist = watchlistRepository.findById(watchlistId.toString());
        Set<Optional<Movie>> result = new HashSet<>();
        if (watchlist.isPresent()) {
            for (ObjectId m : watchlist.get().getMovieId()) {
                result.add(movieRepository.findById(m.toString()));
            }
            return result;
        }
        return result;
    }

    public Watchlist addMovieToWatchlist(ObjectId movieId, ObjectId watchlistId) {
        Optional<Watchlist> watchlist = watchlistRepository.findById(watchlistId.toString());
        if (watchlist.isPresent()) {
            Optional<Movie> movie = movieRepository.findById(movieId.toString());
            if (movie.isPresent()) {
                watchlist.get().getMovieId().add(movieId);
                return watchlistRepository.save(watchlist.get());
            }
        }
        return null;
    }

    public boolean deleteWatchlist(ObjectId id) {
        Optional<Watchlist> watchlist = watchlistRepository.findById(id.toString());
        if (watchlist.isPresent()){
            watchlistRepository.delete(watchlist.get());
            return true;
        }
        return false;
    }

    public Watchlist deleteMovieFromWatchlist(ObjectId movieId, ObjectId watchlistId) {
        Watchlist watchlist = watchlistRepository.findById(watchlistId.toString()).orElse(null);
        if (watchlist == null) {
            return watchlist;
        }
        watchlist.getMovieId().remove(movieId);
        return watchlistRepository.save(watchlist);
    }
}
