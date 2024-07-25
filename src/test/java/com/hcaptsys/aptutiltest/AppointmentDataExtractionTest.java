package com.hcaptsys.aptutiltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.RequestTimeSlots;
import com.hcaptsys.aptdto.ResponseTimeSlots;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.aptmodel.Appointment;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.aptutil.AppointmentDataExtraction;
import com.hcaptsys.rlmodel.Patient;

public class AppointmentDataExtractionTest {

	AppointmentDataExtraction aptde = new AppointmentDataExtraction();

	@Test
	public void showAvailableTimeSlotsBasedOnDateProviderTest1() {
		RequestTimeSlots requestTimeSlots = new RequestTimeSlots();
		requestTimeSlots.setAppointmentDate("2023-09-25");
		requestTimeSlots.setProviderId(1);
		String dateTimeString = "2023-09-25";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");
		Provider p = new Provider();
		Appointment apt = new Appointment();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");
		apt.setAppointmentId(1);
		apt.setAppointmentStatus("Confirmed");
		apt.setProvider(p);
		apt.setAppointmentDateTime(dateTime);
		apt.setPatient(patient);

		List<Appointment> aptList = new ArrayList<Appointment>();
		aptList.add(apt);

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

		assertEquals(aptde.showAvailableTimeSlotsBasedOnDateProvider(aptList, requestTimeSlots), timeSlots);

	}

	@Test
	public void mapAppointmentProviderToSchApt() {
		Provider p = new Provider();
		Appointment apt = new Appointment();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		String dateTimeString = "2023-08-27";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

		apt.setAppointmentId(1);
		apt.setAppointmentStatus("Confirmed");
		apt.setProvider(p);
		apt.setAppointmentDateTime(dateTime);
		apt.setPatient(patient);

		ScheduledAppointments schApt = new ScheduledAppointments();
		schApt.setAppointmentId(1);
		schApt.setAppointmentStatus("Confirmed");
		schApt.setAppointmentWithDrSpecality("Neurologist");
		schApt.setAppointmentWithDr("Aniket Chaudhari");
		schApt.setAppointmentTime("11:00:00");
		schApt.setAppointmentDate("2023-08-27");
		assertEquals(aptde.mapAppointmentProviderToSchApt(p, apt), schApt);
	}
}
