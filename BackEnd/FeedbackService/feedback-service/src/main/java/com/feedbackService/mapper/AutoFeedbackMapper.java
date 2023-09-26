package com.feedbackService.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.feedbackService.dto.FeedbackFormRequestDto;
import com.feedbackService.model.FeedbackForm;

@Mapper
public interface AutoFeedbackMapper {

	AutoFeedbackMapper MAPPER = Mappers.getMapper(AutoFeedbackMapper.class);
	
	FeedbackForm mapToFeedbackForm(FeedbackFormRequestDto feedbackFormRequestDto);
	
	FeedbackFormRequestDto mapToFeedbackRequestDto(FeedbackForm feedbackForm);
}
