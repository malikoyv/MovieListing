package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Movie;
import com.malikoyv.movielisting.repos.MovieRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;
    private final ObjectId movieId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movie = new Movie();
        movie.set_id(movieId);
        movie.setName("name");
        movie.setDirector("director");
        movie.setYear(1972);
        movie.setGenre(List.of("genre"));
    }

    @Test
    void getById_success(){
        when(movieRepository.findById(anyString())).thenReturn(Optional.of(movie));
        Optional<Movie> movie = movieService.getById(movieId);

        assertTrue(movie.isPresent());
        assertEquals("name", movie.get().getName());
        assertEquals("director", movie.get().getDirector());
    }

    @Test
    void getById_failed(){
        when(movieRepository.findById(anyString())).thenReturn(Optional.empty());
        Optional<Movie> movie = movieService.getById(movieId);

        assertTrue(movie.isEmpty());
    }

    @Test
    void getAll_success(){
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        assertEquals(1, movieService.getAll().size());
    }

    @Test
    void getAll_failed(){
        when(movieRepository.findAll()).thenReturn(List.of());

        assertEquals(0, movieService.getAll().size());
    }

    @Test
    void getByName_success(){
        when(movieRepository.findByName(anyString())).thenReturn(Optional.of(movie));
        Optional<Movie> movie = movieService.getByName("name");

        assertTrue(movie.isPresent());
        assertEquals("name", movie.get().getName());
        assertEquals("director", movie.get().getDirector());
    }

    @Test
    void getByName_failed(){
        when(movieRepository.findByName(anyString())).thenReturn(Optional.empty());
        Optional<Movie> movie = movieService.getByName("name");

        assertEquals(Optional.empty(), movie);
    }

    @Test
    void getByDirector_success(){
        when(movieRepository.findByDirector(anyString())).thenReturn(Optional.of(movie));
        Optional<Movie> movie = movieService.getByDirector("director");

        assertTrue(movie.isPresent());
        assertEquals("name", movie.get().getName());
        assertEquals("director", movie.get().getDirector());
    }

    @Test
    void getByDirector_failed(){
        when(movieRepository.findByDirector(anyString())).thenReturn(Optional.empty());
        Optional<Movie> movie = movieService.getByDirector("director");

        assertEquals(Optional.empty(), movie);
    }

    @Test
    void addMovie_success(){
        when(movieRepository.save(movie)).thenReturn(movie);
        Movie result = movieService.addMovie(movie);

        assertEquals(movie, result);
    }

    @Test
    void addMovie_failed(){
        Movie invalidMovie = new Movie();
        when(movieRepository.save(invalidMovie)).thenReturn(null);
        Movie result = movieService.addMovie(invalidMovie);

        assertNull(result);
    }

    @Test
    void updateMovie_success(){
        when(movieRepository.findById(anyString())).thenReturn(Optional.of(movie));
        movie.setName("newName");
        movieService.updateMovie(movieId, movie);

        assertEquals("newName", movie.getName());
    }

    @Test
    void updateMovie_failed(){
        when(movieRepository.findById(anyString())).thenReturn(Optional.empty());
        movieService.updateMovie(movieId, movie);

        assertEquals("name", movie.getName());
    }

    @Test
    void deleteMovie_success(){
        when(movieRepository.existsById(anyString())).thenReturn(true);
        assertTrue(movieService.deleteMovie(movieId));
    }

    @Test
    void deleteMovie_failed(){
        when(movieRepository.existsById(anyString())).thenReturn(false);
        assertFalse(movieService.deleteMovie(movieId));
    }
}
