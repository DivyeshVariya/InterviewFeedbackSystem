package com.feedbackService.service;

import java.util.List;

import com.feedbackService.dto.AllFeedbackFileResponseDto;
import com.feedbackService.dto.ResponseDto;
import com.feedbackService.model.FileManager;

public interface FileManagerService {

	String addFileMappingWithForm(FileManager filemanager);

	AllFeedbackFileResponseDto getFilesByInterviewerName(String interviewerName);

	List<String> deleteFeedbackFile(String fileId);
}
