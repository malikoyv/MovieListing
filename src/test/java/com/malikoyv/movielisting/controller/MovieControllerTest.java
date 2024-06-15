package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.service.MovieService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = MovieController.class)
public class MovieControllerTest {
    @MockBean
    private MovieService movieService;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMovies() {
        List<Movie> movies = new ArrayList<>(List.of(new Movie(new ObjectId(), "movie")));

        when(movieService.getAll()).thenReturn(movies);
        ResponseEntity<List<Movie>> response = movieController.getAllMovies();

        assertEquals(movies, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllMovies_notFound(){
        List<Movie> movies = new ArrayList<>();
        when(movieService.getAll()).thenReturn(movies);
        ResponseEntity<List<Movie>> response = movieController.getAllMovies();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void getMovieById() {
        Movie movie = new Movie(new ObjectId(), "movie");

        when(movieService.getById(movie.get_id())).thenReturn(Optional.of(movie));
        ResponseEntity<Movie> response = movieController.getMovieByID(movie.get_id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
    }

    @Test
    void getMovieById_notFound(){
        Movie movie = new Movie(new ObjectId(), "movie");

        when(movieService.getById(movie.get_id())).thenReturn(Optional.empty());
        ResponseEntity<Movie> response = movieController.getMovieByID(movie.get_id());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void getMovieByName(){
        Movie movie = new Movie(new ObjectId(), "movie");

        when(movieService.getByName(movie.getName())).thenReturn(Optional.of(movie));
        ResponseEntity<Movie> response = movieController.getMovieByName(movie.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
    }

    @Test
    void getMovieByName_notFound(){
        when(movieService.getByName("name")).thenReturn(Optional.empty());
        ResponseEntity<Movie> response = movieController.getMovieByName("name");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void getMovieByDirector(){
        Movie movie = new Movie("director");

        when(movieService.getByDirector("director")).thenReturn(Optional.of(movie));
        ResponseEntity<Movie> response = movieController.getMovieByDirector("director");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movie, response.getBody());
    }

    @Test
    void getMovieByDirector_notFound(){
        when(movieService.getByDirector("director")).thenReturn(Optional.empty());
        ResponseEntity<Movie> response = movieController.getMovieByDirector("director");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void addMovie_success(){
        Movie movie = new Movie(new ObjectId(), "movie");

        when(movieService.addMovie(movie)).thenReturn(movie);
        ResponseEntity<Movie> response = movieController.addMovie(movie);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movie, response.getBody());
    }

    @Test
    void addMovie_failed(){
        when(movieService.addMovie(new Movie("director"))).thenReturn(null);
        ResponseEntity<Movie> response = movieController.addMovie(new Movie("director"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void deleteMovie_success(){
        Movie movie = new Movie(new ObjectId(), "movie");

        when(movieService.deleteMovie(movie.get_id())).thenReturn(true);
        ResponseEntity<Movie> response = movieController.deleteMovie(movie.get_id());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteMovie_notFound(){
        when(movieService.deleteMovie(new ObjectId())).thenReturn(false);
        ResponseEntity<Movie> response = movieController.deleteMovie(new ObjectId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
