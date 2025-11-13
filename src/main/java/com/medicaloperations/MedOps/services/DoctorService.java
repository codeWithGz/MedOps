package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.Doctor;
import com.medicaloperations.MedOps.repositories.DoctorRepository;
import com.medicaloperations.MedOps.services.exceptions.ResourceNotFoundException;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository repository;

	public List<Doctor> findAll() {
		return repository.findAll();
	}
	
	public Doctor findById(Long Id) {
		Optional<Doctor> obj = repository.findById(Id);
		return obj.orElseThrow(()-> new ResourceNotFoundException(Id));
	}
	
    public List<Doctor> findBySpecialty(Integer specialty) {
        return repository.findBySpecialty(specialty);
    }
    
    public Doctor insert(Doctor doc) {
    	return repository.save(doc);
    }
    
    public void delete(Long id) {
    	repository.deleteById(id);
    }
    
    public Doctor update(Long id, Doctor doc) {
    	Doctor updatedDoctor = repository.getReferenceById(id);
    	updateData(updatedDoctor, doc);
    	return repository.save(updatedDoctor);
    }

	private void updateData(Doctor updatedDoctor, Doctor doc) {
		updatedDoctor.setName(doc.getName());
		updatedDoctor.setEmail(doc.getEmail());	
	}
    
	
}
