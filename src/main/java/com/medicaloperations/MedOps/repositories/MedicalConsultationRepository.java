package com.medicaloperations.MedOps.repositories;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicaloperations.MedOps.entities.MedicalConsultation;

public interface MedicalConsultationRepository extends JpaRepository<MedicalConsultation, Long> {

	boolean existsByDoctorIdAndMoment(Long doctorId, Instant moment);

}
