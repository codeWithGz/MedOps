package com.medicaloperations.MedOps.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicaloperations.MedOps.entities.Professional;
import com.medicaloperations.MedOps.entities.enums.Specialty;
import com.medicaloperations.MedOps.services.ProfessionalService;

@RestController
@RequestMapping(value = "/professionals")
public class ProfessionalResource {

	@Autowired
	private ProfessionalService service;

	@GetMapping
	public ResponseEntity<List<Professional>> findAll() {
		List<Professional> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Professional> findById(@PathVariable Long id) {
		Professional obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
    @GetMapping(value = "/specialty/{specialtyName}")
    public ResponseEntity<List<Professional>> findBySpecialtyName(@PathVariable String specialtyName) {
        try {
            Specialty specialtyEnum = Specialty.valueOf(specialtyName.toUpperCase());
            Integer specialtyCode = specialtyEnum.getCode();
            List<Professional> list = service.findBySpecialty(specialtyCode);
            return ResponseEntity.ok().body(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
