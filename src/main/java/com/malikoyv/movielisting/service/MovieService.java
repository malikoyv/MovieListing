package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.repos.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getById(ObjectId id) {
        return movieRepository.findById(id.toString());
    }

    public Optional<Movie> getByName(String name) {
        return movieRepository.findByName(name);
    }

    public Optional<Movie> getByDirector(String director) {
        return movieRepository.findByDirector(director);
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public boolean isMovieValid(Movie movie) {
        return !movie.getName().isEmpty() &&
                !movie.getDirector().isEmpty() &&
                movie.getYear() > 1895 && movie.getYear() < LocalDate.now().getYear() &&
                !movie.getGenre().isEmpty();
    }

    public ResponseEntity<Movie> deleteMovie(ObjectId id) {
        if (movieRepository.existsById(id.toString())) {
            movieRepository.deleteById(id.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
