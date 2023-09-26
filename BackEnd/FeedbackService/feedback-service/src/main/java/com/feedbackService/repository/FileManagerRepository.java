package com.feedbackService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.feedbackService.model.FileManager;
@Repository
public interface FileManagerRepository extends MongoRepository<FileManager, String>{

}
