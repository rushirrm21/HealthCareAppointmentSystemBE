package com.hcaptsys.aptdtotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.AppointmentResponse;

public class AppointmentResponseTest {

	@Test
	public void testGetAppointmentStatus() {
		AppointmentResponse response = new AppointmentResponse();
		response.setAppointmentStatus("Confirmed");
		Assertions.assertEquals("Confirmed", response.getAppointmentStatus());
	}

	@Test
	public void testAllArgsConstructor() {
		AppointmentResponse response = new AppointmentResponse("Cancelled");
		Assertions.assertEquals("Cancelled", response.getAppointmentStatus());
	}

	@Test
	public void testNoArgsConstructor() {
		AppointmentResponse response = new AppointmentResponse();
		Assertions.assertNull(response.getAppointmentStatus());
	}
}