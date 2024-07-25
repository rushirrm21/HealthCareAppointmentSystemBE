package com.hcaptsys.aptdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestDTO {

	private String patientEmail;

	private int providerId;

	private String appointmentDate;

	private String appointmentTime;
}
