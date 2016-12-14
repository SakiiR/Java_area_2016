package com.epitech.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.epitech.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by anakin on 14/12/16.
 */
@Component
public interface            UserRepository extends MongoRepository<User, String> {
    public User             findByUsername(String username);
    public List<User>       findByPassword(String password);
}
