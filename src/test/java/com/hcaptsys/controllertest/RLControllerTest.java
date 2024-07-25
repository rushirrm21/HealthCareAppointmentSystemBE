package com.hcaptsys.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.hcaptsys.controller.RLController;
import com.hcaptsys.rldto.LoginRequest;
import com.hcaptsys.rldto.LoginResponse;
import com.hcaptsys.rldto.PatientDTO;
import com.hcaptsys.rldto.RegistrationResponse;
import com.hcaptsys.rlservice.AuthenticationServiceImpl;
import com.hcaptsys.rlservice.PatientServiceImpl;
import com.hcaptsys.security.JwtHelper;

@ExtendWith(MockitoExtension.class)
public class RLControllerTest {

	@InjectMocks
	RLController rlController;

	@Mock
	AuthenticationServiceImpl authenticationService;

	@Mock
	PatientServiceImpl patientService;

	@Mock
	UserDetailsService userDetailsService;

	@Spy
	ModelMapper modelMapper;

	@Spy
	AuthenticationManager authenticationManager;

	@Mock
	private JwtHelper helper;

	@Test
	public void registerUserTest1() {
		PatientDTO patient = new PatientDTO();
		RegistrationResponse regRes = new RegistrationResponse();
		ResponseEntity<RegistrationResponse> entity = new ResponseEntity<RegistrationResponse>(regRes, HttpStatus.OK);
		when(patientService.saveNewUserToDB(patient)).thenReturn(regRes);
		assertEquals(rlController.registerUser(patient), entity);
	}

	@Test
	public void loginUserTest1() throws Exception {
		UserDetails user = new User("rushi@gmail.com", "QWE@21rrm", new ArrayList<>());
		when(userDetailsService.loadUserByUsername("rushi@gmail.com")).thenReturn(user);
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmailId("rushi@gmail.com");
		loginRequest.setPassword("QWE@21rr");
		LoginResponse response = new LoginResponse();
		response.setEmailId("rushi@gmail.com");
		response.setStatus(true);
		response.setToken(null);
		response.setFirstName(null);
		response.setLastName(null);
		when(patientService.loginUser(loginRequest)).thenReturn(response);
		LoginResponse response2 = new LoginResponse();
		response2.setEmailId("rushi@gmail.com");
		response2.setStatus(true);
		response2.setToken(null);
		response2.setFirstName(null);
		response2.setLastName(null);
		ResponseEntity<LoginResponse> entity = new ResponseEntity<>(response2, HttpStatus.OK);
		assertEquals(rlController.loginUser(loginRequest), entity);
	}
}
