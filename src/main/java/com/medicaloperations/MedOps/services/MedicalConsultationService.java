package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.MedicalConsultation;
import com.medicaloperations.MedOps.repositories.MedicalConsultationRepository;

@Service
public class MedicalConsultationService {

	@Autowired
	private MedicalConsultationRepository repository;

	public List<MedicalConsultation> findAll() {
		return repository.findAll();
	}
	
	public MedicalConsultation findById(Long Id) {
		Optional<MedicalConsultation> obj = repository.findById(Id);
		return obj.get();
	}
	
	
}
