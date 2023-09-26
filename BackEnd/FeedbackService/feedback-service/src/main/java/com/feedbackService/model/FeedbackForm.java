package com.feedbackService.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.feedbackService.dto.FeedbackCandidateInfoDto;
import com.feedbackService.dto.FeedbackInterviewerInfoDto;
import com.feedbackService.dto.FeedbackSoftSkillsEvaluationDto;
import com.feedbackService.dto.FeedbackTechnicalEvaluationDto;
import com.feedbackService.enums.HiringDecision;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("Feedbacks")
public class FeedbackForm extends BaseEntity{

	@Transient
    public static final String SEQUENCE_NAME = "forms_sequence";
	
	@Id
	private String documentNo;
	private FeedbackCandidateInfoDto candidateInfo;
	private FeedbackInterviewerInfoDto interviewerInfo;
	private List<FeedbackTechnicalEvaluationDto> technicalEvaluation;
	private List<FeedbackSoftSkillsEvaluationDto> softSkillsEvaluation; 
	private HiringDecision hiringDecision;
	private Date dateOfInterview;
}
