package com.epitech.repository;

import com.epitech.model.BackofficeUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * This interface is used to
 * interact with the Backoffice Users.
 */
@Component
public interface            BackofficeUserRepository extends MongoRepository<BackofficeUser, String> {
    public BackofficeUser   findByUsername(String username);
}
