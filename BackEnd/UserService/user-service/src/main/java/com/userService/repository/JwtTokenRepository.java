package com.userService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.userService.model.JwtToken;

@Repository
public interface JwtTokenRepository extends MongoRepository<JwtToken, String>{

}
