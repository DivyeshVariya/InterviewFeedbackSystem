package com.feedbackService.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.feedbackService.annotation.CustomAnnoEnumValid;
import com.feedbackService.enums.HiringDecision;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
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
public class FeedbackFormRequestDto {
	
	@Valid
	private FeedbackCandidateInfoDto candidateInfo;
	
	@Valid
	private FeedbackInterviewerInfoDto interviewerInfo;
	
	@Valid
	private List<FeedbackTechnicalEvaluationDto> technicalEvaluation;
	
	@Valid
	private List<FeedbackSoftSkillsEvaluationDto> softSkillsEvaluation; 
	
	@CustomAnnoEnumValid(regexp = "HIRE|PENDING|NO_HIRE")
//	@NotNull(message = "Invalid decision value...")
//	@Pattern(regexp = "NOT_HIRE|HIRE",message = "invalid input")
	private HiringDecision hiringDecision;
	
	@DateTimeFormat(pattern = "YYYY-MM-DD",iso = ISO.DATE)
	@PastOrPresent(message = "Date must be past or present...")
//	@JsonFormat(pattern = "yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
	private LocalDate dateOfInterview;
}
