package com.hcaptsys.aptservice;

import java.util.List;
import com.hcaptsys.aptdto.AppointmentRequestDTO;
import com.hcaptsys.aptdto.AppointmentResponse;
import com.hcaptsys.aptdto.RequestTimeSlots;
import com.hcaptsys.aptdto.ResponseTimeSlots;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.exception.DataNotFoundException;

public interface AppointmentService {

	public AppointmentResponse bookAnAppointment(AppointmentRequestDTO appointmentRequestDTO);

	public List<ResponseTimeSlots> provideAvailableTimeSlots(RequestTimeSlots requestTimeSlots);

	public List<ScheduledAppointments> provideAppointments(String emailId, int funCheck) throws DataNotFoundException;

	public AppointmentResponse changeAppointmentStatusById(int appointmentId);
}
