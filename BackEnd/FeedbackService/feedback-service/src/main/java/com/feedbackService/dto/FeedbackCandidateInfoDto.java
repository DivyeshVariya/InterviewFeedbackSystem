package com.feedbackService.dto;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class FeedbackCandidateInfoDto {

	@NotEmpty(message = "Candidate full name should not be empty or null...")
	private String candidateFullName;
	
	@NotEmpty(message = "Candidate position should not be empty or null...")
	private String candidatePosition;
	
	@NotEmpty(message = "Candidate division should not be empty or null...")
	private String candidateDivision;
	
	@Min(value = 0,message = "Candidate total experience should not be empty or null...")
	@Max(value = 10,message = "Candidate total experience should not be empty or null...")
	private int candidateTotalExperience;
	
	@Min(value = 0,message = "Candidate total experience should not be empty or null...")
	@Max(value = 10,message = "Candidate total experience should not be empty or null...")
	private int candidateRelevantExperience;
	
	@NotEmpty(message = "Candidate primary skills should not be empty or null...")
	private List<String> candidatePrimarySkills;
	
	@NotEmpty(message = "Candidate secondary skills should not be empty or null...")
	private List<String> candidateSecondarySkills;
}
