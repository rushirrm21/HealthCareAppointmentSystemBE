package com.hcaptsys.aptservicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hcaptsys.aptdto.ProviderDTO;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.aptrepository.ProviderRepository;
import com.hcaptsys.aptservice.ProviderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProviderServiceTest {

	@InjectMocks
	ProviderServiceImpl providerService;

	@Mock
	ProviderRepository providerRepository;

	@Test
	public void getProvidersTest1() {
		Provider provider = new Provider();
		provider.setProviderFirstName("Aniket");
		provider.setProviderLastName("Patil");
		provider.setProviderId(1);
		provider.setProviderSpecailty("Neurologist");
		List<Provider> providerList = new ArrayList<Provider>();
		providerList.add(provider);
		List<ProviderDTO> output = new ArrayList<>();
		ProviderDTO providerDTO = new ProviderDTO();
		providerDTO.setProviderFirstName("Aniket");
		providerDTO.setProviderLastName("Patil");
		providerDTO.setProviderId(1);
		providerDTO.setProviderSpecailty("Neurologist");
		output.add(providerDTO);
		when(providerRepository.findAll()).thenReturn(providerList);
		assertEquals(providerService.getProviders(), output);
	}
}
