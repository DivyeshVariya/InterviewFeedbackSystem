package com.feedbackService.dto;

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
public class FeedbackFileSoftSkillsEvaluationDto extends FeedbackSoftSkillsEvaluationDto{

	private String temparyDocumentNo;
}
