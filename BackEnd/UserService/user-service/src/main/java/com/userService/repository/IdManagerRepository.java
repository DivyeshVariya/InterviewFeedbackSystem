package com.userService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.userService.model.IdManager;

@Repository
public interface IdManagerRepository extends MongoRepository<IdManager, String>{

}
