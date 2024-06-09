package com.malikoyv.movielisting.model;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Document(collection = "movies")
public class Movie {
    @Id
    private ObjectId _id;
    private String name;
    private String director;
    private int year;
    @DocumentReference
    private List<Review> reviewIds;
    private List<String> genre;
}
