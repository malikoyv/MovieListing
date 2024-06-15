package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Watchlist;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.service.WatchlistService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = WatchlistController.class)
public class WatchlistControllerTest {
    @MockBean
    private WatchlistService service;

    @MockBean
    private JwtService jwtService;

    @InjectMocks
    private WatchlistController controller;

    @MockBean
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createWatchlist_success() {
        Watchlist watchlist = new Watchlist();

        when(service.createWatchlist(watchlist)).thenReturn(watchlist);
        ResponseEntity<Watchlist> response = controller.createWatchlist(watchlist);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(watchlist, response.getBody());
    }

    @Test
    void createWatchlist_failed() {
        Watchlist watchlist = new Watchlist();

        when(service.createWatchlist(watchlist)).thenReturn(null);
        ResponseEntity<Watchlist> response = controller.createWatchlist(watchlist);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAll_success() {
        List<Watchlist> watchlists = new ArrayList<>(List.of(new Watchlist()));

        when(service.getAllWatchlists()).thenReturn(watchlists);
        ResponseEntity<List<Watchlist>> response = controller.getAllWatchlists();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchlists, response.getBody());
    }

    @Test
    void getAll_failed() {
        List<Watchlist> watchlists = new ArrayList<>();

        when(service.getAllWatchlists()).thenReturn(null);
        ResponseEntity<List<Watchlist>> response = controller.getAllWatchlists();

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getMoviesById_success() {
        ObjectId movieId = new ObjectId();
        List<Watchlist> watchlists = new ArrayList<>(List.of(new Watchlist(movieId)));
        Set<Optional<Movie>> movies = new HashSet<>(Set.of(movieRepository.findById(movieId.toString())));

        when(service.getMovies(watchlists.getFirst().getId())).thenReturn(movies);
        ResponseEntity<Set<Optional<Movie>>> response = controller.getMoviesByWatchlistId(watchlists.getFirst().getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movies, response.getBody());
    }

    @Test
    void getMoviesById_failed() {
        ObjectId watchlistId = new ObjectId();
        Set<Optional<Movie>> movies = new HashSet<>();

        when(service.getMovies(watchlistId)).thenReturn(movies);
        ResponseEntity<Set<Optional<Movie>>> response = controller.getMoviesByWatchlistId(watchlistId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addMovie_success() {
        Movie movie = new Movie();
        Watchlist watchlist = new Watchlist(movie.get_id());

        when(service.addMovieToWatchlist(movie.get_id(), watchlist.getId())).thenReturn(watchlist);
        ResponseEntity<Watchlist> response = controller.addMovieToWatchlist(movie.get_id(), watchlist.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchlist, response.getBody());
    }

    @Test
    void addMovie_failed() {
        Movie movie = new Movie();
        Watchlist watchlist = new Watchlist(movie.get_id());

        when(service.addMovieToWatchlist(movie.get_id(), watchlist.getId())).thenReturn(null);
        ResponseEntity<Watchlist> response = controller.addMovieToWatchlist(movie.get_id(), watchlist.getId());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteWatchlist_success() {
        Watchlist watchlist = new Watchlist();

        when(service.deleteWatchlist(watchlist.getId())).thenReturn(true);
        ResponseEntity<HttpStatus> response = controller.deleteWatchlist(watchlist.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteWatchlist_failed() {
        Watchlist watchlist = new Watchlist();

        when(service.deleteWatchlist(watchlist.getId())).thenReturn(false);
        ResponseEntity<HttpStatus> response = controller.deleteWatchlist(watchlist.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteMovie_success() {
        Movie movie = new Movie();
        Watchlist watchlist = new Watchlist(movie.get_id());

        when(service.deleteMovieFromWatchlist(movie.get_id(), watchlist.getId())).thenReturn(watchlist);
        ResponseEntity<Watchlist> response = controller.deleteMovie(movie.get_id(), watchlist.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchlist, response.getBody());
    }

    @Test
    void deleteMovie_failed() {
        Movie movie = new Movie();
        Watchlist watchlist = new Watchlist(movie.get_id());

        when(service.deleteMovieFromWatchlist(movie.get_id(), watchlist.getId())).thenReturn(null);
        ResponseEntity<Watchlist> response = controller.deleteMovie(movie.get_id(), watchlist.getId());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
