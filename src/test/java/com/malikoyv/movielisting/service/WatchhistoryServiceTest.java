package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.User;
import com.malikoyv.movielisting.model.Watchhistory;
import com.malikoyv.movielisting.repos.WatchhistoryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class WatchhistoryServiceTest {
    @Mock
    private WatchhistoryRepository watchhistoryRepository;

    @InjectMocks
    private WatchhistoryService watchhistoryService;

    private User user;
    private Watchhistory watchhistory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.set_id(new ObjectId());
        watchhistory = new Watchhistory(new ObjectId(), new ObjectId(), user.get_id(), null);
    }

    @Test
    void getWatchhistoryByUser_success(){
        when(watchhistoryRepository.findByUserId(user.get_id())).thenReturn(List.of(watchhistory));

        List<Watchhistory> result = watchhistoryService.getWatchHistoryByUser(user.get_id());

        assertEquals(1, result.size());
        assertEquals(watchhistory, result.getFirst());
    }

    @Test
    void getWatchHistoryByUser_empty(){
        when(watchhistoryRepository.findByUserId(user.get_id())).thenReturn(List.of());

        List<Watchhistory> result = watchhistoryService.getWatchHistoryByUser(user.get_id());

        assertEquals(0, result.size());
    }

    @Test
    void addWatchHistory_success(){
        when(watchhistoryRepository.save(watchhistory)).thenReturn(watchhistory);

        Watchhistory result = watchhistoryService.addWatchHistory(watchhistory);

        assertEquals(watchhistory, result);
    }

    @Test
    void addWatchHistory_null(){
        when(watchhistoryRepository.save(null)).thenReturn(null);

        Watchhistory result = watchhistoryService.addWatchHistory(null);

        assertNull(result);
    }
}
