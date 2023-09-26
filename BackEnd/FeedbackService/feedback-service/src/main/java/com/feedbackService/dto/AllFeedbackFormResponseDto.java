package com.feedbackService.dto;

import java.util.List;

import com.feedbackService.model.FeedbackForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@ToString
public class AllFeedbackFormResponseDto extends ResponseDto{

	private List<FeedbackForm> forms;
}
