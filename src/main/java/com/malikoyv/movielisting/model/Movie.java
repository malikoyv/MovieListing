package com.malikoyv.movielisting.model;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private ObjectId _id;
    private String name;
    private String director;
    private int year;
    private List<ObjectId> reviewIds;
    private List<String> genre;

    public Movie(ObjectId _id, String name){
        this._id = _id;
        this.name = name;
    }

    public Movie(String director){
        this.director = director;
    }
}
