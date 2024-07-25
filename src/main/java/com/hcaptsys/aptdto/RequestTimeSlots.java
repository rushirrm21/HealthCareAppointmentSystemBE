package com.hcaptsys.aptdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTimeSlots {

	private int providerId;

	private String appointmentDate;

}
