package com.epitech.repository;

import com.epitech.model.Module;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * This interface is used to
 * interact with Modules.
 */
@Component
public interface            ModuleRepository extends MongoRepository<Module, String> {
    public Module           findByName(String name);
    public Module           findById(String id);
}
