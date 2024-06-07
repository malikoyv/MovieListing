package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.repos.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/movies")
@RestController
public class MovieController {
    @Autowired
    private MovieRepository repo;

    @GetMapping("/getAll")
    public List<Movie> getAllMovies(){
        return repo.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Movie> getMovieByID(@PathVariable("id") ObjectId id) throws Exception {
        try {
            return repo.findById(id.toString());

        } catch (Exception e) {
            throw new Exception("Error in getMovieByID");
        }
    }
}