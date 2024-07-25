package com.hcaptsys.aptdtotest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.ResponseTimeSlots;

public class ResponseTimeSlotsTest {

	@Test
	public void testSameAppointmentTimeObject() {
		ResponseTimeSlots timeSlots1 = new ResponseTimeSlots("10:00 AM");
		ResponseTimeSlots timeSlots2 = timeSlots1;
		assertEquals(timeSlots1, timeSlots2);
	}

}
