package com.monespaceformation.backend.repository;

import com.monespaceformation.backend.model.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends MongoRepository<Settings, String> {
    // On utilise un seul document de paramètres avec un ID fixe
    // Par défaut, on cherche le document avec l'ID "default"
}

