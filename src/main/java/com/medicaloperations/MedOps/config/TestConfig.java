package com.medicaloperations.MedOps.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.medicaloperations.MedOps.entities.Doctor;
import com.medicaloperations.MedOps.entities.Exam;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;
import com.medicaloperations.MedOps.entities.enums.ExamStatus;
import com.medicaloperations.MedOps.entities.enums.Specialty;
import com.medicaloperations.MedOps.repositories.DoctorRepository;
import com.medicaloperations.MedOps.repositories.ExamRepository;

@Configuration
@Profile({"test", "prod"})
public class TestConfig implements CommandLineRunner {

	@Autowired
	private DoctorRepository professionalRepository;
	
	@Autowired
	private ExamRepository examRepository;




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
		
		if (examRepository.count() == 0) {
			
			Exam e1 = new Exam("Hemograma", "PROT-001", Instant.parse("2023-10-20T10:00:00Z"), Instant.parse("2023-10-21T15:00:00Z"), 
								"/uploads/laudos/exame001.pdf", ExamStatus.DISPONIVEL, professionalRepository.getById(1L), null, "Nenhum preparo necessario", null);

			
			Exam e2 = new Exam("Hemograma", "PROT-001", Instant.parse("2023-10-20T10:00:00Z"), Instant.parse("2023-10-21T15:00:00Z"), 
					"/uploads/laudos/exame001.pdf", ExamStatus.DISPONIVEL, professionalRepository.getById(2L), null, "Nenhum preparo necessario", null);

			examRepository.saveAll(Arrays.asList(e1, e2));
			
		}

	}
}
