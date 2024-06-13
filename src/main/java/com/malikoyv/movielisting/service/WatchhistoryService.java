package com.malikoyv.movielisting.service;

import com.malikoyv.movielisting.model.Watchhistory;
import com.malikoyv.movielisting.repos.WatchhistoryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchhistoryService {
    @Autowired
    private WatchhistoryRepository watchhistoryRepository;

    public List<Watchhistory> getWatchHistoryByUser (ObjectId id){
        return watchhistoryRepository.findByUserId(id);
    }

    public Watchhistory addWatchHistory (Watchhistory watchhistory){
        return watchhistoryRepository.save(watchhistory);
    }
}
