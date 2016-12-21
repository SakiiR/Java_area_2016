package com.epitech.repository;

import com.epitech.model.UserModule;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by sakiir on 21/12/16.
 */
public interface UserModuleRepository  extends MongoRepository<UserModule, String>{

}
