package com.emailService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.emailService.model.ConfigMail;
@Repository
public interface ConfigMailRepository extends MongoRepository<ConfigMail, String>{

	Optional<ConfigMail> findByConfigFor(String mailFor);

}
