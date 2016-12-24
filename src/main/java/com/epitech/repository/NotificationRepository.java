package com.epitech.repository;

import com.epitech.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface            NotificationRepository extends MongoRepository<Notification, String> {  }
