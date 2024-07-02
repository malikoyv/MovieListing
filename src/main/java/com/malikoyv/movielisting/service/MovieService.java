package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.repos.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (isMovieValid(movie)){
            movie.setReviewIds(List.of());
            return movieRepository.save(movie);
        }
        return null;
    }

    public void updateMovie(ObjectId id, Movie movie) {
        Optional<Movie> movieToUpdate = movieRepository.findById(id.toString());
        if (movieToUpdate.isPresent()){
            if (movie.getName() != null){
                movieToUpdate.get().setName(movie.getName());
            } else if (movie.getDirector() != null){
                movieToUpdate.get().setDirector(movie.getDirector());
            } else if (movie.getYear() > 0 && movie.getYear() <= LocalDate.now().getYear()){
                movieToUpdate.get().setYear(movie.getYear());
            } else if (movie.getGenre() != null){
                movieToUpdate.get().setGenre(movie.getGenre());
            }
            movieRepository.save(movieToUpdate.get());
        }
    }

    public boolean deleteMovie(ObjectId id) {
        if (movieRepository.existsById(id.toString())) {
            movieRepository.deleteById(id.toString());
            return true;
        }
        return false;
    }

    private boolean isMovieValid(Movie movie) {
        String movieName = movie.getName();
        String movieDirector = movie.getDirector();
        List<String> movieGenre = movie.getGenre();

        return movieName != null && !movieName.isEmpty() &&
                movieDirector != null && !movieDirector.isEmpty() &&
                movie.getYear() > 1895 && movie.getYear() < LocalDate.now().getYear() &&
                movieGenre != null && !movieGenre.isEmpty();
    }
}
