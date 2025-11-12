package com.medicaloperations.MedOps.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.medicaloperations.MedOps.entities.MedicalConsultation;
import com.medicaloperations.MedOps.entities.Pacient;
import com.medicaloperations.MedOps.entities.Doctor;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;
import com.medicaloperations.MedOps.entities.enums.Specialty;
import com.medicaloperations.MedOps.repositories.MedicalConsultationRepository;
import com.medicaloperations.MedOps.repositories.PacientRepository;
import com.medicaloperations.MedOps.repositories.DoctorRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private DoctorRepository professionalRepository;
	
	@Autowired
	private MedicalConsultationRepository consultationRepository;
	
	@Autowired
	private PacientRepository pacientRepository;

	@Override
	public void run(String... args) throws Exception {

		Doctor d1 = new Doctor(null, "code", Specialty.CLINICO, "code@gmail.com", "codeTheBest", AccountStatus.ACTIVE);
		Doctor d2 = new Doctor(null, "with", Specialty.FARMACEUTICO, "with@gmail.com", "withTheBest", AccountStatus.ACTIVE);
		professionalRepository.saveAll(Arrays.asList(d1, d2));
		
		Pacient p1 = new Pacient(null, "cody", "cody@email.com", "codyPass", AccountStatus.ACTIVE);
		Pacient p2 = new Pacient(null, "gz", "gz@email.com", "gzPass", AccountStatus.ACTIVE);
		pacientRepository.saveAll(Arrays.asList(p1, p2));
		
		MedicalConsultation c1 = new MedicalConsultation(null, Instant.parse("2019-06-20T10:00:00Z"), d1, p1);
		MedicalConsultation c2 = new MedicalConsultation(null, Instant.parse("2019-06-20T10:30:00Z"), d1, p2);
		MedicalConsultation c3 = new MedicalConsultation(null, Instant.parse("2019-06-20T10:00:00Z"), d2, p2);
		consultationRepository.saveAll(Arrays.asList(c1, c2, c3));
			
	}
}
