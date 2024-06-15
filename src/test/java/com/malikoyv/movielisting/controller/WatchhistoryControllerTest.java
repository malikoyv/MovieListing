package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.config.JwtService;
import com.malikoyv.movielisting.model.Watchhistory;
import com.malikoyv.movielisting.service.WatchhistoryService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = WatchhistoryController.class)
public class WatchhistoryControllerTest {
    @MockBean
    private WatchhistoryService service;
    @MockBean
    private JwtService jwtService;
    @InjectMocks
    private WatchhistoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWatchhistoryByUserId_success() {
        Watchhistory watchhistory = new Watchhistory(new ObjectId());

        when(service.getWatchHistoryByUser(watchhistory.getUserId())).thenReturn(List.of(watchhistory));
        ResponseEntity<List<Watchhistory>> response = controller.getByUser(watchhistory.getUserId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(watchhistory, response.getBody().getFirst());
    }

    @Test
    void getWatchhistoryByUserId_failure() {
        Watchhistory watchhistory = new Watchhistory(new ObjectId());

        when(service.getWatchHistoryByUser(watchhistory.getUserId())).thenReturn(null);
        ResponseEntity<List<Watchhistory>> response = controller.getByUser(watchhistory.getUserId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createWatchhistory_success() {
        Watchhistory watchhistory = new Watchhistory(new ObjectId());

        when(service.addWatchHistory(watchhistory)).thenReturn(watchhistory);
        ResponseEntity<Watchhistory> response = controller.add(watchhistory);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(watchhistory, response.getBody());
    }

    @Test
    void createWatchhistory_failure() {
        Watchhistory watchhistory = new Watchhistory(new ObjectId());

        when(service.addWatchHistory(watchhistory)).thenReturn(null);
        ResponseEntity<Watchhistory> response = controller.add(watchhistory);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}
