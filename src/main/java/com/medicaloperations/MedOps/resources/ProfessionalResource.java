package com.medicaloperations.MedOps.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicaloperations.MedOps.entities.Professional;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;

@RestController
@RequestMapping(value = "/professionals")
public class ProfessionalResource {

	@GetMapping
	public ResponseEntity<Professional> findAll(){
		Professional p = new Professional(1L, "cody", "cody@gmail.com", "codyTheBest", AccountStatus.ACTIVE);
		return ResponseEntity.ok().body(p);
	}
	
}
