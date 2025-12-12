package com.medicaloperations.MedOps.repositories;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicaloperations.MedOps.entities.MedicalConsultation;

public interface MedicalConsultationRepository extends JpaRepository<MedicalConsultation, Long> {

	@Query("SELECT COUNT(c) > 0 FROM MedicalConsultation c " +
	           "WHERE c.doctor.id = :doctorId " +
	           "AND c.moment > :startWindow " +
	           "AND c.moment < :endWindow")
	    boolean existsInTimeWindow(@Param("doctorId") Long doctorId, 
	                               @Param("startWindow") Instant startWindow, 
	                               @Param("endWindow") Instant endWindow);
}
