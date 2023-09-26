package com.feedbackService.service;

import java.io.IOException;
import java.text.ParseException;

import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.springframework.web.multipart.MultipartFile;

import com.feedbackService.dto.AllFeedbackFormResponseDto;
import com.feedbackService.dto.DownloadFileResponseDto;
import com.feedbackService.dto.FeedbackFormRequestDto;
import com.feedbackService.dto.FeedbackFormResponseDto;
import com.feedbackService.dto.ResponseDto;
import com.feedbackService.dto.UpdateFeedbackFormRequestDto;
import com.feedbackService.exception.FailToSaveDocumentException;


public interface FeedbackManagerService {

	ResponseDto handleFeedbackForm(FeedbackFormRequestDto feedbackFormRequestDto) throws FailToSaveDocumentException;

	ResponseDto handleFeedbackDocument(MultipartFile feedbackFile,String interviewerName,String interviewerDesignation) throws IOException, ParseException;

	DownloadFileResponseDto handleDownloadDocument(String fileId) throws IllegalStateException, IOException;

	FeedbackFormResponseDto getFeedbackForm(String documentNo);

	AllFeedbackFormResponseDto getAllFeedbackFormForHr();

	ResponseDto updateFeedabackDetails(UpdateFeedbackFormRequestDto feedFormRequestDto);

	ResponseDto deleteFeedbackForm(String documentNo);

	AllFeedbackFormResponseDto getAllFeedbackFormForInterviewer(String interviewerName);

}
