package com.medicaloperations.MedOps.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.medicaloperations.MedOps.entities.MedicalConsultation;
import com.medicaloperations.MedOps.entities.Professional;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;
import com.medicaloperations.MedOps.entities.enums.Specialty;
import com.medicaloperations.MedOps.repositories.MedicalConsultationRepository;
import com.medicaloperations.MedOps.repositories.ProfessionalRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private ProfessionalRepository professionalRepository;
	
	@Autowired
	private MedicalConsultationRepository consultationRepository;

	@Override
	public void run(String... args) throws Exception {

		Professional p1 = new Professional(null, "cody", Specialty.CLINICO, "cody@gmail.com", "codyTheBest", AccountStatus.ACTIVE);
		Professional p2 = new Professional(null, "with", Specialty.FARMACEUTICO, "with@gmail.com", "withTheBest", AccountStatus.ACTIVE);
		professionalRepository.saveAll(Arrays.asList(p1, p2));
		
		MedicalConsultation c1 = new MedicalConsultation(null, Instant.parse("2019-06-20T10:00:00Z"), p1);
		MedicalConsultation c2 = new MedicalConsultation(null, Instant.parse("2019-06-20T10:30:00Z"), p1);
		MedicalConsultation c3 = new MedicalConsultation(null, Instant.parse("2019-06-20T10:00:00Z"), p2);
		consultationRepository.saveAll(Arrays.asList(c1, c2, c3));
			
	}
}
