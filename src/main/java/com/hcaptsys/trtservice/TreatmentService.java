package com.hcaptsys.trtservice;

import java.util.List;
import com.hcaptsys.exception.DataNotFoundException;
import com.hcaptsys.trtdto.TreatmentDTO;

public interface TreatmentService {

	public List<TreatmentDTO> getTreatmentDetails(String emailId) throws DataNotFoundException;

}
