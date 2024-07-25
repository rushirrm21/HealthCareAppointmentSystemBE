package com.hcaptsys.trtservicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import com.hcaptsys.exception.DataNotFoundException;
import com.hcaptsys.trtdto.TreatmentDTO;
import com.hcaptsys.trtmodel.Treatment;
import com.hcaptsys.trtrepository.TreatmentRepository;
import com.hcaptsys.trtservice.TreatmentServiceImpl;
import com.hcaptsys.trtutil.TreatmentDataExtraction;

@ExtendWith(MockitoExtension.class)
public class TreatmentServiceTest {

	@InjectMocks
	TreatmentServiceImpl treatmentService;

	@Mock
	TreatmentRepository treatmentRepository;

	@Spy
	TreatmentDataExtraction tde;

	@Test
	public void getTreatmentDetailsTest1() throws DataNotFoundException {
		List<Treatment> tList = new ArrayList<Treatment>();
		when(treatmentRepository.findAll()).thenReturn(tList);
		List<TreatmentDTO> tDTOList = new ArrayList<TreatmentDTO>();
		assertEquals(treatmentService.getTreatmentDetails("rushi@gmail.com"), tDTOList);
	}
}
