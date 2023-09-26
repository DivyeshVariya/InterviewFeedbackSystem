package com.feedbackService.dto;

import com.feedbackService.annotation.CustomAnnoEnumValid;
import com.feedbackService.enums.Rating;

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
public class FeedbackSoftSkillsEvaluationDto {

	@NotEmpty(message = "Skill should not be empty or null...")
	private String skill;
	
	@CustomAnnoEnumValid(regexp = "A|B|C|D")
    private Rating rating;
}
