package com.hcaptsys.aptdtotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.RequestTimeSlots;

public class RequestTimeSlotsTest {

	@Test
	public void testGetProviderId() {
		RequestTimeSlots request = new RequestTimeSlots();
		request.setProviderId(1);
		Assertions.assertEquals(1, request.getProviderId());
	}

	@Test
	public void testGetAppointmentDate() {
		RequestTimeSlots request = new RequestTimeSlots();
		request.setAppointmentDate("2022-01-01");
		Assertions.assertEquals("2022-01-01", request.getAppointmentDate());
	}

	@Test
	public void testAllArgsConstructor() {
		RequestTimeSlots request = new RequestTimeSlots(1, "2022-01-01");
		Assertions.assertEquals(1, request.getProviderId());
		Assertions.assertEquals("2022-01-01", request.getAppointmentDate());
	}

	@Test
	public void testNoArgsConstructor() {
		RequestTimeSlots request = new RequestTimeSlots();
		Assertions.assertEquals(0, request.getProviderId());
		Assertions.assertNull(request.getAppointmentDate());
	}
}