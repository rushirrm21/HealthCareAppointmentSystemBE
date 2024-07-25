package com.hcaptsys.trtutiltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.rlmodel.Patient;
import com.hcaptsys.trtdto.TreatmentDTO;
import com.hcaptsys.trtmodel.Treatment;
import com.hcaptsys.trtutil.TreatmentDataExtraction;

public class TreatmentDataExtractionTest {

	TreatmentDataExtraction treatmentDataExtraction = new TreatmentDataExtraction();

	@Test
	public void extractTreatmentByIdTest() {
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Treatment t = new Treatment();
		t.setPatient(patient);
		t.setProvider(p);
		t.setTreatmentId(1);
		t.setTreatmentStatus("Visited");
		t.setTreatmentDiagnosis("Blood Thick");
		t.setTreatmentGiven("Tablets");
		String dateTimeString = "2023-08-27";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		t.setTreatmentDateTime(dateTime);

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
		List<TreatmentDTO> trtDTOList = new ArrayList<TreatmentDTO>();
		trtDTOList.add(tDTO);

		List<Treatment> trtList = new ArrayList<Treatment>();
		trtList.add(t);
		assertEquals(treatmentDataExtraction.extractTreatmentById(trtList, "rushi@gmail.com"), trtDTOList);

	}

	@Test
	public void mapToDTOTreatment() {
		Provider p = new Provider();
		p.setProviderFirstName("Aniket");
		p.setProviderLastName("Chaudhari");
		p.setProviderId(1);
		p.setProviderSpecailty("Neurologist");

		Patient patient = new Patient();
		patient.setEmailId("rushi@gmail.com");
		patient.setFirstName("Rushi");
		patient.setLastName("Patil");
		patient.setPassword("QWE@21rrm");

		Treatment t = new Treatment();
		t.setPatient(patient);
		t.setProvider(p);
		t.setTreatmentId(1);
		t.setTreatmentStatus("Visited");
		t.setTreatmentDiagnosis("Blood Thick");
		t.setTreatmentGiven("Tablets");
		String dateTimeString = "2023-08-27";
		dateTimeString = dateTimeString + "T" + "11:00:00";
		String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		t.setTreatmentDateTime(dateTime);

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

		assertEquals(treatmentDataExtraction.mapToDTOTreatment(t), tDTO);
	}
}
