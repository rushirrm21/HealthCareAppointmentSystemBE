package com.hcaptsys.aptdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledAppointments {

	private int appointmentId;
	private String appointmentWithDr;
	private String appointmentWithDrSpecality;
	private String appointmentDate;
	private String appointmentTime;
	private String appointmentStatus;
}
