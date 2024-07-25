package com.hcaptsys.rlservice;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.hcaptsys.rldto.LoginRequest;
import com.hcaptsys.rldto.LoginResponse;
import com.hcaptsys.rldto.PatientDTO;
import com.hcaptsys.rldto.RegistrationResponse;
import com.hcaptsys.rlmodel.Patient;
import com.hcaptsys.rlrepository.PatientRepository;
import com.hcaptsys.rlutil.DataManipulation;

import io.micrometer.core.instrument.config.validate.ValidationException;
import jakarta.transaction.Transactional;

@Service
//@Transactional
public class PatientServiceImpl implements PatientService, UserDetailsService {

	@Autowired
	PatientRepository patientRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

	@Autowired
	ModelMapper modelMapper;

	@Override
	public RegistrationResponse saveNewUserToDB(PatientDTO patientDTO) throws ValidationException {
		List<Patient> patientList = new ArrayList<Patient>();
		patientList = patientRepository.findAll();
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setEmailId(patientDTO.getEmailId());

		for (Patient p : patientList) {
			if (p.getEmailId().equalsIgnoreCase(patientDTO.getEmailId())) {
				logger.info("patient already registered");
				registrationResponse.setStatus("AlreadyRegistered");
				return registrationResponse;
			}
		}
		logger.info("saveNewUserToDB invoked to register patient");
		patientDTO.setPassword(passwordEncoder().encode(patientDTO.getPassword()));
		Patient validatedPatient = modelMapper.map(patientDTO, Patient.class);
		DataManipulation dataManipulation = new DataManipulation();
		validatedPatient.setFirstName(dataManipulation.updateFirstLetter(validatedPatient.getFirstName()));
		validatedPatient.setLastName(dataManipulation.updateFirstLetter(validatedPatient.getLastName()));
		patientRepository.save(validatedPatient);
		registrationResponse.setStatus("Registered");
		logger.info("saveNewUserToDB registered patient and returning response to controller");
		return registrationResponse;
	}

	@Override
	public LoginResponse loginUser(LoginRequest user) throws BadCredentialsException {
		logger.info("loginUser method invoked");
		Patient patientFromDB = patientRepository.findById(user.getEmailId()).orElse(null);
		if (patientFromDB != null && passwordEncoder().matches(user.getPassword(), patientFromDB.getPassword())) {
			LoginResponse loginResponse = new LoginResponse();
			loginResponse.setEmailId(patientFromDB.getEmailId());
			loginResponse.setStatus(true);
			loginResponse.setToken("DEMO");
			loginResponse.setFirstName(patientFromDB.getFirstName());
			loginResponse.setLastName(patientFromDB.getLastName());
			logger.info("login successfull");
			return loginResponse;
		}
		logger.info("Bad credentials");
		logger.error("Bad credentials");
		throw new BadCredentialsException(" Invalid Username or Password  !!");
	}

	@Override
	public UserDetails loadUserByUsername(String patientEmail) throws BadCredentialsException {
		logger.info("loadUserByUsername method invoked");
		Patient patientFromDB = patientRepository.findById(patientEmail).orElse(null);
		if (patientFromDB != null) {
			if (patientEmail.equalsIgnoreCase(patientFromDB.getEmailId())) {
				logger.info("successfully found in DB and matched user");
				return new User(patientFromDB.getEmailId(), patientFromDB.getPassword(), new ArrayList<>());
			}
		}
		logger.info("Patient email is not found he/she is not registered or have entered wrong details");
		throw new BadCredentialsException(" Invalid Username or Password  !!");
	}
}
