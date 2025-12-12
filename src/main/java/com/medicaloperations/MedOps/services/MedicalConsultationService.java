package com.medicaloperations.MedOps.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.MedicalConsultation;
import com.medicaloperations.MedOps.repositories.MedicalConsultationRepository;
import com.medicaloperations.MedOps.services.exceptions.DatabaseException;
import com.medicaloperations.MedOps.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MedicalConsultationService {

	@Autowired
	private MedicalConsultationRepository repository;

	public List<MedicalConsultation> findAll() {
		return repository.findAll();
	}
	
	public MedicalConsultation findById(Long Id) {
		Optional<MedicalConsultation> obj = repository.findById(Id);
		return obj.orElseThrow(()-> new ResourceNotFoundException(Id));
	}
	
	public MedicalConsultation insert(MedicalConsultation consultation) {
		long durationMinutes = 30;
		Instant startWindow = consultation.getMoment().minus(durationMinutes, ChronoUnit.MINUTES);
        Instant endWindow = consultation.getMoment().plus(durationMinutes, ChronoUnit.MINUTES);

		
		
        boolean hasConflict = repository.existsInTimeWindow(
        		consultation.getDoctor().getId(), 
                startWindow, 
                endWindow
            );
		
        if (hasConflict) {
            throw new IllegalArgumentException("Ops! Parece que ja existe uma consulta nesse horário, tente trocar o horario ou data.");
        }
        
    	return repository.save(consultation);
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
    
    public MedicalConsultation update(Long id, MedicalConsultation consultation) {
    	
    	try {
    		MedicalConsultation updatedPacient = repository.getReferenceById(id);
    		updateData(updatedPacient, consultation);
    		return repository.save(updatedPacient);	
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
    	
    	
    }

	private void updateData(MedicalConsultation updatedPacient, MedicalConsultation consultation) {
		updatedPacient.setDoctor(consultation.getDoctor());
		updatedPacient.setMoment(consultation.getMoment());				
	}
	
	
	
}
