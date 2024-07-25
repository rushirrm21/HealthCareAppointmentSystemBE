package com.hcaptsys.aptdtotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.AppointmentRequestDTO;

public class AppointmentRequestDTOTest {

	@Test
	public void testGetPatientEmail() {
		AppointmentRequestDTO dto = new AppointmentRequestDTO();
		dto.setPatientEmail("test@example.com");
		Assertions.assertEquals("test@example.com", dto.getPatientEmail());
	}

	@Test
	public void testGetProviderId() {
		AppointmentRequestDTO dto = new AppointmentRequestDTO();
		dto.setProviderId(1);
		Assertions.assertEquals(1, dto.getProviderId());
	}

	@Test
	public void testGetAppointmentDate() {
		AppointmentRequestDTO dto = new AppointmentRequestDTO();
		dto.setAppointmentDate("2022-01-01");
		Assertions.assertEquals("2022-01-01", dto.getAppointmentDate());
	}

	@Test
	public void testGetAppointmentTime() {
		AppointmentRequestDTO dto = new AppointmentRequestDTO();
		dto.setAppointmentTime("10:00 AM");
		Assertions.assertEquals("10:00 AM", dto.getAppointmentTime());
	}

	@Test
	public void testAllArgsConstructor() {
		AppointmentRequestDTO dto = new AppointmentRequestDTO("test@example.com", 1, "2022-01-01", "10:00 AM");
		Assertions.assertEquals("test@example.com", dto.getPatientEmail());
		Assertions.assertEquals(1, dto.getProviderId());
		Assertions.assertEquals("2022-01-01", dto.getAppointmentDate());
		Assertions.assertEquals("10:00 AM", dto.getAppointmentTime());
	}

	@Test
	public void testNoArgsConstructor() {
		AppointmentRequestDTO dto = new AppointmentRequestDTO();
		Assertions.assertNull(dto.getPatientEmail());
		Assertions.assertEquals(0, dto.getProviderId());
		Assertions.assertNull(dto.getAppointmentDate());
		Assertions.assertNull(dto.getAppointmentTime());
	}
}
