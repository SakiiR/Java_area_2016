package com.epitech.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.epitech.model.User;

/**
 * Created by anakin on 14/12/16.
 */
public interface UserRepository extends MongoRepository<User, String> {
    public User findByUserName(String UserName);
    public List<User> findByPassword(String password);

}
