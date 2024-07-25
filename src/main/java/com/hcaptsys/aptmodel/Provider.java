package com.hcaptsys.aptmodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

	@Id
	@NotNull
	private int providerId;

	@NotNull
	private String providerFirstName;

	@NotNull
	private String providerLastName;

	@NotNull
	private String providerSpecailty;
}
