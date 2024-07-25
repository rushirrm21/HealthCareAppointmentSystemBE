package com.hcaptsys.aptservice;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcaptsys.aptdto.ProviderDTO;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.aptrepository.ProviderRepository;
import com.hcaptsys.aptutil.ProviderMapper;
import jakarta.transaction.Transactional;

@Service
//@Transactional
public class ProviderServiceImpl implements ProviderService {

	@Autowired
	ProviderRepository providerRepository;

	//method to provide list of providers
	@Override
	public List<ProviderDTO> getProviders() {
		List<Provider> providerList = providerRepository.findAll();
		ProviderMapper providerMapper = new ProviderMapper();
		List<ProviderDTO> output = new ArrayList<>();
		for (Provider p : providerList) {
			ProviderDTO out = providerMapper.providerMapToDTO(p);
			output.add(out);
		}
		return output;
	}

}
