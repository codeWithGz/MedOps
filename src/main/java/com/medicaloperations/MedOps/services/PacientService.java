package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medicaloperations.MedOps.entities.Pacient;
import com.medicaloperations.MedOps.repositories.PacientRepository;

@Service
public class PacientService {

	@Autowired
	private PacientRepository repository;

	public List<Pacient> findAll() {
		return repository.findAll();
	}
	
	public Pacient findById(Long Id) {
		Optional<Pacient> obj = repository.findById(Id);
		return obj.get();
	}
	
	
}
