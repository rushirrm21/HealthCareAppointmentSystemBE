package com.hcaptsys.trtservice;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hcaptsys.exception.DataNotFoundException;
import com.hcaptsys.trtdto.TreatmentDTO;
import com.hcaptsys.trtmodel.Treatment;
import com.hcaptsys.trtrepository.TreatmentRepository;
import com.hcaptsys.trtutil.TreatmentDataExtraction;

@Service
public class TreatmentServiceImpl implements TreatmentService {

	@Autowired
	TreatmentRepository treatmentRepository;

	Logger logger = LoggerFactory.getLogger(TreatmentServiceImpl.class);

	//method to provide treatment hostory of the patient
	@Override
	public List<TreatmentDTO> getTreatmentDetails(String emailId) throws DataNotFoundException {
		logger.info("getTreatmentDetails method invoked from TreatmentServiceImpl");
		TreatmentDataExtraction tde = new TreatmentDataExtraction();
		List<Treatment> tList = treatmentRepository.findAll();
		List<TreatmentDTO> tDTOList = tde.extractTreatmentById(tList, emailId);
		logger.info("returning successfull response from TreatmentServiceImpl to controller");
		return tDTOList;

	}

}
