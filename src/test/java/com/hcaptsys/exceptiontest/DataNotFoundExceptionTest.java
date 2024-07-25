package com.hcaptsys.exceptiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.hcaptsys.exception.DataNotFoundException;

class DataNotFoundExceptionTest {

	@Test
	void testDataNotFoundException() {
		String message = "Data not found";
		DataNotFoundException exception = new DataNotFoundException(message);
		assertEquals(message, exception.getMessage());
	}
}