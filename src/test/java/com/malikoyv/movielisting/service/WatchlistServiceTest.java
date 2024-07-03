package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.model.Watchlist;
import com.malikoyv.movielisting.repos.MovieRepository;
import com.malikoyv.movielisting.repos.WatchlistRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WatchlistServiceTest {
    @Mock
    private WatchlistRepository watchlistRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private WatchlistService watchlistService;

    private Movie movie;
    private final ObjectId movieId = new ObjectId();
    private final ObjectId watchlistId = new ObjectId();
    private Watchlist watchlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie = new Movie(movieId, "movie");

        watchlist = new Watchlist(watchlistId, Set.of(movieId));
    }

    @Test
    void createWatchlist_success(){
        when(watchlistRepository.save(watchlist)).thenReturn(watchlist);
        Watchlist result = watchlistService.createWatchlist(watchlist);

        assertNotNull(result);
        assertEquals(watchlist, result);
    }

    @Test
    void createWatchlist_failed(){
        when(watchlistRepository.save(watchlist)).thenReturn(null);
        Watchlist result = watchlistService.createWatchlist(watchlist);

        assertNull(result);
    }

    @Test
    void getAllWatchlist_success(){
        when(watchlistRepository.findAll()).thenReturn(List.of(watchlist));
        List<Watchlist> result = watchlistService.getAllWatchlists();

        assertNotNull(result);
        assertEquals(List.of(watchlist), result);
    }

    @Test
    void getAllWatchlist_empty() {
        when(watchlistRepository.findAll()).thenReturn(List.of());
        List<Watchlist> result = watchlistService.getAllWatchlists();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getMovies_success() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.of(watchlist));
        when(movieRepository.findById(movieId.toString())).thenReturn(Optional.of(movie));

        Set<Optional<Movie>> result = watchlistService.getMovies(watchlistId);

        assertNotNull(result);
        assertEquals(Set.of(Optional.of(movie)), result);
    }

    @Test
    void getMovies_empty() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.of(watchlist));
        when(movieRepository.findById(movieId.toString())).thenReturn(Optional.empty());

        Set<Optional<Movie>> result = watchlistService.getMovies(watchlistId);

        assertNotNull(result);
        assertEquals(Set.of(Optional.empty()), result);
    }

    @Test
    void addMovieToWatchlist_success() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.of(watchlist));
        when(movieRepository.findById(movieId.toString())).thenReturn(Optional.of(movie));
        when(watchlistRepository.save(watchlist)).thenReturn(watchlist);

        Watchlist result = watchlistService.addMovieToWatchlist(movieId, watchlistId);

        assertNotNull(result);
        assertEquals(result, watchlist);
    }

    @Test
    void addMovieToWatchlist_watchlist_notfound() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.empty());

        Watchlist result = watchlistService.addMovieToWatchlist(movieId, watchlistId);

        assertNull(result);
    }

    @Test
    void addMovieToWatchlist_movie_notfound() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.of(watchlist));
        when(movieRepository.findById(movieId.toString())).thenReturn(Optional.empty());

        Watchlist result = watchlistService.addMovieToWatchlist(movieId, watchlistId);

        assertNull(result);
    }

    @Test
    void deleteWatchlist_success() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.of(watchlist));
        boolean result = watchlistService.deleteWatchlist(watchlistId);

        assertTrue(result);
    }

    @Test
    void deleteWatchlist_notfound() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.empty());
        boolean result = watchlistService.deleteWatchlist(watchlistId);

        assertFalse(result);
    }

    @Test
    void deleteMovieFromWatchlist_success() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.of(watchlist));
        when(watchlistRepository.save(watchlist)).thenReturn(watchlist);

        Watchlist result = watchlistService.deleteMovieFromWatchlist(movieId, watchlistId);

        assertNotNull(result);
        assertEquals(result, watchlist);
    }

    @Test
    void deleteMovieFromWatchlist_failed() {
        when(watchlistRepository.findById(watchlistId.toString())).thenReturn(Optional.empty());

        Watchlist result = watchlistService.deleteMovieFromWatchlist(movieId, watchlistId);

        assertNull(result);
    }
}
