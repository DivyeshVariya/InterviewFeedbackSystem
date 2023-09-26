package com.feedbackService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.feedbackService.model.IdManager;
@Repository
public interface IdManagerRepository extends MongoRepository<IdManager, String>{

}
