package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.Doctor;
import com.medicaloperations.MedOps.repositories.DoctorRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository repository;

	public List<Doctor> findAll() {
		return repository.findAll();
	}
	
	public Doctor findById(Long Id) {
		Optional<Doctor> obj = repository.findById(Id);
		return obj.get();
	}
	
    public List<Doctor> findBySpecialty(Integer specialty) {
        return repository.findBySpecialty(specialty);
    }
    
    public Doctor insert(Doctor doc) {
    	return repository.save(doc);
    }
	
}
