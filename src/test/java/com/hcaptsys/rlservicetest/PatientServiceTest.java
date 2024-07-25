package com.hcaptsys.rlservicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.hcaptsys.rldto.LoginRequest;
import com.hcaptsys.rldto.LoginResponse;
import com.hcaptsys.rldto.PatientDTO;
import com.hcaptsys.rldto.RegistrationResponse;
import com.hcaptsys.rlmodel.Patient;
import com.hcaptsys.rlrepository.PatientRepository;
import com.hcaptsys.rlservice.PatientServiceImpl;
import io.micrometer.core.instrument.config.validate.ValidationException;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

	@InjectMocks
	PatientServiceImpl patientService;

	@Mock
	PatientRepository patientRepository;

	@Mock
	ModelMapper modelMapper;

	@Spy
	PasswordEncoder passwordEncoder;

	@Test
	public void saveNewUserToDBTest1() {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient p = new Patient();
		p.setEmailId("ru@gmail.com");
		p.setPassword("QWE@21rrm");
		p.setFirstName("Rushi");
		p.setLastName("Patil");
		patientList.add(p);
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setEmailId("rushi@gmail.com");
		patientDTO.setPassword("QWE@21rrm");
		patientDTO.setFirstName("Rushi");
		patientDTO.setLastName("Patil");
		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setPassword("$2a$10$bN9ckDPFef22lysK1i2reeJF0t/oi7LbcQj9z4gHJvQQFNSGYwsZS");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setEmailId("rushi@gmail.com");
		registrationResponse.setStatus("Registered");
		when(patientRepository.findAll()).thenReturn(patientList);
		when(modelMapper.map(patientDTO, Patient.class)).thenReturn(patient);
		when(patientRepository.save(patient)).thenReturn(patient);
		assertEquals(patientService.saveNewUserToDB(patientDTO), registrationResponse);
	}

	@Test
	public void saveNewUserToDBTest2() {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient p = new Patient();
		p.setEmailId("ru@gmail.com");
		p.setPassword("QWE@21rrm");
		p.setFirstName("Rushi");
		p.setLastName("Patil");
		patientList.add(p);
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setEmailId("rushi@gmail.com");
		patientDTO.setPassword("QWE@21rrm");
		patientDTO.setFirstName("Rushi");
		patientDTO.setLastName("Patil");
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setEmailId("rushi@gmail.com");
		registrationResponse.setStatus("Registered");
		when(patientRepository.findAll()).thenReturn(patientList);
		when(modelMapper.map(patientDTO, Patient.class)).thenThrow(ValidationException.class);
		assertThrows(ValidationException.class, () -> patientService.saveNewUserToDB(patientDTO));
	}

	@Test
	public void saveNewUserToDBTest3() {
		List<Patient> patientList = new ArrayList<Patient>();
		Patient p = new Patient();
		p.setEmailId("rushi@gmail.com");
		p.setPassword("QWE@21rrm");
		p.setFirstName("Rushi");
		p.setLastName("Patil");
		patientList.add(p);
		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setEmailId("rushi@gmail.com");
		patientDTO.setPassword("QWE@21rrm");
		patientDTO.setFirstName("Rushi");
		patientDTO.setLastName("Patil");
		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setPassword("$2a$10$bN9ckDPFef22lysK1i2reeJF0t/oi7LbcQj9z4gHJvQQFNSGYwsZS");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		RegistrationResponse registrationResponse = new RegistrationResponse();
		registrationResponse.setEmailId("rushi@gmail.com");
		registrationResponse.setStatus("AlreadyRegistered");
		when(patientRepository.findAll()).thenReturn(patientList);
		assertEquals(patientService.saveNewUserToDB(patientDTO), registrationResponse);
	}

	@Test
	public void loadUserByUsernameTest1() {
		Optional<Patient> patientFromDB = Optional.ofNullable(new Patient("rushi@gmail.com",
				"$10$bN9ckDPFef22lysK1i2reeJF0t/oi7LbcQj9z4gHJvQQFNSGYwsZS", "Rushi", "Patil"));
		when(patientRepository.findById("rushi@gmail.com")).thenReturn(patientFromDB);
		UserDetails user = new User("rushi@gmail.com", "$10$bN9ckDPFef22lysK1i2reeJF0t/oi7LbcQj9z4gHJvQQFNSGYwsZS",
				new ArrayList<>());
		assertEquals(patientService.loadUserByUsername("rushi@gmail.com"), user);
	}

	@Test
	public void loadUserByUsernameTest2() {
		Optional<Patient> patientFromDB = Optional.ofNullable(new Patient());
		when(patientRepository.findById("rushi@gmail.com")).thenReturn(patientFromDB);
		assertThrows(BadCredentialsException.class, () -> patientService.loadUserByUsername("rushi@gmail.com"));
	}

	@Test
	public void loginUserTest1() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmailId("rushi@gmail.com");
		loginRequest.setPassword("QWE@21rrm");
		Optional<Patient> patientFromDB = Optional.ofNullable(new Patient());
		when(patientRepository.findById("rushi@gmail.com")).thenReturn(patientFromDB);
		assertThrows(BadCredentialsException.class, () -> patientService.loginUser(loginRequest));
	}

	@Test
	public void loginUserTest2() {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmailId("rushi@gmail.com");
		loginRequest.setPassword("QWE@21rrm");
		Patient patientFromDB = new Patient();
		patientFromDB.setEmailId("rushi@gmail.com");
		patientFromDB.setPassword("$2a$10$zT.zf53qA5DIbY97JcaEjeDES8ACkjNA7aABWm8PO7Ly47FiSFyIa");
		patientFromDB.setFirstName("Rushi");
		patientFromDB.setLastName("Patil");
		Optional<Patient> patientFromDBMocked = Optional.ofNullable(patientFromDB);
		when(patientRepository.findById("rushi@gmail.com")).thenReturn(patientFromDBMocked);
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setEmailId("rushi@gmail.com");
		loginResponse.setStatus(true);
		loginResponse.setToken("DEMO");
		loginResponse.setFirstName("Rushi");
		loginResponse.setLastName("Patil");
		assertEquals(patientService.loginUser(loginRequest), loginResponse);
	}
}
