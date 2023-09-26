package com.emailService.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.emailService.model.MailAdmin;
@Repository
public interface MailAdminRepository extends MongoRepository<MailAdmin, String>{

	Optional<MailAdmin> findByMailFrom(String email);

	void deleteByMailFrom(String email);

	Optional<MailAdmin> findByMailAdminFor(String mailAdminFor);

}
