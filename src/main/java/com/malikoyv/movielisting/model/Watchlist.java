package com.malikoyv.movielisting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "watchlist")
public class Watchlist {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private Set<ObjectId> movieId = new HashSet<>();

    public Watchlist(ObjectId movieId) {
        this.movieId.add(movieId);
    }

    public Watchlist(ObjectId id, Set<ObjectId> movieIds){
        this.id = id;
        this.movieId.addAll(movieIds);
    }
}
