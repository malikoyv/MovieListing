package com.malikoyv.movielisting.repos;

import com.malikoyv.movielisting.model.ERole;
import com.malikoyv.movielisting.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
