package com.monespaceformation.backend.repository;

import com.monespaceformation.backend.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    // Trouver toutes les notifications non lues
    List<Notification> findByIsReadFalse();
    
    // Compter les notifications non lues
    long countByIsReadFalse();
}

