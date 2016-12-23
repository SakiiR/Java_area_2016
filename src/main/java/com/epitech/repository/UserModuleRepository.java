package com.epitech.repository;

import com.epitech.model.UserModule;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * This interface is used to
 * interact with UserModules.
 */
public interface UserModuleRepository  extends MongoRepository<UserModule, String>{

}
