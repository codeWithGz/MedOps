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
@Profile({"test", "prod"})
public class TestConfig implements CommandLineRunner {

	@Autowired
	private DoctorRepository professionalRepository;




	@Override
	public void run(String... args) throws Exception {
		
		if (professionalRepository.count() == 0) {
			
			Doctor d1 = new Doctor(null, "codeTheClinical", Specialty.CLINICO, "code@gmail.com", "codeTheClinical", AccountStatus.ACTIVE);
			Doctor d2 = new Doctor(null, "codeThePharmacist", Specialty.FARMACEUTICO, "code@gmail.com", "codeThePharmacist",AccountStatus.ACTIVE);
			Doctor d3 = new Doctor(null, "codeTheAuxiliar", Specialty.AUXENFERMAGEM, "code@gmail.com", "codeTheAuxiliar",AccountStatus.ACTIVE);
			Doctor d4 = new Doctor(null, "codeTheNurse", Specialty.ENFERMEIRO, "code@gmail.com", "codeTheNurse",AccountStatus.ACTIVE);
			Doctor d5 = new Doctor(null, "codeTheDentist", Specialty.DENTISTA, "code@gmail.com", "codeTheDentist",AccountStatus.ACTIVE);
			professionalRepository.saveAll(Arrays.asList(d1, d2, d3, d4, d5));
			
		}

	}
}
