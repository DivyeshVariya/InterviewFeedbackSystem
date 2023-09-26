package com.feedbackService.dto;

import java.time.LocalDate;

import com.feedbackService.enums.HiringDecision;

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
public class FeedbackFileDecisionDto {

	private String temparyDocumentNo;
	private HiringDecision hiringDecision;
	private LocalDate dateOfInterview;
}
