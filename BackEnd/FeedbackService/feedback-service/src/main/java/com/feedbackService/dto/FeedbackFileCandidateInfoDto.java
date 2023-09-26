package com.feedbackService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FeedbackFileCandidateInfoDto extends FeedbackCandidateInfoDto{

	private String temparyDocumentNo;
}
