package com.medicaloperations.MedOps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicaloperations.MedOps.entities.Pacient;

public interface PacientRepository extends JpaRepository<Pacient, Long> {

	List<Pacient> findById(Integer id);
	
}
