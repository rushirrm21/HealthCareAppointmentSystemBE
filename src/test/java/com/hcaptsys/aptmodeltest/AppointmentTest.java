package com.hcaptsys.aptmodeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptmodel.Appointment;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.rlmodel.Patient;

public class AppointmentTest {

	@Test
	public void testAppointmentId() {
		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1);
		assertEquals(1, appointment.getAppointmentId());
	}

	@Test
	public void testPatient() {
		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		Appointment appointment = new Appointment();
		appointment.setPatient(patient);
		assertEquals(patient, appointment.getPatient());
	}

	@Test
	public void testProvider() {
		Provider provider = new Provider();
		provider.setProviderId(1);
		Appointment appointment = new Appointment();
		appointment.setProvider(provider);
		assertEquals(provider, appointment.getProvider());
	}

	@Test
	public void testAppointmentDateTime() {
		LocalDateTime dateTime = LocalDateTime.of(2022, 1, 1, 10, 0);
		Appointment appointment = new Appointment();
		appointment.setAppointmentDateTime(dateTime);

		assertEquals(dateTime, appointment.getAppointmentDateTime());
	}

	@Test
	public void testAppointmentStatus() {
		Appointment appointment = new Appointment();
		appointment.setAppointmentStatus("Confirmed");

		assertEquals("Confirmed", appointment.getAppointmentStatus());
	}
}
