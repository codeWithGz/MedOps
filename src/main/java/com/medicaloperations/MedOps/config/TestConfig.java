package com.medicaloperations.MedOps.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.medicaloperations.MedOps.entities.Professional;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;
import com.medicaloperations.MedOps.entities.enums.Specialty;
import com.medicaloperations.MedOps.repositories.ProfessionalRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private ProfessionalRepository professionalRepository;

	@Override
	public void run(String... args) throws Exception {

		Professional p1 = new Professional(null, "cody", Specialty.CLINICO, "cody@gmail.com", "codyTheBest", AccountStatus.ACTIVE);
		Professional p2 = new Professional(null, "with", Specialty.FARMACEUTICO, "with@gmail.com", "withTheBest", AccountStatus.ACTIVE);
		
		professionalRepository.saveAll(Arrays.asList(p1, p2));
	}
	
	
	
	
	
}
