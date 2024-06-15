package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Watchlist;
import com.malikoyv.movielisting.service.WatchlistService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;

    @PostMapping("/createWatchlist")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Watchlist> createWatchlist(@RequestBody Watchlist watchlist) {
        Watchlist newWatchlist = watchlistService.createWatchlist(watchlist);
        if (newWatchlist == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newWatchlist, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Watchlist>> getAllWatchlists() {
        List<Watchlist> watchlist = watchlistService.getAllWatchlists();
        if (watchlist == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(watchlist, HttpStatus.FOUND);
    }

    @GetMapping("/getMovies/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Set<Optional<Movie>>> getMoviesByWatchlistId(@PathVariable("id") ObjectId watchlistId) {
        Set<Optional<Movie>> movies = watchlistService.getMovies(watchlistId);
        if (movies.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping("/addMovie/{movieId}/{watchlistId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Watchlist> addMovieToWatchlist(@PathVariable("movieId") ObjectId movieId, @PathVariable("watchlistId") ObjectId watchlistId) {
        Watchlist newWatchlist = watchlistService.addMovieToWatchlist(movieId, watchlistId);
        if (newWatchlist == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newWatchlist, HttpStatus.OK);
    }

    @DeleteMapping("/deleteWatchlist/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteWatchlist(@PathVariable("id") ObjectId id) {
        if (watchlistService.deleteWatchlist(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteMovieFromWatchlist/{movieId}/{watchlistId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Watchlist> deleteMovie(@PathVariable("movieId") ObjectId movieId, @PathVariable("watchlistId") ObjectId watchlistId) {
        Watchlist watchlist = watchlistService.deleteMovieFromWatchlist(movieId, watchlistId);
        if (watchlist == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(watchlist, HttpStatus.OK);
    }
}
