package com.medicaloperations.MedOps.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.medicaloperations.MedOps.entities.Doctor;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;
import com.medicaloperations.MedOps.entities.enums.Specialty;
import com.medicaloperations.MedOps.repositories.DoctorRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private DoctorRepository professionalRepository;




	@Override
	public void run(String... args) throws Exception {

		Doctor d1 = new Doctor(null, "code", Specialty.CLINICO, "code@gmail.com", "codeTheBest", AccountStatus.ACTIVE);
		Doctor d2 = new Doctor(null, "with", Specialty.FARMACEUTICO, "with@gmail.com", "withTheBest",
				AccountStatus.ACTIVE);
		professionalRepository.saveAll(Arrays.asList(d1, d2));


	}
}
