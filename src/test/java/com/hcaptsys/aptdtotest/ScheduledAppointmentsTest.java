package com.hcaptsys.aptdtotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.ScheduledAppointments;

public class ScheduledAppointmentsTest {

	@Test
	public void testAppointmentId() {
		ScheduledAppointments appointment = new ScheduledAppointments(1, "Dr. Smith", "Cardiology", "2022-01-01",
				"10:00 AM", "Confirmed");
		assertEquals(1, appointment.getAppointmentId());
	}

	@Test
	public void testAppointmentWithDr() {
		ScheduledAppointments appointment = new ScheduledAppointments(1, "Dr. Smith", "Cardiology", "2022-01-01",
				"10:00 AM", "Confirmed");
		assertEquals("Dr. Smith", appointment.getAppointmentWithDr());
	}

	@Test
	public void testAppointmentWithDrSpeciality() {
		ScheduledAppointments appointment = new ScheduledAppointments(1, "Dr. Smith", "Cardiology", "2022-01-01",
				"10:00 AM", "Confirmed");
		assertEquals("Cardiology", appointment.getAppointmentWithDrSpecality());
	}

	@Test
	public void testAppointmentDate() {
		ScheduledAppointments appointment = new ScheduledAppointments(1, "Dr. Smith", "Cardiology", "2022-01-01",
				"10:00 AM", "Confirmed");
		assertEquals("2022-01-01", appointment.getAppointmentDate());
	}

	@Test
	public void testAppointmentTime() {
		ScheduledAppointments appointment = new ScheduledAppointments(1, "Dr. Smith", "Cardiology", "2022-01-01",
				"10:00 AM", "Confirmed");
		assertEquals("10:00 AM", appointment.getAppointmentTime());
	}

	@Test
	public void testAppointmentStatus() {
		ScheduledAppointments appointment = new ScheduledAppointments(1, "Dr. Smith", "Cardiology", "2022-01-01",
				"10:00 AM", "Confirmed");
		assertEquals("Confirmed", appointment.getAppointmentStatus());
	}
}
