package com.hcaptsys.aptmodel;

import java.time.LocalDateTime;
import com.hcaptsys.rlmodel.Patient;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int appointmentId;

	@ManyToOne
	@JoinColumn(name = "patientId")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "providerId")
	private Provider provider;

	@NotNull
	private LocalDateTime appointmentDateTime;

	@NotNull
	private String appointmentStatus;

}
