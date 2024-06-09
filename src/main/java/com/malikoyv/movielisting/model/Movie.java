package com.malikoyv.movielisting.model;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Document(collection = "movies")
public class Movie {
    @NonNull
    private ObjectId _id;
    @NonNull
    private String name;
    @NonNull
    private String director;
    @NonNull
    private int year;
    private double review;
    @NonNull
    private String genre;
}
