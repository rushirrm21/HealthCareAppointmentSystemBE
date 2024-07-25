package com.hcaptsys.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hcaptsys.aptdto.AppointmentRequestDTO;
import com.hcaptsys.aptdto.AppointmentResponse;
import com.hcaptsys.aptdto.ProviderDTO;
import com.hcaptsys.aptdto.RequestTimeSlots;
import com.hcaptsys.aptdto.ResponseTimeSlots;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.aptservice.AppointmentServiceImpl;
import com.hcaptsys.aptservice.ProviderService;
import com.hcaptsys.exception.DataNotFoundException;
import com.hcaptsys.trtdto.TreatmentDTO;
import com.hcaptsys.trtservice.TreatmentService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/healthcareappsys/api/v1")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class APTController {

	@Autowired
	AppointmentServiceImpl appointmentService;

	@Autowired
	ProviderService providerService;

	@Autowired
	TreatmentService treatmentService;

	Logger logger = LoggerFactory.getLogger(APTController.class);

	// GetMapping for fetching the providers available
	@GetMapping("/providers")
	public ResponseEntity<List<ProviderDTO>> getAllProviders() {
		logger.info("@GetMapping getAllProviders method invoked");
		List<ProviderDTO> providers = providerService.getProviders();
		logger.info("returning response to frontend from getAllProviders");
		return new ResponseEntity<List<ProviderDTO>>(providers, HttpStatus.OK);
	}

	//PostMapping for booking the appointment with provider
	@PostMapping("/bookappointment")
	public ResponseEntity<AppointmentResponse> bookAppointment(
			@RequestBody AppointmentRequestDTO appointmentRequestDTO) {
		logger.info("@PostMapping bookAppointment method invoked");
		AppointmentResponse aptResponse = appointmentService.bookAnAppointment(appointmentRequestDTO);
		logger.info("returning response to frontend from bookAppointment");
		return new ResponseEntity<>(aptResponse, HttpStatus.OK);
	}

	//PostMaping for taking provider name and date, the giving them the available time slots available 
	@PostMapping("/appointmenttimeslots")
	public ResponseEntity<List<ResponseTimeSlots>> getTimeSlots(@RequestBody RequestTimeSlots requestTimeSlots) {
		logger.info("@PostMapping getTimeSlots method invoked");
		List<ResponseTimeSlots> timeSlotsList = appointmentService.provideAvailableTimeSlots(requestTimeSlots);
		logger.info("returning response to frontend from getTimeSlots");
		return new ResponseEntity<List<ResponseTimeSlots>>(timeSlotsList, HttpStatus.OK);
	}

	//GetMapping for showing the upcoming appointments of the patient with providers
	@GetMapping("/upcomingappointments/{emailId}")
	public ResponseEntity<List<ScheduledAppointments>> getScheduledAppointments(@PathVariable String emailId)
			throws DataNotFoundException {
		logger.info("@GetMapping getScheduledAppointments method invoked");
		List<ScheduledAppointments> saList = new ArrayList<>();
		saList = appointmentService.provideAppointments(emailId, 1);
		logger.info("returning response to frontend from getScheduledAppointments");
		return new ResponseEntity<List<ScheduledAppointments>>(saList, HttpStatus.OK);
	}

	//PutMapping for the patient who cancels the appointment then we can change the status to cancelled
	@PutMapping("/updateappointment/{appointmentId}")
	public ResponseEntity<AppointmentResponse> deleteAppointment(@PathVariable String appointmentId) {
		logger.info("@DeleteMapping deleteAppointment method invoked");
		int aptId = Integer.parseInt(appointmentId);
		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes = appointmentService.changeAppointmentStatusById(aptId);
		logger.info("returning response to frontend from deleteAppointment");
		return new ResponseEntity<AppointmentResponse>(aptRes, HttpStatus.OK);
	}

	//GetMapping for fetching all the appointments of the patient
	@GetMapping("/appointments/{emailId}")
	public ResponseEntity<List<ScheduledAppointments>> getAllAppointments(@PathVariable String emailId)
			throws DataNotFoundException {
		logger.info("@GetMapping getAllAppointments method invoked");
		List<ScheduledAppointments> saList = new ArrayList<>();
		saList = appointmentService.provideAppointments(emailId, 2);
		logger.info("returning response to frontend from getAllAppointments");
		return new ResponseEntity<List<ScheduledAppointments>>(saList, HttpStatus.OK);
	}

	//GetMapping for fetching the treatment history of the patient
	@GetMapping("/treatmenthistory/{emailId}")
	public ResponseEntity<List<TreatmentDTO>> getTreatmentHistory(@PathVariable String emailId)
			throws DataNotFoundException {
		logger.info("@GetMapping getTreatmentHistory method invoked");
		List<TreatmentDTO> trtList = new ArrayList<>();
		trtList = treatmentService.getTreatmentDetails(emailId);
		logger.info("returning response to frontend from getTreatmentHistory");
		return new ResponseEntity<List<TreatmentDTO>>(trtList, HttpStatus.OK);
	}

}
