package com.hcaptsys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcaptsys.rldto.LoginRequest;
import com.hcaptsys.rldto.LoginResponse;
import com.hcaptsys.rldto.PatientDTO;
import com.hcaptsys.rldto.RegistrationResponse;
import com.hcaptsys.rlservice.AuthenticationService;
import com.hcaptsys.rlservice.PatientService;
import com.hcaptsys.security.JwtHelper;

@RestController
@RequestMapping("/healthcareappsys/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RLController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public AuthenticationService authenticationService;

	@Autowired
	private JwtHelper helper;

	@Autowired
	PatientService patientService;

	Logger logger = LoggerFactory.getLogger(RLController.class);

	//PostMapping for registering new patient
	@PostMapping("/patientregistration")
	public ResponseEntity<RegistrationResponse> registerUser(@RequestBody PatientDTO patient) {
		logger.info("registerUser is invoked to register user");
		RegistrationResponse regRes = patientService.saveNewUserToDB(patient);
		logger.info("User Registered returning response from next line to frontend");
		return new ResponseEntity<RegistrationResponse>(regRes, HttpStatus.OK);
	}

	//PostMapping for patient login
	@PostMapping("/patientlogin")
	public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
		logger.info("loginUser method is invoked to login user and generate token");
		authenticationService.doAuthenticate(loginRequest.getEmailId(), loginRequest.getPassword());
		logger.info("Login credentials are authenticated successfully");
		UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmailId());
		String token = this.helper.generateToken(userDetails);
		LoginResponse res = patientService.loginUser(loginRequest);
		LoginResponse response = LoginResponse.builder().token(token).emailId(userDetails.getUsername()).status(true)
				.firstName(res.getFirstName()).lastName(res.getLastName()).build();
		logger.info("Token generated returing response to from next line to frontend");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}