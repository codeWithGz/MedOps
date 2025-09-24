package com.medicaloperations.MedOps.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicaloperations.MedOps.entities.MedicalConsultation;
import com.medicaloperations.MedOps.services.MedicalConsultationService;

@RestController
@RequestMapping(value = "/consultations")
public class MedicalConsultationResource {

	@Autowired
	private MedicalConsultationService service;

	@GetMapping
	public ResponseEntity<List<MedicalConsultation>> findAll() {
		List<MedicalConsultation> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicalConsultation> findById(@PathVariable Long id) {
		MedicalConsultation obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	

}
