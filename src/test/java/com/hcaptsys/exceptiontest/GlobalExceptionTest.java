package com.hcaptsys.exceptiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.exception.GlobalExceptions;
import com.hcaptsys.rldto.LoginResponse;
import com.hcaptsys.rldto.RegistrationResponse;

class GlobalExceptionsTest {

//	@Test
//	void testExceptionHandler1() {
////		BadCredentialsException exception = mock(BadCredentialsException.class);
//		GlobalExceptions globalExceptions = new GlobalExceptions();
//		ResponseEntity<LoginResponse> responseEntity = globalExceptions.exceptionHandler1();
//		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//	}
//
//	@Test
//	void testExceptionHandler2() {
////		ValidationException exception = mock(ValidationException.class);
//		GlobalExceptions globalExceptions = new GlobalExceptions();
//		ResponseEntity<RegistrationResponse> responseEntity = globalExceptions.exceptionHandler2();
//		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//	}
//
//	@Test
//	void testExceptionHandler3() {
////		DataNotFoundException exception = mock(DataNotFoundException.class);
//		GlobalExceptions globalExeptions = new GlobalExceptions();
//		ResponseEntity<List<ScheduledAppointments>> responseEntiy = globalExeptions.exceptionHandler3();
//		assertEquals(HttpStatus.NOT_FOUND, responseEntiy.getStatusCode());
//	}
}
