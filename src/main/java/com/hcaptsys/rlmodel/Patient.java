package com.hcaptsys.rlmodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

	@Id
	@NotNull
	private String emailId;

	@NotBlank(message = "Password should not be null or blank")
	private String password;

	@NotBlank(message = "FirstName should not be null or blank")
	private String firstName;

	@NotBlank(message = "LastName should not be null or blank")
	private String lastName;
}
