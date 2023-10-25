package com.feedbackService.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.feedbackService.dto.AllFeedbackFileResponseDto;
import com.feedbackService.dto.AllFeedbackFormResponseDto;
import com.feedbackService.dto.DownloadFileResponseDto;
import com.feedbackService.dto.FeedbackFormRequestDto;
import com.feedbackService.dto.FeedbackFormResponseDto;
import com.feedbackService.dto.HiringDataResponseDto;
import com.feedbackService.dto.ReportDateRangeDto;
import com.feedbackService.dto.ResponseDto;
import com.feedbackService.dto.UpdateFeedbackFormRequestDto;
import com.feedbackService.exception.FailToSaveDocumentException;
import com.feedbackService.service.FeedbackManagerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedbackManager")
@Validated
public class FeedbackManagerController implements ErrorController{
	
	@Autowired
	FeedbackManagerService feedbackManagerService;

	private Logger logger= LoggerFactory.getLogger(FeedbackManagerController.class);
	
	@PostMapping("/handle-feedback-form")
	public ResponseEntity<ResponseDto> handleFeedbackForm(@Valid @RequestBody FeedbackFormRequestDto feedbackFormRequestDto) throws FailToSaveDocumentException
	{
		logger.info(feedbackFormRequestDto.toString());
		return new ResponseEntity<>(feedbackManagerService.handleFeedbackForm(feedbackFormRequestDto),HttpStatus.OK);
	}
	
	@PostMapping(path="/handle-feedback-document",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseDto> handleFeedbackDocument(
			@RequestParam("feedbackFile") MultipartFile feedbackFile,
		    @RequestParam("interviewerName") String interviewerName,
		    @RequestParam("interviewerDesignation") String interviewerDesignation)
		    		throws IOException, ParseException
	{
		logger.info(interviewerName+" "+interviewerDesignation);
		return new ResponseEntity<>(feedbackManagerService.handleFeedbackDocument(feedbackFile,interviewerName,interviewerDesignation),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-feedback-files-by-interviewer")
	public ResponseEntity<AllFeedbackFileResponseDto> getAllFeedbackFileByInterviewer(@RequestParam String interviewerName)
	{
		logger.info(interviewerName);
		return new ResponseEntity<>(feedbackManagerService.getAllFeedbackFileByInterviewer(interviewerName),HttpStatus.OK);
	}
	
	@GetMapping("/download-document")
	public ResponseEntity<ByteArrayResource> handleDownloadDocument(@RequestParam("fileId") String fileId) throws IllegalStateException, IOException
	{
		logger.info(fileId);
		DownloadFileResponseDto loadFile=feedbackManagerService.handleDownloadDocument(fileId);
		  return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
	                .body(new ByteArrayResource(loadFile.getFile()));
	}
	
	@GetMapping("/get-feedback-by-documentNo")
	public ResponseEntity<FeedbackFormResponseDto> getFeedbackForm(@RequestParam("documentNo") String documentNo)
	{
		return new ResponseEntity<>(feedbackManagerService.getFeedbackForm(documentNo),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-feedback-file")
	public ResponseEntity<ResponseDto> deleteFile(@RequestParam("fileId") String fileId)
	{
		logger.info(fileId);
		return new ResponseEntity<>(feedbackManagerService.deleteFeedbackFile(fileId),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-feedbacks-hr")
	public ResponseEntity<AllFeedbackFormResponseDto> getAllFeedbackFormForHr()
	{
		return new ResponseEntity<>(feedbackManagerService.getAllFeedbackFormForHr(),HttpStatus.OK);
	}
	
	@GetMapping("/get-all-feedbacks-interviewer")
	public ResponseEntity<AllFeedbackFormResponseDto> getAllFeedbackFormForInterviewer(@RequestParam String interviewerName)
	{
		return new ResponseEntity<>(feedbackManagerService.getAllFeedbackFormForInterviewer(interviewerName),HttpStatus.OK);
	}
	
	@PutMapping("/update-feedback-details")
	public ResponseEntity<ResponseDto> updateFeedbackDetails(@RequestBody UpdateFeedbackFormRequestDto feedFormRequestDto)
	{
		return new ResponseEntity<>(feedbackManagerService.updateFeedabackDetails(feedFormRequestDto),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-feedback")
	public ResponseEntity<ResponseDto> deleteFeedbackForm(@RequestParam("documentNo") String documentNo)
	{
		return new ResponseEntity<>(feedbackManagerService.deleteFeedbackForm(documentNo),HttpStatus.OK);
	}
	
	@GetMapping("/get-hiring-data")
	public ResponseEntity<HiringDataResponseDto> getHiringData(@RequestParam("to") LocalDate to,@RequestParam("from") LocalDate from)
	{
		ReportDateRangeDto dateRange=new ReportDateRangeDto(to,from);
		logger.info(dateRange.toString());
		return new ResponseEntity<>(feedbackManagerService.getHiringData(dateRange),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/error")
    public String error() {
		return "<center><h1 style=\"color:red;\">Unauthorized Access!</h1></center>";
    }
}
