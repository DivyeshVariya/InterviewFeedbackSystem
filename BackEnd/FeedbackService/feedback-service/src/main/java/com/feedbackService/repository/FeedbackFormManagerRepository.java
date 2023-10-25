package com.feedbackService.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.feedbackService.enums.HiringDecision;
import com.feedbackService.model.FeedbackForm;
@Repository
public interface FeedbackFormManagerRepository extends MongoRepository<FeedbackForm, String>{

	List<FeedbackForm> findByInterviewerInfo_InterviewerName(String interviewerName);

	@Query(value="{'dateOfInterview' : { $gte: ?0, $lte: ?1 },'hiringDecision':?2 }" ,count=true)                 
	public int getObjectByDate(Date from, Date to,HiringDecision status);
	
	@Query(value="{'dateOfInterview' : { $gte: ?0, $lte: ?1 }}" ,count=true)                 
	public int getObjectByDate(Date from, Date to);	
}
