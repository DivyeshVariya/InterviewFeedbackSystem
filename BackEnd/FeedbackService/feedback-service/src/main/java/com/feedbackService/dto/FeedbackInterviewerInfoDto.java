package com.feedbackService.dto;

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
public class FeedbackInterviewerInfoDto {

	@NotEmpty(message = "Interviewer name should not be empty or null...")
	private String interviewerName;
	
	@NotEmpty(message = "Interviewer designation should not be empty or null...")
    private String interviewerDesignation;
}
