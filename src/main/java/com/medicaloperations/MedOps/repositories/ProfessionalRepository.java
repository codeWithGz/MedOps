package com.medicaloperations.MedOps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicaloperations.MedOps.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

	List<Professional> findBySpecialty(Integer specialty);
	
}
