package com.hcaptsys.aptutil;

import java.sql.Time;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hcaptsys.aptdto.RequestTimeSlots;
import com.hcaptsys.aptdto.ResponseTimeSlots;
import com.hcaptsys.aptdto.ScheduledAppointments;
import com.hcaptsys.aptmodel.Appointment;
import com.hcaptsys.aptmodel.Provider;

public class AppointmentDataExtraction {

	Logger logger = LoggerFactory.getLogger(AppointmentDataExtraction.class);

	public List<ResponseTimeSlots> showAvailableTimeSlotsBasedOnDateProvider(List<Appointment> aptList,
			RequestTimeSlots requestTimeSlots) {
		logger.info("showAvailableTimeSlotsBasedOnDateProvider method invoked");
		int flag = 0;
		logger.info("Appointment Date ",requestTimeSlots.getAppointmentDate());
		List<Time> timeList1 = new ArrayList<>();
		Time time1 = Time.valueOf("10:00:00");
		Time time2 = Time.valueOf("11:00:00");
		Time time3 = Time.valueOf("12:00:00");
		Time time4 = Time.valueOf("13:00:00");
		Time time5 = Time.valueOf("14:00:00");
		Time time6 = Time.valueOf("15:00:00");
		Time time7 = Time.valueOf("16:00:00");
		Time time8 = Time.valueOf("18:00:00");
		Time time9 = Time.valueOf("19:00:00");
		Time time10 = Time.valueOf("20:00:00");
		timeList1.add(time1);
		timeList1.add(time2);
		timeList1.add(time3);
		timeList1.add(time4);
		timeList1.add(time5);
		timeList1.add(time6);
		timeList1.add(time7);
		timeList1.add(time8);
		timeList1.add(time9);
		timeList1.add(time10);
		List<Time> timeList2 = new ArrayList<>();
		List<Time> timeList3 = new ArrayList<>();
		for (Appointment apt : aptList) {
			ZonedDateTime zonedDateTime = apt.getAppointmentDateTime().atZone(ZoneId.systemDefault());
			OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
			Time time = Time.valueOf(offsetDateTime.toLocalTime());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = apt.getAppointmentDateTime().format(formatter);
			if (apt.getProvider().getProviderId() == requestTimeSlots.getProviderId()
					&& apt.getAppointmentStatus().equalsIgnoreCase("Confirmed")) {
				if (formattedDate.equals(requestTimeSlots.getAppointmentDate())) {
					logger.info("Provider id and date matched----------------");
					for (Time t : timeList1) {
						if (time.equals(t)) {
							flag = 1;
							timeList3.add(time);
							logger.info("Date matched =" + requestTimeSlots.getAppointmentDate() + " Time" + time);
						}
					}
				}
			}
		}

		timeList2 = timeList1;
		if (flag == 1) {
			for (Time t : timeList3) {
				timeList2.remove(t);
			}
		}

		List<ResponseTimeSlots> timeSlots = new ArrayList<ResponseTimeSlots>();
		for (Time t : timeList2) {
			logger.info("Time: ",t);
			ResponseTimeSlots ts1 = new ResponseTimeSlots();
			ts1.setAppointmentTime(t.toString());
			timeSlots.add(ts1);
		}
		logger.info("returning available time slots to appointmentService");
		return timeSlots;
	}

	public ScheduledAppointments mapAppointmentProviderToSchApt(Provider p, Appointment apt) {
		logger.info("mapping Appointment and provider to combine object to provide it to frontend");
		ScheduledAppointments schApt = new ScheduledAppointments();
		String name = p.getProviderFirstName() + " " + p.getProviderLastName();
		schApt.setAppointmentWithDr(name);
		schApt.setAppointmentWithDrSpecality(p.getProviderSpecailty());
		schApt.setAppointmentStatus(apt.getAppointmentStatus());
		ZonedDateTime zonedDateTime = apt.getAppointmentDateTime().atZone(ZoneId.systemDefault());
		OffsetDateTime offsetDateTime = zonedDateTime.toOffsetDateTime();
		Time time = Time.valueOf(offsetDateTime.toLocalTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = apt.getAppointmentDateTime().format(formatter);
		schApt.setAppointmentDate(formattedDate);
		schApt.setAppointmentTime(time.toString());
		schApt.setAppointmentId(apt.getAppointmentId());
		logger.info("returning response from mapAppointmentProviderToSchApt to appointmentService");
		return schApt;
	}
}
