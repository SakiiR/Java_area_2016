package com.epitech.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.epitech.model.User;
import org.springframework.stereotype.Component;

/**
 * This interface is used to
 * interact with Users.
 */
@Component
public interface            UserRepository extends MongoRepository<User, String> {
    public User             findByUsername(String username);
}
