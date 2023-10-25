package com.feedbackService.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.feedbackService.dto.AllFeedbackFileResponseDto;
import com.feedbackService.dto.ResponseDto;
import com.feedbackService.model.FeedbackForm;
import com.feedbackService.model.FileManager;
import com.feedbackService.repository.FileManagerRepository;
import com.feedbackService.service.FileManagerService;
@Service
public class FileManagerServiceImpl implements FileManagerService{

	@Autowired
	FileManagerRepository fileManagerRepository;
	
	private Logger logger=LoggerFactory.getLogger(FileManagerServiceImpl.class);
	
	@Override
	public String addFileMappingWithForm(FileManager filemanager){
		// TODO Auto-generated method stub
		Optional<FileManager> savedData=Optional.of(fileManagerRepository.save(filemanager));
		if(savedData.isEmpty())
		{
			return "Fail to add mapping...";
		}else
		{
		return "Mapping added successfully...";
		}
	}

	@Override
	public AllFeedbackFileResponseDto getFilesByInterviewerName(String interviewerName) {
		// TODO Auto-generated method stub
		List<FileManager> savedData=fileManagerRepository.findByInterviewerName(interviewerName);
		logger.info(savedData.toString());
		AllFeedbackFileResponseDto responseDto=new AllFeedbackFileResponseDto();
		responseDto.setListOfFiles(savedData);
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setResponseMessage("All feedback file fetched for current interviewer !!!");
		return responseDto;
	}

	@Override
	public List<String> deleteFeedbackFile(String fileId) {
		// TODO Auto-generated method stub
		ResponseDto responseDto=new ResponseDto();
		Optional<FileManager> savedData=fileManagerRepository.findById(fileId);
		if(!savedData.isEmpty())
		{
			fileManagerRepository.deleteById(fileId);
			logger.info("file info removed successfully !!");
			return savedData.get().getFormsFromFile();
		}
		else
		{
			return List.of();
		}
	}

}
