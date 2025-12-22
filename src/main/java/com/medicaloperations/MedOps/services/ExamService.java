package com.medicaloperations.MedOps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.Exam;
import com.medicaloperations.MedOps.repositories.ExamRepository;
import com.medicaloperations.MedOps.services.exceptions.DatabaseException;
import com.medicaloperations.MedOps.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

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
	
	public Exam insert(Exam exam) {
    	return repository.save(exam);
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
    
    public Exam update(Long id, Exam exam) {
    	
    	try {
    		Exam updatedExam = repository.getReferenceById(id);
    		updateData(updatedExam, exam);
    		return repository.save(updatedExam);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
    	
    	
    }

	private void updateData(Exam updatedExam, Exam exam) {
		updatedExam.setExamName(exam.getExamName());
		updatedExam.setExamStatus(exam.getExamStatus());
		updatedExam.setResultReleaseDate(exam.getResultReleaseDate());
		updatedExam.setResultFilePath(exam.getResultFilePath());
	}

}
