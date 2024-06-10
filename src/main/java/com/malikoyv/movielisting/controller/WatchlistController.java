package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Watchlist;
import com.malikoyv.movielisting.service.WatchlistService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Watchlist>> getAllWatchlists() {
        List<Watchlist> watchlist = watchlistService.getAllWatchlists();
        if (watchlist.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(watchlist, HttpStatus.FOUND);
    }

    @GetMapping("/getMovie/{id}")
    public ResponseEntity<Movie> getWatchlistByMovieId(@PathVariable("id") ObjectId watchlistId) {
        Optional<Movie> movie = watchlistService.getMovie(watchlistId);
        return movie.map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addMovie")
    public ResponseEntity<Watchlist> addWatchlist(@RequestBody Watchlist watchlist) {
        return new ResponseEntity<>(watchlistService.addToWatchlist(watchlist), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteWatchlist/{id}")
    public ResponseEntity<Watchlist> deleteWatchlist(@PathVariable("id") ObjectId id) {
        if (watchlistService.deleteMovie(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
