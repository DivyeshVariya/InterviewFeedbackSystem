package com.feedbackService.dto;

import com.feedbackService.model.FeedbackForm;

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
public class FeedbackFormResponseDto extends ResponseDto{

	private FeedbackForm form;
}
