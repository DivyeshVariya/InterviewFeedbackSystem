package com.feedbackService.serviceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.feedbackService.dto.AllFeedbackFormResponseDto;
import com.feedbackService.dto.DownloadFileResponseDto;
import com.feedbackService.dto.FeedbackFormRequestDto;
import com.feedbackService.dto.FeedbackFormResponseDto;
import com.feedbackService.dto.ResponseDto;
import com.feedbackService.dto.UpdateFeedbackFormRequestDto;
import com.feedbackService.exception.FailToDownloadFileException;
import com.feedbackService.exception.FailToSaveDocumentException;
import com.feedbackService.exception.FeedbackNotFoundException;
import com.feedbackService.mapper.AutoFeedbackMapper;
import com.feedbackService.model.FeedbackForm;
import com.feedbackService.model.FileManager;
import com.feedbackService.repository.FeedbackFormManagerRepository;
import com.feedbackService.service.FeedbackManagerService;
import com.feedbackService.service.FileManagerService;
import com.feedbackService.service.IdManagerService;
import com.feedbackService.utils.ConvertExcelToJson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Service
public class FeedbackManagerServiceImpl implements FeedbackManagerService {

	@Autowired
	FeedbackFormManagerRepository feedbackFormManagerRepository;

	@Autowired
	ConvertExcelToJson convertExcelToJson;

	@Autowired
	IdManagerService idManagerService;

	@Autowired
	private GridFsTemplate template;

	@Autowired
	private GridFsOperations operations;

	@Autowired
	private FileManagerService fileManagerService;

	private Logger logger = LoggerFactory.getLogger(FeedbackManagerServiceImpl.class);

	@Override
	public ResponseDto handleFeedbackForm(FeedbackFormRequestDto feedbackFormRequestDto)
			throws FailToSaveDocumentException {
		// TODO Auto-generated method stub
		logger.info(feedbackFormRequestDto.toString());
		FeedbackForm feedbackForm = AutoFeedbackMapper.MAPPER.mapToFeedbackForm(feedbackFormRequestDto);
		logger.info(feedbackForm.toString());
		logger.info(feedbackForm.getDateOfInterview().toString());
		feedbackForm.setDocumentNo("EI/GL/AHMD/FORM/" + idManagerService.generateSequence(FeedbackForm.SEQUENCE_NAME));
		feedbackForm.setDateOfInterview(
				Date.from(feedbackFormRequestDto.getDateOfInterview().atStartOfDay(ZoneId.of("UTC")).toInstant()));
		logger.info(feedbackForm.toString());
		Optional<FeedbackForm> savedForm = Optional.of(feedbackFormManagerRepository.save(feedbackForm));
		if (savedForm.isEmpty()) {
			throw new FailToSaveDocumentException("Oops ! Fail to save data into database...");
		} else {
			logger.info(savedForm.toString());
			logger.info("Data save successfully...");
			ResponseDto responseDto = new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setResponseMessage("Data save successfully....");
			return responseDto;
		}
	}

	@Override
	public ResponseDto handleFeedbackDocument(MultipartFile feedbackFile, String interviewerName,
			String interviewerDesignation) throws IOException, ParseException {
		// TODO Auto-generated method stub

		List<FeedbackForm> formDataFromFile = convertExcelToJson.ConvertToJson(feedbackFile, interviewerName,
				interviewerDesignation);
		feedbackFormManagerRepository.saveAll(formDataFromFile);
		logger.info("Document data saved into Database....");

		DBObject metadata = new BasicDBObject();
		metadata.put("fileSize", feedbackFile.getSize());
		
		String[] fileNameData = feedbackFile.getOriginalFilename().split("\\.");
		String newFileName = fileNameData[0].concat("_" + interviewerName).concat("."+fileNameData[1]).replace(" ", "_");
		logger.info(newFileName);
		Object fileID = template.store(feedbackFile.getInputStream(), newFileName, feedbackFile.getContentType(),
				metadata);
		logger.info(fileID.toString());

		List<String> listOfFormId = new ArrayList<>();
		for (FeedbackForm form : formDataFromFile) {
			listOfFormId.add(form.getDocumentNo());
		}
		
		FileManager fileManager = new FileManager(fileID.toString(), listOfFormId, interviewerName,
				newFileName);
		logger.info(fileManagerService.addFileMappingWithForm(fileManager));

		logger.info("Document save successfully...");
		ResponseDto responseDto = new ResponseDto();
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setResponseMessage("Document save successfully....");
		return responseDto;
	}

	@Override
	public DownloadFileResponseDto handleDownloadDocument(String fileId) throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		GridFSFile gridFSFile = template.findOne(new Query(Criteria.where("_id").is(fileId)));

		DownloadFileResponseDto loadFile = new DownloadFileResponseDto();

