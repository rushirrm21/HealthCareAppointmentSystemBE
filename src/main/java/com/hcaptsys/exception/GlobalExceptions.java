package com.hcaptsys.exception;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.rldto.LoginResponse;
import com.hcaptsys.rldto.RegistrationResponse;
import jakarta.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptions {

	Logger logger = LoggerFactory.getLogger(GlobalExceptions.class);

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<LoginResponse> exceptionHandler1() {
		logger.error("Bad Credentials Exception occured");
		LoginResponse response = LoginResponse.builder().token("No Token").emailId("No Email").status(false)
				.firstName(null).lastName(null).build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<RegistrationResponse> exceptionHandler2() {
		logger.error("Validation Exception occured because of invalid data");
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setEmailId(null);
		registrationResponse.setStatus("NotRegistered");
		return new ResponseEntity<>(registrationResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<List<ScheduledAppointments>> exceptionHandler3() {
		logger.error("No appointments data found");
		List<ScheduledAppointments> saList = null;
		return new ResponseEntity<List<ScheduledAppointments>>(saList, HttpStatus.NOT_FOUND);
	}
}
