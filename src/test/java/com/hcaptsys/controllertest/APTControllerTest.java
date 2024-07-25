package com.hcaptsys.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.hcaptsys.aptdto.AppointmentRequestDTO;
import com.hcaptsys.aptdto.AppointmentResponse;
import com.hcaptsys.aptdto.ProviderDTO;
import com.hcaptsys.aptdto.RequestTimeSlots;
import com.hcaptsys.aptdto.ResponseTimeSlots;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.aptservice.AppointmentServiceImpl;
import com.hcaptsys.aptservice.ProviderServiceImpl;
import com.hcaptsys.controller.APTController;
import com.hcaptsys.exception.DataNotFoundException;
import com.hcaptsys.trtdto.TreatmentDTO;
import com.hcaptsys.trtservice.TreatmentServiceImpl;

@ExtendWith(MockitoExtension.class)
public class APTControllerTest {

	@InjectMocks
	APTController aptController;

	@Mock
	AppointmentServiceImpl appointmentService;

	@Mock
	ProviderServiceImpl providerService;

	@Mock
	TreatmentServiceImpl treatmentService;

	@Spy
	ModelMapper modelMapper;

	AppointmentResponse appointmentResponse = new AppointmentResponse();
	AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();

	@Test
	public void getAllProvidersTest1() {
		List<ProviderDTO> providers = new ArrayList<>();
		when(providerService.getProviders()).thenReturn(providers);
		ResponseEntity<List<ProviderDTO>> entity = new ResponseEntity<List<ProviderDTO>>(providers, HttpStatus.OK);
		assertEquals(aptController.getAllProviders(), entity);
	}

	@Test
	public void bookAppointmentTest1() {
		appointmentResponse.setAppointmentStatus("Confirmed");
		ResponseEntity<AppointmentResponse> entity = new ResponseEntity<AppointmentResponse>(appointmentResponse,
				HttpStatus.OK);
		when(appointmentService.bookAnAppointment(appointmentRequestDTO)).thenReturn(appointmentResponse);
		assertEquals(aptController.bookAppointment(appointmentRequestDTO), entity);
	}

	@Test
	public void bookAppointmentTest2() {
		appointmentResponse.setAppointmentStatus("SameDayTime");
		ResponseEntity<AppointmentResponse> entity = new ResponseEntity<AppointmentResponse>(appointmentResponse,
				HttpStatus.OK);
		when(appointmentService.bookAnAppointment(appointmentRequestDTO)).thenReturn(appointmentResponse);
		assertEquals(aptController.bookAppointment(appointmentRequestDTO), entity);
	}

	@Test
	public void bookAppointmentTest3() {
		appointmentResponse.setAppointmentStatus("Declined");
		ResponseEntity<AppointmentResponse> entity = new ResponseEntity<AppointmentResponse>(appointmentResponse,
				HttpStatus.OK);
		when(appointmentService.bookAnAppointment(appointmentRequestDTO)).thenReturn(appointmentResponse);
		assertEquals(aptController.bookAppointment(appointmentRequestDTO), entity);
	}

	@Test
	public void getTimeSlotsTest1() {
		List<ResponseTimeSlots> timeSlotsList = new ArrayList<ResponseTimeSlots>();
		RequestTimeSlots requestTimeSlots = new RequestTimeSlots();
		when(appointmentService.provideAvailableTimeSlots(requestTimeSlots)).thenReturn(timeSlotsList);
		ResponseEntity<List<ResponseTimeSlots>> entity = new ResponseEntity<List<ResponseTimeSlots>>(timeSlotsList,
				HttpStatus.OK);
		assertEquals(aptController.getTimeSlots(requestTimeSlots), entity);
	}

	@Test
	public void getScheduledAppointments1() throws DataNotFoundException {
		List<ScheduledAppointments> saList = new ArrayList<>();
		when(appointmentService.provideAppointments("rushi@gmail.com", 1)).thenReturn(saList);
		ResponseEntity<List<ScheduledAppointments>> entity = new ResponseEntity<List<ScheduledAppointments>>(saList,
				HttpStatus.OK);
		assertEquals(aptController.getScheduledAppointments("rushi@gmail.com"), entity);
	}

	@Test
	public void getScheduledAppointments2() throws DataNotFoundException {
		when(appointmentService.provideAppointments("rushi@gmail.com", 1)).thenThrow(DataNotFoundException.class);
		assertThrows(DataNotFoundException.class, ()->aptController.getScheduledAppointments("rushi@gmail.com") );
	}

	@Test
	public void deleteAppointment() {
		AppointmentResponse aptRes = new AppointmentResponse();
		when(appointmentService.changeAppointmentStatusById(1)).thenReturn(aptRes);
		ResponseEntity<AppointmentResponse> entity = new ResponseEntity<AppointmentResponse>(aptRes, HttpStatus.OK);
		assertEquals(aptController.deleteAppointment("1"), entity);
	}

	@Test
	public void getAllAppointments1() throws DataNotFoundException {
		List<ScheduledAppointments> saList = new ArrayList<>();
		when(appointmentService.provideAppointments("rushi@gmail.com", 2)).thenReturn(saList);
		ResponseEntity<List<ScheduledAppointments>> entity = new ResponseEntity<List<ScheduledAppointments>>(saList,
				HttpStatus.OK);
		assertEquals(aptController.getAllAppointments("rushi@gmail.com"), entity);
	}

	@Test
	public void getAllAppointments2() throws DataNotFoundException {
		when(appointmentService.provideAppointments("rushi@gmail.com", 2)).thenThrow(DataNotFoundException.class);
		assertThrows(DataNotFoundException.class, ()->aptController.getAllAppointments("rushi@gmail.com") );
	}

	@Test
	public void getTreatmentHistory1() throws DataNotFoundException {
		List<TreatmentDTO> tList = new ArrayList<>();
		when(treatmentService.getTreatmentDetails("rushi@gmail.com")).thenReturn(tList);
		ResponseEntity<List<TreatmentDTO>> entity = new ResponseEntity<List<TreatmentDTO>>(tList, HttpStatus.OK);
		assertEquals(aptController.getTreatmentHistory("rushi@gmail.com"), entity);
	}

	@Test
	public void getTreatmentHistory2() throws DataNotFoundException {
		when(treatmentService.getTreatmentDetails("rushi@gmail.com")).thenThrow(DataNotFoundException.class);
		assertThrows(DataNotFoundException.class, ()->aptController.getTreatmentHistory("rushi@gmail.com") );
	}
}