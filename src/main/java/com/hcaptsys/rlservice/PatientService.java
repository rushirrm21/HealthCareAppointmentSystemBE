package com.hcaptsys.rlservice;

import org.springframework.security.authentication.BadCredentialsException;
import com.hcaptsys.rldto.LoginRequest;
import com.hcaptsys.rldto.LoginResponse;
import com.hcaptsys.rldto.PatientDTO;
import com.hcaptsys.rldto.RegistrationResponse;

public interface PatientService {

	public RegistrationResponse saveNewUserToDB(PatientDTO patientDTO);

	public LoginResponse loginUser(LoginRequest user) throws BadCredentialsException;
}
