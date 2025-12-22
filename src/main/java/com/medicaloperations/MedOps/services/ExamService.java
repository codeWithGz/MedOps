package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.Exam;
import com.medicaloperations.MedOps.repositories.ExamRepository;
import com.medicaloperations.MedOps.services.exceptions.ResourceNotFoundException;

@Service
public class ExamService {

	@Autowired
	private ExamRepository repository;

	public List<Exam> findAll() {
		return repository.findAll();
	}
	
	public Exam findById(Long Id) {
		Optional<Exam> obj = repository.findById(Id);
		return obj.orElseThrow(()-> new ResourceNotFoundException(Id));
	}
	
//	public Exam insert(Exam exam) {
//		String encodedPassword = passwordCrypt.encode(exam.getPassword());
//		exam.setPassword(encodedPassword);
//    	return repository.save(exam);
//    }
//    
//    public void delete(Long id) {
//    	
//    	try {
//    		repository.deleteById(id);			
//		} catch (EmptyResultDataAccessException e) {
//			throw new ResourceNotFoundException(id);
//		} catch(DataIntegrityViolationException e) {
//			throw new DatabaseException(e.getMessage());
//		}
//    	
//    }
//    
//    public Pacient update(Long id, Pacient pacient) {
//    	
//    	try {
//    		Pacient updatedPacient = repository.getReferenceById(id);
//    		updateData(updatedPacient, pacient);
//    		return repository.save(updatedPacient);	
//		} catch (EntityNotFoundException e) {
//			throw new ResourceNotFoundException(id);
//		}
//    	
//    	
//    }
//
//	private void updateData(Pacient updatedPacient, Pacient pacient) {
//		updatedPacient.setName(pacient.getName());
//		updatedPacient.setEmail(pacient.getEmail());				
//		updatedPacient.setPassword(pacient.getPassword());				
//
//		
//	}

}
