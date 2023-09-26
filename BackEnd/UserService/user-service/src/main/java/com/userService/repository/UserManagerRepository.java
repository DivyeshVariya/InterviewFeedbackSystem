package com.userService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.userService.model.Users;

@Repository
public interface UserManagerRepository extends MongoRepository<Users, Long>{

	Optional<Users> findByUserEmailId(String emailId);

	void deleteByUserEmailId(String userEmailId);
}