		if (gridFSFile != null && gridFSFile.getMetadata() != null) {
			loadFile.setFilename(gridFSFile.getFilename());

			loadFile.setFileType(gridFSFile.getMetadata().get("_contentType").toString());

			loadFile.setFileSize(gridFSFile.getMetadata().get("fileSize").toString());

			loadFile.setFile(IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()));

			loadFile.setResponseCode(HttpStatus.OK.value());

			loadFile.setResponseMessage("Document downloaded successfully...");

			return loadFile;
		} else {
			throw new FailToDownloadFileException("Oops ! Fail to download document...");
		}

	}

	@Override
	public FeedbackFormResponseDto getFeedbackForm(String documentNo) {
		// TODO Auto-generated method stub
		Optional<FeedbackForm> savedData=feedbackFormManagerRepository.findById(documentNo);
		if(savedData.isEmpty())
		{
			throw new FeedbackNotFoundException("Feedback not found !!");
		}
		else
		{
			logger.info(documentNo+" form data fetched successfully !!!");
			FeedbackFormResponseDto feedbackFormResponseDto=new FeedbackFormResponseDto();
			feedbackFormResponseDto.setForm(savedData.get());
			feedbackFormResponseDto.setResponseCode(HttpStatus.OK.value());
			feedbackFormResponseDto.setResponseMessage(documentNo+" form data fetched successfully !!!");
			return feedbackFormResponseDto;
		}
	}

	@Override
	public AllFeedbackFormResponseDto getAllFeedbackFormForHr() {
		// TODO Auto-generated method stub
		List<FeedbackForm> allSavedForm =feedbackFormManagerRepository.findAll();
		
		logger.info("All feedback forms get successfully !!!");
		
		AllFeedbackFormResponseDto responseDto= new AllFeedbackFormResponseDto();
		responseDto.setForms(allSavedForm);
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setResponseMessage("All feedback forms fetched successfully !!");
		return responseDto;
		
	}
	
	@Override
	public AllFeedbackFormResponseDto getAllFeedbackFormForInterviewer(String interviewerName) {
		// TODO Auto-generated method stub
		List<FeedbackForm> allSavedForm =feedbackFormManagerRepository.findByInterviewerInfo_InterviewerName(interviewerName);
		if(allSavedForm.isEmpty())
		{
			throw new FeedbackNotFoundException("NO feedbackforms found !!! ");
		}
		else {
		logger.info("All feedback forms get successfully !!!");
		
		AllFeedbackFormResponseDto responseDto= new AllFeedbackFormResponseDto();
		responseDto.setForms(allSavedForm);
		responseDto.setResponseCode(HttpStatus.OK.value());
		responseDto.setResponseMessage("All feedback forms fetched successfully !!");
		return responseDto;
		}
		
	}

	@Override
	public ResponseDto updateFeedabackDetails(UpdateFeedbackFormRequestDto feedFormRequestDto) {
		// TODO Auto-generated method stub
		Optional<FeedbackForm> savedFormFromDB=feedbackFormManagerRepository.findById(feedFormRequestDto.getDocumentNo());
		if(savedFormFromDB.isEmpty())
		{
			throw new FeedbackNotFoundException("Data not found ! Invalid document no.");
		}
		else
		{
			savedFormFromDB.get().setCandidateInfo(feedFormRequestDto.getCandidateInfo());
			savedFormFromDB.get().setInterviewerInfo(feedFormRequestDto.getInterviewerInfo());
			savedFormFromDB.get().setTechnicalEvaluation(feedFormRequestDto.getTechnicalEvaluation());
			savedFormFromDB.get().setSoftSkillsEvaluation(feedFormRequestDto.getSoftSkillsEvaluation());
			savedFormFromDB.get().setHiringDecision(feedFormRequestDto.getHiringDecision());
			savedFormFromDB.get().setDateOfInterview(Date.from(feedFormRequestDto.getDateOfInterview().atStartOfDay(ZoneId.of("UTC")).toInstant()));
		
			feedbackFormManagerRepository.save(savedFormFromDB.get());
			logger.info("data updated successfully...");
			
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setResponseMessage("Data Updated successfully !!!");
			return responseDto;
		}
	}

	@Override
	public ResponseDto deleteFeedbackForm(String documentNo) {
		// TODO Auto-generated method stub
		Optional<FeedbackForm> savedFormFromDB=feedbackFormManagerRepository.findById(documentNo);
		if(savedFormFromDB.isEmpty())
		{
			throw new FeedbackNotFoundException("Data not found ! Invalid document no. !!");
		}
		else
		{
			feedbackFormManagerRepository.deleteById(documentNo);
			
			logger.info(documentNo+" form deleted from database successfully !!");
			
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setResponseMessage("Data deleted successfully !!!");
			return responseDto;
		}
	}
}
