package com.emailService.serviceImpl;

import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.emailService.dto.ForgetPasswordResponseDto;
import com.emailService.dto.ResponseDto;
import com.emailService.dto.SendMailRequestDto;
import com.emailService.exception.ConfigMailNotFoundException;
import com.emailService.exception.MailAdminNotFoundException;
import com.emailService.exception.MailTemplateNotFoundexception;
import com.emailService.model.ConfigMail;
import com.emailService.model.MailAdmin;
import com.emailService.model.MailTemplate;
import com.emailService.repository.ConfigMailRepository;
import com.emailService.repository.MailAdminRepository;
import com.emailService.repository.MailTemplateRepository;
import com.emailService.service.ConfigMailManagerService;
import com.emailService.service.EmailManagerService;
import com.emailService.service.MailAdminManagerService;
import com.emailService.service.MailTemplateManagerService;
import com.emailService.utils.RandomStringGenerate;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailManagerServiceImpl implements EmailManagerService
{

	@Autowired
	private MailTemplateRepository mailTemplateRepository;
	
	@Autowired
	private MailAdminRepository mailAdminRepository;
	
	@Autowired
	private ConfigMailRepository configMailRepository;
	
	@Autowired
	private RandomStringGenerate randomStringGenerate;
	
	private Logger logger=LoggerFactory.getLogger(EmailManagerServiceImpl.class);
	
	@Override
	public ResponseDto sendMail(SendMailRequestDto sendMailRequestDto) throws Exception {
		// TODO Auto-generated method stub
		Optional<ConfigMail> configMail=configMailRepository.findByConfigFor(sendMailRequestDto.getMailFor());
		logger.info(configMail.toString());
		
		if(configMail.isEmpty())
		{
			throw new ConfigMailNotFoundException("Requested configuration not found...");
		}
		else
		{
		Optional<MailAdmin> mailAdmin=mailAdminRepository.findByMailAdminFor(sendMailRequestDto.getMailFor());
		logger.info(mailAdmin.toString());
		if(mailAdmin.isEmpty())
		{
			throw new MailAdminNotFoundException("Requested mail admin credentials not found...");
		}
		else
		{
		Optional<MailTemplate> mailTemplate=mailTemplateRepository.findByTemplateFor(sendMailRequestDto.getMailFor());	
		logger.info(mailTemplate.toString());	
		
		if(mailTemplate.isEmpty())
		{
			throw new MailTemplateNotFoundexception("Requested mail template not found...");
		}
		else
		{
		
		Properties properties = System.getProperties();
		
		properties.put("mail.smtp.host",configMail.get().getMailHost());
		properties.put("mail.smtp.ssl.enable",configMail.get().isMailSmtpSslEnable());
		properties.put("mail.smtp.auth",configMail.get().isMailSmtpAuthEnable());
		properties.put("mail.smtp.port",configMail.get().getMailPort());
		properties.put("mail.smtp.starttls.enable", configMail.get().isEnableSmptStarttls());
		properties.put("mail.smtp.socketFactory.port",configMail.get().getMailSmptSocketFactoryPort());
		
		Session session = Session.getInstance(properties,new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				logger.info(mailAdmin.get().getMailPassword());
				return new PasswordAuthentication(mailAdmin.get().getMailFrom(), mailAdmin.get().getMailPassword());
			}
		});

		session.setDebug(true);
		
		//for forget password
		String newPassword=null;
		if(sendMailRequestDto.getMailFor().equals("Forget_Password"))
		{
			String[] mailBody=mailTemplate.get().getMailBody().split("\\$");
			newPassword=randomStringGenerate.getAlphaNumericString(10);
			String newMailBody=mailBody[0]+newPassword+mailBody[1];
			mailTemplate.get().setMailBody(newMailBody);
			logger.info("For forget passord :"+newMailBody);
		}
		logger.info("For other service :"+mailTemplate.get().getMailBody());
		// Creating a mime message
        MimeMessage mimeMessage = new MimeMessage(session);
 
        mimeMessage.setFrom(mailAdmin.get().getMailFrom());
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(sendMailRequestDto.getMailTo()));
        mimeMessage.setText(mailTemplate.get().getMailBody());
        mimeMessage.setSubject(mailTemplate.get().getMailSubject());
 
        // Sending the mail
        Transport.send(mimeMessage);
        logger.info("mail sent successfully...");
		
		if(sendMailRequestDto.getMailFor().equals("Forget_Password"))
		{
			ForgetPasswordResponseDto forgetPasswordResponseDto=new ForgetPasswordResponseDto();
			forgetPasswordResponseDto.setResponseCode(HttpStatus.OK.value());
			forgetPasswordResponseDto.setMessage("Mail sent Successfully...");
			forgetPasswordResponseDto.setNewPassword(newPassword);
			return forgetPasswordResponseDto;
		}
		else
		{
			ResponseDto responseDto=new ResponseDto();
			responseDto.setResponseCode(HttpStatus.OK.value());
			responseDto.setMessage("Mail sent Successfully...");
			return responseDto;     
		}
		}
		}
	  }
	}
}
