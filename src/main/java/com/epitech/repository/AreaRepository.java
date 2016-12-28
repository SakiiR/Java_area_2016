package com.epitech.repository;

import com.epitech.model.Area;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * This interface is used to
 * interact with Areas.
 */
@Component
public interface                AreaRepository extends MongoRepository<Area, String> {
    public Area                 findByActionName(String actionName);
    public Area                 findByReactionName(String reactionName);
}
