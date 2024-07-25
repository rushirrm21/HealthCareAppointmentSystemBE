package com.hcaptsys.rldto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

	private String emailId;
	private String password;
	private String firstName;
	private String lastName;
}
