package com.emailService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.emailService.model.MailTemplate;
@Repository
public interface MailTemplateRepository extends MongoRepository<MailTemplate, String>{

	Optional<MailTemplate> findByTemplateFor(String mailFor);

}
