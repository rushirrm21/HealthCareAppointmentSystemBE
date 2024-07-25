package com.hcaptsys.aptutiltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.ProviderDTO;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.aptutil.ProviderMapper;

public class ProviderMapperTest {

	ProviderMapper providerMapper = new ProviderMapper();

	@Test
	public void providerMapToDTOTest() {

		Provider provider = new Provider();
		provider.setProviderFirstName("Rushi");
		provider.setProviderLastName("Matsagar");
		provider.setProviderId(1);
		provider.setProviderSpecailty("Neurologist");

		ProviderDTO pDTO = new ProviderDTO();
		pDTO.setProviderId(1);
		pDTO.setProviderFirstName("Rushi");
		pDTO.setProviderLastName("Matsagar");
		pDTO.setProviderSpecailty("Neurologist");

		assertEquals(providerMapper.providerMapToDTO(provider), pDTO);

	}
}
