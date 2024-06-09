package com.malikoyv.movielisting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Document(collection = "reviews")
public class Review {
    private ObjectId _id;
    private ObjectId movieId;
    private ObjectId authorId;
    private String description;
}
