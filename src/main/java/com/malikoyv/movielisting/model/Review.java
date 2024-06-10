package com.malikoyv.movielisting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "reviews")
public class Review {
    @Id
    private ObjectId _id;
    private ObjectId movieId;
    private ObjectId authorId;
    private double rating;
    private String description;
}
