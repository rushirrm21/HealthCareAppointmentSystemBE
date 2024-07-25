package com.hcaptsys.trtdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDTO {

	private String providerName;

	private String providerSpecality;

	private String treatmentGiven;

	private String treatmentDiagnosis;

	private String treatmentDateTime;

	private String treatmentStatus;

}
