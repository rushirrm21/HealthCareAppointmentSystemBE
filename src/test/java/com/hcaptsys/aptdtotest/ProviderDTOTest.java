package com.hcaptsys.aptdtotest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptdto.ProviderDTO;

public class ProviderDTOTest {

	@Test
	public void testGetProviderId() {
		ProviderDTO dto = new ProviderDTO();
		dto.setProviderId(1);
		Assertions.assertEquals(1, dto.getProviderId());
	}

	@Test
	public void testGetProviderFirstName() {
		ProviderDTO dto = new ProviderDTO();
		dto.setProviderFirstName("John");
		Assertions.assertEquals("John", dto.getProviderFirstName());
	}

	@Test
	public void testGetProviderLastName() {
		ProviderDTO dto = new ProviderDTO();
		dto.setProviderLastName("Doe");
		Assertions.assertEquals("Doe", dto.getProviderLastName());
	}

	@Test
	public void testGetProviderSpecialty() {
		ProviderDTO dto = new ProviderDTO();
		dto.setProviderSpecailty("Cardiology");
		Assertions.assertEquals("Cardiology", dto.getProviderSpecailty());
	}

	@Test
	public void testAllArgsConstructor() {
		ProviderDTO dto = new ProviderDTO(1, "John", "Doe", "Cardiology");
		Assertions.assertEquals(1, dto.getProviderId());
		Assertions.assertEquals("John", dto.getProviderFirstName());
		Assertions.assertEquals("Doe", dto.getProviderLastName());
		Assertions.assertEquals("Cardiology", dto.getProviderSpecailty());
	}

	@Test
	public void testNoArgsConstructor() {
		ProviderDTO dto = new ProviderDTO();
		Assertions.assertEquals(0, dto.getProviderId());
		Assertions.assertNull(dto.getProviderFirstName());
		Assertions.assertNull(dto.getProviderLastName());
		Assertions.assertNull(dto.getProviderSpecailty());
	}
}
