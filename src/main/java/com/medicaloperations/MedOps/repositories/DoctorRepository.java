package com.medicaloperations.MedOps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicaloperations.MedOps.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	List<Doctor> findBySpecialty(Integer specialty);
	
}
