package com.malikoyv.movielisting.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "watchhistory")
public class Watchhistory {
    @Id
    private ObjectId id;
    private ObjectId movieId;
    private ObjectId userId;
    private Date watchedAt;
}
