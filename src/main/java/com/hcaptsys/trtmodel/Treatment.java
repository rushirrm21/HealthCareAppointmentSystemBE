package com.hcaptsys.trtmodel;

import java.time.LocalDateTime;
import com.hcaptsys.aptmodel.Provider;
import com.hcaptsys.rlmodel.Patient;
import jakarta.persistence.Entity;
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
public class Treatment {

	@Id
	@NotNull
	private int treatmentId;

	@NotNull
	private LocalDateTime treatmentDateTime;

	@NotNull
	private String treatmentGiven;

	@NotNull
	private String treatmentDiagnosis;

	@NotNull
	private String treatmentStatus;

	@ManyToOne
	@JoinColumn(name = "patientId")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "providerId")
	private Provider provider;

}
