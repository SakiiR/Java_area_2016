package com.epitech.repository;

import com.epitech.model.Module;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * Created by sakiir on 20/12/16.
 */
@Component
public interface            ModuleRepository extends MongoRepository<Module, String> {
    public Module           findByName(String name);
}
