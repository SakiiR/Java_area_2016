package com.epitech.repository;

import com.epitech.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * this interface is used to interact with notifications
 */
public interface            NotificationRepository extends MongoRepository<Notification, String> {  }
