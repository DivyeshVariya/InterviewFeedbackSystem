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
public class UpdateFeedbackFormRequestDto extends FeedbackFormRequestDto{

	@NotEmpty(message = "Document no. should not be empty or null...")
	private String documentNo;
}
