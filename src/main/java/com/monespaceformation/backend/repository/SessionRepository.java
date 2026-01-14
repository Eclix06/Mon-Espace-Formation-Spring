package com.monespaceformation.backend.repository;

import com.monespaceformation.backend.model.SessionFormation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends MongoRepository<SessionFormation, String> {
}
