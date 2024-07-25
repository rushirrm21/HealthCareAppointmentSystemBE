package com.hcaptsys.aptutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcaptsys.aptdto.ProviderDTO;
import com.hcaptsys.aptmodel.Provider;

public class ProviderMapper {

	Logger logger = LoggerFactory.getLogger(ProviderMapper.class);

	public ProviderDTO providerMapToDTO(Provider p) {
		logger.info("Mapper method providerMapToDTO to send provider to frontend");
		ProviderDTO pDTO = new ProviderDTO();
		pDTO.setProviderId(p.getProviderId());
		pDTO.setProviderFirstName(p.getProviderFirstName());
		pDTO.setProviderLastName(p.getProviderLastName());
		pDTO.setProviderSpecailty(p.getProviderSpecailty());
		logger.info("Returning responser from providerMapToDTO");
		return pDTO;
	}
}
