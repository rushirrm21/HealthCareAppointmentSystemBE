package com.hcaptsys.aptmodeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptmodel.Provider;

public class ProviderTest {

	@Test
	public void testProviderId() {
		Provider provider = new Provider();
		provider.setProviderId(1);
		assertEquals(1, provider.getProviderId());
	}

	@Test
	public void testProviderFirstName() {
		Provider provider = new Provider();
		provider.setProviderFirstName("John");
		assertEquals("John", provider.getProviderFirstName());
	}

	@Test
	public void testProviderLastName() {
		Provider provider = new Provider();
		provider.setProviderLastName("Doe");
		assertEquals("Doe", provider.getProviderLastName());
	}

	@Test
	public void testProviderSpecialty() {
		Provider provider = new Provider();
		provider.setProviderSpecailty("Cardiology");
		assertEquals("Cardiology", provider.getProviderSpecailty());
	}

}
