package com.malikoyv.movielisting.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "roles")
public class Role implements GrantedAuthority {
    @Id
    private ObjectId id;
    private ERole name;

    @Override
    public String getAuthority() {
        return this.name.name();
    }

    public Role(ERole name) {
        this.name = name;
    }
}
