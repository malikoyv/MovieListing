package com.malikoyv.movielisting.model;

import jakarta.annotation.Nullable;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Document(collection = "users")
public class User {
    @Id
    private ObjectId _id;
    private String username;
    private String email;
    private String password;
}
