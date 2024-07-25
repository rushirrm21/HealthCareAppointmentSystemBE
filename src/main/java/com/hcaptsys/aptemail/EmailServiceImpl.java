package com.hcaptsys.aptemail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	private final JavaMailSender mailSender;

	Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	public EmailServiceImpl(JavaMailSender mailSender) {
		logger.info("Executing constructor of EmailServiceImpl");
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(String to, String subject, String message) {
		logger.info("SendEmail method invoked for send email from EmailServiceImpl");
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("healthcareappointmentsystem@gmail.com");
		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);

		this.mailSender.send(simpleMailMessage);
		logger.info("SendEmail method succesffully sent email from EmailServiceImpl");

	}

}
