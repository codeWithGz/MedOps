package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.Doctor;
import com.medicaloperations.MedOps.entities.Pacient;
import com.medicaloperations.MedOps.repositories.PacientRepository;
import com.medicaloperations.MedOps.services.exceptions.DatabaseException;
import com.medicaloperations.MedOps.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PacientService {

	@Autowired
	private PacientRepository repository;

	public List<Pacient> findAll() {
		return repository.findAll();
	}
	
	public Pacient findById(Long Id) {
		Optional<Pacient> obj = repository.findById(Id);
		return obj.orElseThrow(()-> new ResourceNotFoundException(Id));
	}
	
	public Pacient insert(Pacient pacient) {
    	return repository.save(pacient);
    }
    
    public void delete(Long id) {
    	
    	try {
    		repository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
    	
    }
    
    public Pacient update(Long id, Pacient pacient) {
    	
    	try {
    		Pacient updatedPacient = repository.getReferenceById(id);
    		updateData(updatedPacient, pacient);
    		return repository.save(updatedPacient);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
    	
    	
    }

	private void updateData(Pacient updatedPacient, Pacient pacient) {
		updatedPacient.setName(pacient.getName());
		updatedPacient.setEmail(pacient.getEmail());				
		updatedPacient.setPassword(pacient.getPassword());				

		
	}
    
	
	
}
