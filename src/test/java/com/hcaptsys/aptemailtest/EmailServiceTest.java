package com.hcaptsys.aptemailtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import com.hcaptsys.aptemail.EmailServiceImpl;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

	private EmailServiceImpl emailService;

	@Mock
	private JavaMailSender mailSender;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		emailService = new EmailServiceImpl(mailSender);
	}

	@Test
	void sendEmailShouldSendEmailSuccessfully() {
		// Arrange
		String to = "test@example.com";
		String subject = "Test Subject";
		String message = "Test Message";
		SimpleMailMessage expectedMailMessage = new SimpleMailMessage();
		expectedMailMessage.setFrom("healthcareappointmentsystem@gmail.com");
		expectedMailMessage.setTo(to);
		expectedMailMessage.setSubject(subject);
		expectedMailMessage.setText(message);
		emailService.sendEmail(to, subject, message);
		verify(mailSender, times(1)).send(expectedMailMessage);
	}
}
