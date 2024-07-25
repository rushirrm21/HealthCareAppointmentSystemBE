package com.hcaptsys.rlrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcaptsys.rlmodel.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

}
