package com.hcaptsys.rlservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
//@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager manager;

	Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Override
	public void doAuthenticate(String email, String password) {
		logger.info("doAuthenticate method invoked");
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		try {
			manager.authenticate(authentication);
			logger.info("Authenticating.......");
		} catch (BadCredentialsException e) {
			logger.error("Exception occured because of bad credentials");
			throw new BadCredentialsException(" Invalid Username or Password  !!");
		}
	}
}
