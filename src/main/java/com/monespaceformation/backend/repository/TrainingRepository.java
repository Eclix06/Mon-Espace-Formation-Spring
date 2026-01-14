package com.monespaceformation.backend.repository;

import com.monespaceformation.backend.model.Training;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface TrainingRepository extends MongoRepository<Training, String> {
    // C'est vide pour l'instant, c'est normal !
    // MongoRepository fait tout le travail magique derrière.
    
    // Trouver toutes les formations assignées à un formateur
    List<Training> findByTrainerId(String trainerId);
}