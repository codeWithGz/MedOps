package com.medicaloperations.MedOps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicaloperations.MedOps.entities.Professional;

public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

}
