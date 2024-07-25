package com.hcaptsys.trtutil;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcaptsys.trtdto.TreatmentDTO;
import com.hcaptsys.trtmodel.Treatment;

public class TreatmentDataExtraction {

	Logger logger = LoggerFactory.getLogger(TreatmentDataExtraction.class);

	public List<TreatmentDTO> extractTreatmentById(List<Treatment> tList, String emailId) {

		logger.info("extractTreatmentById method invoked from TreatmentDataExtraction class");
		List<Treatment> trt = new ArrayList<>();
		for (Treatment t : tList) {
			String emailByTreatment = t.getPatient().getEmailId();
			if (emailByTreatment.equals(emailId)) {
				trt.add(t);
			}
		}
		logger.info("Treatment data sorted by emailId now mapping in further steps to DTO");
		List<TreatmentDTO> tDTOList = new ArrayList<>();
		TreatmentDTO tDTO = new TreatmentDTO();
		for (Treatment t : trt) {
			tDTO = this.mapToDTOTreatment(t);
			tDTOList.add(tDTO);
		}
		logger.info("Returning response to service class");
		return tDTOList;
	}

	public TreatmentDTO mapToDTOTreatment(Treatment t) {
		logger.info("mapToDTOTreatment method invoked");
		TreatmentDTO tDTO = new TreatmentDTO();
		String name = t.getProvider().getProviderFirstName() + " " + t.getProvider().getProviderLastName();
		tDTO.setProviderName(name);
		tDTO.setProviderSpecality(t.getProvider().getProviderSpecailty());
		tDTO.setTreatmentDiagnosis(t.getTreatmentDiagnosis());
		tDTO.setTreatmentGiven(t.getTreatmentGiven());
		tDTO.setTreatmentStatus(t.getTreatmentStatus());
		Instant instant = t.getTreatmentDateTime().atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		tDTO.setTreatmentDateTime(date.toString());
		logger.info("returning response from mapToDTOTreatment method");
		return tDTO;

	}
}
