package com.feedbackService.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
