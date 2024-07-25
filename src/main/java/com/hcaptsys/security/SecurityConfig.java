package com.hcaptsys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint point;

	@Autowired
	private JwtAuthenticationFilter filter;

	@SuppressWarnings("deprecation")
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors()
		.and().csrf(csrf -> csrf.disable()).authorizeRequests().requestMatchers("/"
						// should be protected api
						,"/healthcareappsys/api/v1/bookappointment"
						,"/healthcareappsys/api/v1/appointmenttimeslots"
						,"/healthcareappsys/api/v1/upcomingappointments/{emailId}"
						,"/healthcareappsys/api/v1/updateappointment/{appointmentId}"
						,"/healthcareappsys/api/v1/appointments/{emailId}"
						,"/healthcareappsys/api/v1/providers"
						,"/healthcareappsys/api/v1/treatmenthistory/{emailId}"
						).authenticated()
				.requestMatchers("/healthcareappsys/api/v1/patientregistration"
						,"/healthcareappsys/api/v1/patientlogin"
						,"http://localhost:8085/swagger-ui.html"
						,"/v3/api-docs/**"
		                ,"/swagger-ui/**"
		                ,"/swagger-ui.html"
		                ,"/webjars/**").permitAll().anyRequest().authenticated().and()
				.exceptionHandling(ex -> ex.authenticationEntryPoint(point))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}