package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.service.MovieService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequestMapping("/movies")
@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Movie>> getAllMovies() {
        try {
            return new ResponseEntity<>(movieService.getAll(), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Optional<Movie>> getMovieByID(@PathVariable("id") ObjectId id) {
        try {
            return new ResponseEntity<>(movieService.getById(id), HttpStatus.OK);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByReview/{review}")
    public ResponseEntity<Optional<Movie>> getMovieByReview(@PathVariable("review") double review) {
        try {
            return new ResponseEntity<>(movieService.getByReview(review), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Optional<Movie>> getMovieByName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(movieService.getByName(name), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByDirector/{director}")
    public ResponseEntity<Optional<Movie>> getMovieByDirector(@PathVariable("director") String director) {
        try {
            return new ResponseEntity<>(movieService.getByDirector(director), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMovie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        if (movieService.isMovieValid(movie)){
            return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateReview/{id}/{value}")
    public ResponseEntity<Movie> updateReview(@PathVariable ObjectId id, @PathVariable double value) {
        if (movieService.updateReview(id, value) != null){
            return new ResponseEntity<>(movieService.updateReview(id, value), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable ObjectId id) {
        return movieService.deleteMovie(id);
    }
}