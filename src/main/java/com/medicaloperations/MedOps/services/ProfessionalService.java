package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.Professional;
import com.medicaloperations.MedOps.repositories.ProfessionalRepository;

@Service
public class ProfessionalService {

	@Autowired
	private ProfessionalRepository repository;

	public List<Professional> findAll() {
		return repository.findAll();
	}
	
	public Professional findById(Long Id) {
		Optional<Professional> obj = repository.findById(Id);
		return obj.get();
	}
	
	
	
	
}
