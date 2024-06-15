package com.malikoyv.movielisting.controller;

import com.malikoyv.movielisting.model.Watchhistory;
import com.malikoyv.movielisting.service.WatchhistoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchhistory")
public class WatchhistoryController {
    @Autowired
    private WatchhistoryService watchhistoryService;

    @GetMapping("/getByUser/{id}")
    public ResponseEntity<List<Watchhistory>> getByUser(@PathVariable ObjectId id) {
        List<Watchhistory> watchhistory = watchhistoryService.getWatchHistoryByUser(id);
        if (watchhistory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(watchhistory, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Watchhistory> add(@RequestBody Watchhistory watchhistory) {
        Watchhistory w = watchhistoryService.addWatchHistory(watchhistory);
        if (w == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(w, HttpStatus.CREATED);
    }
}
