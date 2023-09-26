package com.feedbackService.utils;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.feedbackService.dto.FeedbackCandidateInfoDto;
import com.feedbackService.dto.FeedbackFileCandidateInfoDto;
import com.feedbackService.dto.FeedbackFileDecisionDto;
import com.feedbackService.dto.FeedbackFileSoftSkillsEvaluationDto;
import com.feedbackService.dto.FeedbackFileTechnicalEvaluationDto;
import com.feedbackService.dto.FeedbackFormRequestDto;
import com.feedbackService.dto.FeedbackInterviewerInfoDto;
import com.feedbackService.dto.FeedbackSoftSkillsEvaluationDto;
import com.feedbackService.dto.FeedbackTechnicalEvaluationDto;
import com.feedbackService.enums.HiringDecision;
import com.feedbackService.enums.Rating;
import com.feedbackService.exception.FailToParseDataException;
import com.feedbackService.mapper.AutoFeedbackMapper;
import com.feedbackService.model.FeedbackForm;
import com.feedbackService.service.IdManagerService;


@Component
public class ConvertExcelToJson {

	@Autowired
	IdManagerService idManagerService;
	
	private Logger logger=LoggerFactory.getLogger(ConvertExcelToJson.class);
	
	public List<FeedbackForm> ConvertToJson(MultipartFile file,String interviewerName,String interviewerDesignation) throws IOException, ParseException
	{
		try {
			XSSFWorkbook workBook=new XSSFWorkbook(file.getInputStream());
			List<FeedbackForm> dataList=new ArrayList<>();
			List<FeedbackFileCandidateInfoDto> candidateInfoList=new ArrayList<>();
			List<FeedbackFileTechnicalEvaluationDto> technicalEvalInfoList=new ArrayList<>();
			List<FeedbackFileSoftSkillsEvaluationDto> softSkillsEvalInfoList=new ArrayList<>();
			List<FeedbackFileDecisionDto> interviewDecisionInfoList=new ArrayList<>();
			
			// for candidate info
			XSSFSheet workSheet1=workBook.getSheetAt(0);
			XSSFRow header1 = workSheet1.getRow(0);
			for(int i=1;i<workSheet1.getPhysicalNumberOfRows();i++)
			{
				XSSFRow row=workSheet1.getRow(i);
				FeedbackFileCandidateInfoDto newCandidateInfo=new FeedbackFileCandidateInfoDto();
				for(int j=0;j<row.getPhysicalNumberOfCells();j++)
				{
					String h=header1.getCell(j).toString();
					logger.info(h);
					logger.info(row.getCell(j).toString());
					if(h.equals("Tempary_Form_No"))
					{
						newCandidateInfo.setTemparyDocumentNo(row.getCell(j).toString());
					}
					else if(h.equals("Candidate_Full_Name"))
					{
						newCandidateInfo.setCandidateFullName(row.getCell(j).toString());
					}
					else if(h.equals("Candidate_Position"))
					{
						newCandidateInfo.setCandidatePosition(row.getCell(j).toString());
					}
					else if(h.equals("Candidate_Division"))
					{
						newCandidateInfo.setCandidateDivision(row.getCell(j).toString());
					}
					else if(h.equals("Candidate_Total_Experience"))
					{
						newCandidateInfo.setCandidateTotalExperience(Double.valueOf(row.getCell(j).toString()).intValue());
					}
					else if(h.equals("Candidate_Relevant_Experience"))
					{
						newCandidateInfo.setCandidateRelevantExperience(Double.valueOf(row.getCell(j).toString()).intValue());
					}
					else if(h.equals("Candidate_Primary_Skills"))
					{
						List<String> primary_skills=Arrays.stream(row.getCell(j).toString().split(",")).toList() ;
						logger.info(primary_skills.toString());
						newCandidateInfo.setCandidatePrimarySkills(primary_skills);
					}
					else if(h.equals("Candidate_Secondary_Skills"))
					{
						List<String> secondary_skills=Arrays.stream(row.getCell(j).toString().split(",")).toList() ;
						logger.info(secondary_skills.toString());
						newCandidateInfo.setCandidateSecondarySkills(secondary_skills);
					}
					else
					{
						throw new FailToParseDataException("Something want to wrong in candidate info ! Fail to parse document data...");
					}
				}
				candidateInfoList.add(newCandidateInfo);
			}
			Stream.of(candidateInfoList).forEach(s->logger.info(s.toString()));
			
			// for Technical skills
			XSSFSheet workSheet2=workBook.getSheetAt(1);
			XSSFRow header2 = workSheet2.getRow(0);
			for(int i=1;i<workSheet2.getPhysicalNumberOfRows();i++)
			{
				XSSFRow row=workSheet2.getRow(i);
				FeedbackFileTechnicalEvaluationDto newtechnicalEvaluation=new FeedbackFileTechnicalEvaluationDto();
				for(int j=0;j<row.getPhysicalNumberOfCells();j++)
				{
					String h=header2.getCell(j).toString();
					logger.info(h);
					logger.info(row.getCell(j).toString());
					if(h.equals("Tempary_Form_No"))
					{
						newtechnicalEvaluation.setTemparyDocumentNo(row.getCell(j).toString());
					}
					else if(h.equals("Skill"))
					{
						newtechnicalEvaluation.setSkill(row.getCell(j).toString());
					}
					else if(h.equals("Rating"))
					{
						newtechnicalEvaluation.setRating(Rating.valueOf(row.getCell(j).toString()));
					}
					else if(h.equals("Comments"))
					{
						newtechnicalEvaluation.setComments(row.getCell(j).toString());
					}
					else
					{
						throw new FailToParseDataException("Something want to wrong in technical evaulation info ! Fail to parse document data...");
					}
				}
				technicalEvalInfoList.add(newtechnicalEvaluation);
			}
			logger.info(technicalEvalInfoList.toString());
			
			// for Soft skills
			XSSFSheet workSheet3=workBook.getSheetAt(2);
			XSSFRow header3 = workSheet3.getRow(0);
			for(int i=1;i<workSheet3.getPhysicalNumberOfRows();i++)
			{
				XSSFRow row=workSheet3.getRow(i);
				FeedbackFileSoftSkillsEvaluationDto newSoftSkillEvaluation=new FeedbackFileSoftSkillsEvaluationDto();
				for(int j=0;j<row.getPhysicalNumberOfCells();j++)
				{
					String h=header3.getCell(j).toString();
					logger.info(h);
					logger.info(row.getCell(j).toString());
					if(h.equals("Tempary_Form_No"))
					{
						newSoftSkillEvaluation.setTemparyDocumentNo(row.getCell(j).toString());
					}
					else if(h.equals("Skill"))
					{
						newSoftSkillEvaluation.setSkill(row.getCell(j).toString());
					}
					else if(h.equals("Rating"))
					{
						newSoftSkillEvaluation.setRating(Rating.valueOf(row.getCell(j).toString()));
					}
					else
					{
						throw new FailToParseDataException("Something want to wrong in soft skill evaluation info ! Fail to parse document data...");
					}
				}
				softSkillsEvalInfoList.add(newSoftSkillEvaluation);
			}
			logger.info(softSkillsEvalInfoList.toString());
			
			// for decision making
			XSSFSheet workSheet4=workBook.getSheetAt(3);
			XSSFRow header4 = workSheet4.getRow(0);
			for(int i=1;i<workSheet4.getPhysicalNumberOfRows();i++)
			{
				XSSFRow row=workSheet4.getRow(i);
				FeedbackFileDecisionDto newInterviewDecision=new FeedbackFileDecisionDto();
				for(int j=0;j<row.getPhysicalNumberOfCells();j++)
				{
					String h=header4.getCell(j).toString();
					logger.info(h);
					logger.info(row.getCell(j).toString());
					if(h.equals("Tempary_Form_No"))
					{
						newInterviewDecision.setTemparyDocumentNo(row.getCell(j).toString());
					}
					else if(h.equals("Hiring_Decision"))
					{
						newInterviewDecision.setHiringDecision(HiringDecision.valueOf(row.getCell(j).toString()));
					}
					else if(h.equals("Date_Of_Interview"))
					{
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
						newInterviewDecision.setDateOfInterview(LocalDate.parse(row.getCell(j).toString(),formatter));
					}
					else
					{
						throw new FailToParseDataException("Something want to wrong in interview decision info ! Fail to parse document data...");
					}
				}
				interviewDecisionInfoList.add(newInterviewDecision);
			}
			logger.info(interviewDecisionInfoList.toString());
			
			//for interviewer info
			FeedbackInterviewerInfoDto newInterviewerInfo=new FeedbackInterviewerInfoDto();
			newInterviewerInfo.setInterviewerName(interviewerName);
			newInterviewerInfo.setInterviewerDesignation(interviewerDesignation);
			
			// final prepare data list
			FeedbackFormRequestDto newFeedbackForm=new FeedbackFormRequestDto();
			for(FeedbackFileCandidateInfoDto fbfcid: candidateInfoList)
			{
				// for candidate info
				newFeedbackForm.setCandidateInfo(fbfcid);
				
				// for technical evaluation info
				List<FeedbackTechnicalEvaluationDto> newTechnicalEvaluationList=new ArrayList<>();
				for(FeedbackFileTechnicalEvaluationDto fbfted :technicalEvalInfoList)
				{
					if(fbfted.getTemparyDocumentNo().equals(fbfcid.getTemparyDocumentNo()))
					{
						newTechnicalEvaluationList.add(fbfted);
					}
				}
				newFeedbackForm.setTechnicalEvaluation(newTechnicalEvaluationList);
				
				// for soft skills evaluation info
				List<FeedbackSoftSkillsEvaluationDto> newSoftSkillEvaluationList=new ArrayList<>();
				for(FeedbackFileSoftSkillsEvaluationDto fbfssed: softSkillsEvalInfoList)
				{
					if(fbfssed.getTemparyDocumentNo().equals(fbfcid.getTemparyDocumentNo()))
					{
						newSoftSkillEvaluationList.add(fbfssed);
					}
				}
				newFeedbackForm.setSoftSkillsEvaluation(newSoftSkillEvaluationList);
				
				// for interview decision 
				Date tempDate= null;
				for(FeedbackFileDecisionDto fbfdd: interviewDecisionInfoList)
				{
					if(fbfdd.getTemparyDocumentNo().equals(fbfcid.getTemparyDocumentNo()))
					{
						newFeedbackForm.setHiringDecision(fbfdd.getHiringDecision());
						newFeedbackForm.setDateOfInterview(fbfdd.getDateOfInterview());
					}
				}
				
				// for interviewer info
				newFeedbackForm.setInterviewerInfo(newInterviewerInfo);
				
				// final prepare form 
				FeedbackForm feedbackForm=AutoFeedbackMapper.MAPPER.mapToFeedbackForm(newFeedbackForm);
				feedbackForm.setDateOfInterview(Date.from(newFeedbackForm.getDateOfInterview().atStartOfDay(ZoneId.of("UTC")).toInstant()));
				feedbackForm.setDocumentNo("EI/GL/AHMD/FORM/"+idManagerService.generateSequence(FeedbackForm.SEQUENCE_NAME));
				dataList.add(feedbackForm);
			}
			
			logger.info(dataList.toString());
			
			return dataList;
		}
		catch(IOException e)
		{
			e.getStackTrace();
		}
		return null;
	}
}
