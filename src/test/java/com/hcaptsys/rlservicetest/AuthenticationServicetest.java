package com.hcaptsys.rlservicetest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.hcaptsys.rlservice.AuthenticationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServicetest {

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private AuthenticationServiceImpl authenticationService;

	@Test
	public void doAuthenticateTest1() {
		String email = "rushi@gmail.com";
		String password = "QWE@21rrm";
		UsernamePasswordAuthenticationToken authenticationCred = new UsernamePasswordAuthenticationToken(
				"rushi@gmail.com", "QWE@21rrm");
		authenticationService.doAuthenticate(email, password);
		verify(authenticationManager).authenticate(authenticationCred);
	}

	@Test
	public void doAuthenticateTest2() {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("rushi@gmail.com",
				"QWE@21rrm");
		when(authenticationManager.authenticate(authentication)).thenThrow(BadCredentialsException.class);
		assertThrows(BadCredentialsException.class,
				() -> authenticationService.doAuthenticate("rushi@gmail.com", "QWE@21rrm"));

	}
}
