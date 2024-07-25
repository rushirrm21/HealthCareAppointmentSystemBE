package com.hcaptsys.aptdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProviderDTO {

	private int providerId;

	private String providerFirstName;

	private String providerLastName;

	private String providerSpecailty;
}
