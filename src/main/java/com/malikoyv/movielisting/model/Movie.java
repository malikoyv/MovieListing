package com.malikoyv.movielisting.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Document(collection = "movies")
public class Movie {
    private ObjectId _id;
    private String name;
    private String director;
    private int year;
    private double rating;
    private String genre;
}
