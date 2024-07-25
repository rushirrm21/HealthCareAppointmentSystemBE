package com.hcaptsys.aptservicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hcaptsys.aptdto.AppointmentRequestDTO;
import com.hcaptsys.aptdto.AppointmentResponse;
import com.hcaptsys.aptdto.RequestTimeSlots;
import com.hcaptsys.aptdto.ResponseTimeSlots;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.aptemail.EmailServiceImpl;
import com.hcaptsys.aptmodel.Appointment;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.aptrepository.AppointmentRepository;
import com.hcaptsys.aptrepository.ProviderRepository;
import com.hcaptsys.aptservice.AppointmentServiceImpl;
import com.hcaptsys.exception.DataNotFoundException;
import com.hcaptsys.rlmodel.Patient;
import com.hcaptsys.rlrepository.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

	@InjectMocks
	AppointmentServiceImpl appointmentService;

	@Mock
	AppointmentRepository appointmentRepository;

	@Mock
	ProviderRepository providerRepository;

	@Mock
	PatientRepository patientRepository;

	@Mock
	EmailServiceImpl emailService;

	@Test
	public void changeAppointmentStatusByIdTest1() {
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-09-25";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);
		Optional<Appointment> apt1 = Optional.ofNullable(appointment);
		when(appointmentRepository.findById(1)).thenReturn(apt1);
		when(appointmentRepository.save(appointment)).thenReturn(appointment);
		emailService.sendEmail("rushi@gmail.com", "emailSubject", "emailBody");
		verify(emailService).sendEmail("rushi@gmail.com", "emailSubject", "emailBody");
		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes.setAppointmentStatus("Cancelled");
		assertEquals(appointmentService.changeAppointmentStatusById(1), aptRes);
	}

	@Test
	public void provideAvailableTimeSlotsTest1() {
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-09-25";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);
		List<Appointment> aptList = new ArrayList<>();
		aptList.add(appointment);
		when(appointmentRepository.findAll()).thenReturn(aptList);
		List<ResponseTimeSlots> timeSlots = new ArrayList<ResponseTimeSlots>();
		ResponseTimeSlots rts1 = new ResponseTimeSlots();
		ResponseTimeSlots rts2 = new ResponseTimeSlots();
		ResponseTimeSlots rts3 = new ResponseTimeSlots();
		ResponseTimeSlots rts4 = new ResponseTimeSlots();
		ResponseTimeSlots rts5 = new ResponseTimeSlots();
		ResponseTimeSlots rts6 = new ResponseTimeSlots();
		ResponseTimeSlots rts7 = new ResponseTimeSlots();
		ResponseTimeSlots rts8 = new ResponseTimeSlots();
		ResponseTimeSlots rts9 = new ResponseTimeSlots();
		rts1.setAppointmentTime("10:00:00");
		timeSlots.add(rts1);
		rts2.setAppointmentTime("12:00:00");
		timeSlots.add(rts2);
		rts3.setAppointmentTime("13:00:00");
		timeSlots.add(rts3);
		rts4.setAppointmentTime("14:00:00");
		timeSlots.add(rts4);
		rts5.setAppointmentTime("15:00:00");
		timeSlots.add(rts5);
		rts6.setAppointmentTime("16:00:00");
		timeSlots.add(rts6);
		rts7.setAppointmentTime("18:00:00");
		timeSlots.add(rts7);
		rts8.setAppointmentTime("19:00:00");
		timeSlots.add(rts8);
		rts9.setAppointmentTime("20:00:00");
		timeSlots.add(rts9);
		RequestTimeSlots requestTimeSlots = new RequestTimeSlots();
		requestTimeSlots.setProviderId(1);
		requestTimeSlots.setAppointmentDate("2023-09-25");
		assertEquals(appointmentService.provideAvailableTimeSlots(requestTimeSlots), timeSlots);
	}

	@Test
	public void provideAppointmentsTest1() throws DataNotFoundException {
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Optional<Provider> p1 = Optional.ofNullable(p);

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-10-25";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);

		List<Appointment> aptList = new ArrayList<>();
		aptList.add(appointment);

		List<ScheduledAppointments> saList = new ArrayList<>();
		ScheduledAppointments schApt = new ScheduledAppointments();
		schApt.setAppointmentId(1);
		schApt.setAppointmentStatus("Confirmed");
		schApt.setAppointmentWithDrSpecality("Neurologist");
		schApt.setAppointmentWithDr("Aniket Chaudhari");
		schApt.setAppointmentTime("11:00:00");
		schApt.setAppointmentDate("2023-10-25");
		saList.add(schApt);

		when(appointmentRepository.findAll()).thenReturn(aptList);
		when(providerRepository.findById(appointment.getProvider().getProviderId())).thenReturn(p1);
		assertEquals(appointmentService.provideAppointments("rushi@gmail.com", 1), saList);

	}

	@Test
	public void provideAppointmentsTest2() throws DataNotFoundException {
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Optional<Provider> p1 = Optional.ofNullable(p);

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-10-25";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);

		List<Appointment> aptList = new ArrayList<>();
		aptList.add(appointment);

		List<ScheduledAppointments> saList = new ArrayList<>();
		ScheduledAppointments schApt = new ScheduledAppointments();
		schApt.setAppointmentId(1);
		schApt.setAppointmentStatus("Confirmed");
		schApt.setAppointmentWithDrSpecality("Neurologist");
		schApt.setAppointmentWithDr("Aniket Chaudhari");
		schApt.setAppointmentTime("11:00:00");
		schApt.setAppointmentDate("2023-10-25");
		saList.add(schApt);

		when(appointmentRepository.findAll()).thenReturn(aptList);
		when(providerRepository.findById(appointment.getProvider().getProviderId())).thenReturn(p1);
		assertEquals(appointmentService.provideAppointments("rushi@gmail.com", 2), saList);

	}

	@Test
	public void testProvideAppointmentsWithNoAppointments() {
		// Arrange
		String emailId = "test@example.com";
		int funCheck = 1;
		List<Appointment> aptList = new ArrayList<>();

		when(appointmentRepository.findAll()).thenReturn(aptList);

		// Act and Assert
		assertThrows(DataNotFoundException.class, () -> appointmentService.provideAppointments(emailId, funCheck));
		verify(appointmentRepository).findAll();
	}

	@Test
	public void bookAnAppointmentTest1() {
		AppointmentRequestDTO aptReqDTO = new AppointmentRequestDTO();
		aptReqDTO.setPatientEmail("rushi@gmail.com");
		aptReqDTO.setProviderId(1);
		aptReqDTO.setAppointmentTime("11:00:00");
		aptReqDTO.setAppointmentDate("2023-07-22");
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");
		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-07-22";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);

		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes.setAppointmentStatus("BeforeCurrentTime");
		assertEquals(appointmentService.bookAnAppointment(aptReqDTO), aptRes);

	}

	@Test
	public void bookAnAppointmentTest2() {
		AppointmentRequestDTO aptReqDTO = new AppointmentRequestDTO();
		aptReqDTO.setPatientEmail("rushi@gmail.com");
		aptReqDTO.setProviderId(2);
		aptReqDTO.setAppointmentTime("11:00:00");
		aptReqDTO.setAppointmentDate("2023-07-22");

		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-07-22";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);

		List<Appointment> aptList = new ArrayList<Appointment>();
		aptList.add(appointment);
		when(appointmentRepository.findAll()).thenReturn(aptList);
		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes.setAppointmentStatus("SameDayTime");
		assertEquals(appointmentService.bookAnAppointment(aptReqDTO), aptRes);

	}

	@Test
	public void bookAnAppointmentTest3() {
		AppointmentRequestDTO aptReqDTO = new AppointmentRequestDTO();
		aptReqDTO.setPatientEmail("rushi@gmail.com");
		aptReqDTO.setProviderId(1);
		aptReqDTO.setAppointmentTime("11:00:00");
		aptReqDTO.setAppointmentDate("2023-10-22");

		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-09-22";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);

		List<Appointment> aptList = new ArrayList<Appointment>();
		aptList.add(appointment);
		when(appointmentRepository.findAll()).thenReturn(aptList);
		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes.setAppointmentStatus("SameProvider");
		assertEquals(appointmentService.bookAnAppointment(aptReqDTO), aptRes);

	}

	@Test
	public void bookAnAppointmentTest4() {
		AppointmentRequestDTO aptReqDTO = new AppointmentRequestDTO();
		aptReqDTO.setPatientEmail("rushi@gmail.com");
		aptReqDTO.setProviderId(1);
		aptReqDTO.setAppointmentTime("12:00:00");
		aptReqDTO.setAppointmentDate("2023-09-22");

		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Provider p2 = new Provider();
		p2.setProviderFirstName("Aniket");
		p2.setProviderLastName("Chaudhari");
		p2.setProviderId(2);
		p2.setProviderSpecailty("Neurologist");

		Optional<Provider> p2Mocked = Optional.ofNullable(p2);

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Patient patient2 = new Patient();
		patient2.setEmailId("rushi@gmail.com");
		patient2.setFirstName("Rushi");
		patient2.setLastName("Patil");
		patient2.setPassword("QWE@21rrm");

		Optional<Patient> patient2Mocked = Optional.ofNullable(patient2);

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-07-22";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);

		List<Appointment> aptList = new ArrayList<Appointment>();
		aptList.add(appointment);
		when(appointmentRepository.findAll()).thenReturn(aptList);
		when(patientRepository.findById(aptReqDTO.getPatientEmail())).thenReturn(patient2Mocked);
		when(providerRepository.findById(aptReqDTO.getProviderId())).thenReturn(p2Mocked);
		emailService.sendEmail("rushi@gmail.com", "emailSubject", "emailBody");
		verify(emailService).sendEmail("rushi@gmail.com", "emailSubject", "emailBody");
		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes.setAppointmentStatus("Confirmed");
		assertEquals(appointmentService.bookAnAppointment(aptReqDTO), aptRes);
	}

	@Test
	public void bookAnAppointmentTest5() {
		AppointmentRequestDTO aptReqDTO = new AppointmentRequestDTO();
		aptReqDTO.setPatientEmail("rushi@gmail.com");
		aptReqDTO.setProviderId(1);
		aptReqDTO.setAppointmentTime("11:00:00");
		aptReqDTO.setAppointmentDate("2023-12-22");
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");
		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		appointment.setAppointmentStatus("Confirmed");
		appointment.setPatient(patient);
		appointment.setProvider(p);
		String dateTimeString = "2023-07-22";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		appointment.setAppointmentDateTime(dateTime);

		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes.setAppointmentStatus("AfterThreeMonths");
		assertEquals(appointmentService.bookAnAppointment(aptReqDTO), aptRes);

	}
}