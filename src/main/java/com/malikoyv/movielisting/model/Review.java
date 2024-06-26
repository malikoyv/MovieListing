package com.malikoyv.movielisting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    public Review(ObjectId _id, ObjectId movieId, ObjectId authorId, double rating){
        this._id = _id;
        this.movieId = movieId;
        this.authorId = authorId;
        this.rating = rating;
    }

    public Review(ObjectId movieId){
        this.movieId = movieId;
    }

    public Review(ObjectId authorId, double rating){
        this.authorId = authorId;
        this.rating = rating;
    }

    public Review(ObjectId id, String description) {
        this._id = id;
        this.description = description;
    }
}
