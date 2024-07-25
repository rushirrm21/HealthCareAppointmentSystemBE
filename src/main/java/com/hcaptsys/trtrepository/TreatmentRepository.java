package com.hcaptsys.trtrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hcaptsys.trtmodel.Treatment;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {

}
