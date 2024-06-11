package com.malikoyv.movielisting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "roles")
public class Role {
    @Id
    private ObjectId id;
    private ERole name;

    public Role(ERole name) {

        this.name = name;
    }
}
