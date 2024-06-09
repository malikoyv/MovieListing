package com.malikoyv.movielisting.model;

import jakarta.annotation.Nullable;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Document(collection = "users")
public class User {
    @NonNull
    private ObjectId _id;
    @NonNull
    private String username;
    @Nullable
    private String email;
    @NonNull
    private String password;
}
