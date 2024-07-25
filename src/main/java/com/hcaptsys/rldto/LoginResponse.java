package com.hcaptsys.rldto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

	private String emailId;
	private boolean status;
	private String token;
	private String firstName;
	private String lastName;
}