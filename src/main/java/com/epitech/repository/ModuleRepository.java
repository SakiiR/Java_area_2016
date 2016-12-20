package com.epitech.repository;

import com.epitech.model.Module;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by sakiir on 20/12/16.
 */
public interface            ModuleRepository extends MongoRepository<Module, String>{
    public Module           findByName(String name);
}
