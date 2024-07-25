package com.hcaptsys.aptservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcaptsys.aptdto.AppointmentRequestDTO;
import com.hcaptsys.aptdto.AppointmentResponse;
import com.hcaptsys.aptdto.RequestTimeSlots;
import com.hcaptsys.aptdto.ResponseTimeSlots;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.aptemail.EmailService;
import com.hcaptsys.aptmodel.Appointment;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.aptrepository.AppointmentRepository;
import com.hcaptsys.aptrepository.ProviderRepository;
import com.hcaptsys.aptutil.AppointmentDataExtraction;
import com.hcaptsys.exception.DataNotFoundException;
import com.hcaptsys.rlmodel.Patient;
import com.hcaptsys.rlrepository.PatientRepository;
import jakarta.transaction.Transactional;

@Service
//@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	ProviderRepository providerRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	EmailService emailService;

	Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	//Cancel appointment method
	@Override
	public AppointmentResponse changeAppointmentStatusById(int appointmentId) {
		logger.info("deleteAppointment method invoked from AppointmentServiceImpl to delete an appointment ");
		Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
		appointment.setAppointmentStatus("Cancelled");
		appointmentRepository.save(appointment);
		String emailTo = appointment.getPatient().getEmailId();
		String emailSubject = "Appointment booking cancelled";
		String emailBody = "Hello " + appointment.getPatient().getFirstName() + " "
				+ appointment.getPatient().getLastName() + ", your appointment with Dr."
				+ appointment.getProvider().getProviderFirstName() + " "
				+ appointment.getProvider().getProviderLastName() + " having "
				+ appointment.getProvider().getProviderSpecailty() + " as speciality, on date and time "
				+ appointment.getAppointmentDateTime() + " is cancelled !" + "\n\n"
				+ "Your healt is our responsibility !" + "\n\n\n" + "Thanks and Regards," + "\n"
				+ "Healthcare Appointment System";
		emailService.sendEmail(emailTo, emailSubject, emailBody);
		logger.info("Appointment deleted");
		AppointmentResponse aptRes = new AppointmentResponse();
		aptRes.setAppointmentStatus("Cancelled");
		logger.info("Returning response to controller");
		return aptRes;
	}

	//providing the available time slots method
	@Override
	public List<ResponseTimeSlots> provideAvailableTimeSlots(RequestTimeSlots requestTimeSlots) {
		logger.info(
				"provideAvailableTimeSlots method invoked to provide Available time slots to user of specific provider");
		List<Appointment> aptList = new ArrayList<>();
		aptList = appointmentRepository.findAll();
		AppointmentDataExtraction aptDE = new AppointmentDataExtraction();
		logger.info("returning response to controller with available time slots");
		return aptDE.showAvailableTimeSlotsBasedOnDateProvider(aptList, requestTimeSlots);
	}

	//method for booking appointment
	@Override
	public AppointmentResponse bookAnAppointment(AppointmentRequestDTO appointmentRequestDTO) {
		logger.info("bookAnAppointment method invoked from AppointmentServiceImpl");
		int flag = 0;
		AppointmentResponse aptRes = new AppointmentResponse();
		List<Appointment> aptList = new ArrayList<>();
		aptList = appointmentRepository.findAll();
		String dateTimeString = appointmentRequestDTO.getAppointmentDate();
		dateTimeString = dateTimeString + "T" + appointmentRequestDTO.getAppointmentTime();
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		logger.info("Date and Time conversion successfull");
		// appointments at same time
		for (Appointment apt : aptList) {
			if (apt.getPatient().getEmailId().equals(appointmentRequestDTO.getPatientEmail())
					&& apt.getAppointmentStatus().equalsIgnoreCase("Confirmed")) {
				logger.info("Patient id matched\n DB " + apt.getAppointmentDateTime() + "\n FE " + dateTime);
				if (apt.getAppointmentDateTime().equals(dateTime)) {
					logger.info("Times matched you can't attend two appointments at the same date and time");
					flag = 1;
					break;
				}
			}
		}
		// appointment with same provider in future is present so can't book now
		for (Appointment apt : aptList) {
			if (apt.getPatient().getEmailId().equals(appointmentRequestDTO.getPatientEmail())
					&& apt.getProvider().getProviderId() == appointmentRequestDTO.getProviderId()
					&& apt.getAppointmentStatus().equalsIgnoreCase("Confirmed")) {
				logger.info("Patient id and Provider Id matched");
				if (apt.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
					flag = 0;
					break;
				} else {
					logger.info("appointment with same provider in future is present so can't book now");
					flag = 2;
				}
			}
		}

		if (dateTime.isAfter(LocalDateTime.now()) && flag == 0
				&& dateTime.isBefore(LocalDateTime.now().plusMonths(3))) {
			Appointment apt = new Appointment();
			apt.setAppointmentId(0);
			Patient p = patientRepository.findById(appointmentRequestDTO.getPatientEmail()).orElse(null);
			apt.setPatient(p);
			Provider pr = providerRepository.findById(appointmentRequestDTO.getProviderId()).orElse(null);
			apt.setProvider(pr);
			apt.setAppointmentDateTime(dateTime);
			apt.setAppointmentStatus("Confirmed");
			appointmentRepository.save(apt);

			logger.info("mail sending");
			String emailTo = appointmentRequestDTO.getPatientEmail();
			String emailSubject = "Appointment booking confirmation";
			String emailBody = "Hello " + p.getFirstName() + " " + p.getLastName()
					+ ", your appointment has been booked with Dr." + pr.getProviderFirstName() + " "
					+ pr.getProviderLastName() + " having " + pr.getProviderSpecailty() + " as speciality, on date "
					+ appointmentRequestDTO.getAppointmentDate() + " and time "
					+ appointmentRequestDTO.getAppointmentTime() + " is confirmed !" + "\n\n"
					+ "Your healt is our responsibility !" + "\n\n\n" + "Thanks and Regards," + "\n"
					+ "Healthcare Appointment System";
			emailService.sendEmail(emailTo, emailSubject, emailBody);
			logger.info("mail sent and appointment is booked successfully");
			aptRes.setAppointmentStatus("Confirmed");
			return aptRes;
		}
		if (flag == 1) {
			aptRes.setAppointmentStatus("SameDayTime");
			logger.info("can't book appointment on SameDayTime");
			return aptRes;
		} else if (!dateTime.isAfter(LocalDateTime.now())) {
			aptRes.setAppointmentStatus("BeforeCurrentTime");
			logger.info("can't book appointment on SameDayTime");
			return aptRes;
		} else if (!dateTime.isBefore(LocalDateTime.now().plusMonths(3))) {
			aptRes.setAppointmentStatus("AfterThreeMonths");
			logger.info("can't book appointment on SameDayTime");
			return aptRes;
		} 
//		else if(flag == 2) {
			aptRes.setAppointmentStatus("SameProvider");
			logger.info("SameProvider appintment declined");
			return aptRes;
//		}
//		aptRes.setAppointmentStatus("Declined");
//		logger.info("appintment declined");
//		return aptRes;
	}

	//method for providing the upcoming appointments and all appointments
	@Override
	public List<ScheduledAppointments> provideAppointments(String emailId, int funCheck) throws DataNotFoundException {
		logger.info("providing appointment from AppointmentServiceImpl");
		int flag = 0;
		List<ScheduledAppointments> saList = new ArrayList<>();
		List<Appointment> aptList = new ArrayList<>();
		List<Appointment> aptList2 = new ArrayList<>();
		aptList = appointmentRepository.findAll();
		for (Appointment apt : aptList) {
			if (funCheck == 1) {
				logger.info("Providing upcoming appointments");
				if (apt.getPatient().getEmailId().equals(emailId)
						&& apt.getAppointmentDateTime().isAfter(LocalDateTime.now())
						&& apt.getAppointmentStatus().equalsIgnoreCase("Confirmed")) {
					flag = 1;
					aptList2.add(apt);
				}
			} else {
				logger.info("Providing history of appointments");
				if (apt.getPatient().getEmailId().equals(emailId)) {
					flag = 1;
					aptList2.add(apt);
				}
			}
		}
		if (flag == 0) {
			logger.info("No appointments found");
			throw new DataNotFoundException("Exception is occured");
		}
		Provider provider = new Provider();
		ScheduledAppointments schApt = new ScheduledAppointments();
		logger.info("converting to response objects");
		for (Appointment apt : aptList2) {
			provider = providerRepository.findById(apt.getProvider().getProviderId()).orElse(null);
			AppointmentDataExtraction aptDE = new AppointmentDataExtraction();
			schApt = aptDE.mapAppointmentProviderToSchApt(provider, apt);
			saList.add(schApt);
		}
		logger.info("returning response to controller from provideAppointments method");
		return saList;
	}
}
