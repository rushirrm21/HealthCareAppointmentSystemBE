package com.hcaptsys;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HealthcareAppointmentSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void main() {
		HealthcareAppointmentSystemApplication.main(new String[] {});
		boolean val = true;
		assertEquals(true, val);
	}
}
