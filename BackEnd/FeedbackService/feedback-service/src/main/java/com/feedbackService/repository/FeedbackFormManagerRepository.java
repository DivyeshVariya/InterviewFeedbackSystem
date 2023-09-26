package com.feedbackService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.feedbackService.model.FeedbackForm;
@Repository
public interface FeedbackFormManagerRepository extends MongoRepository<FeedbackForm, String>{

	List<FeedbackForm> findByInterviewerInfo_InterviewerName(String interviewerName);

}
