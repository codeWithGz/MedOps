package com.medicaloperations.MedOps.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicaloperations.MedOps.entities.Pacient;
import com.medicaloperations.MedOps.services.PacientService;

@RestController
@RequestMapping(value = "/pacients")
public class PacientResource {

	@Autowired
	private PacientService service;

	@GetMapping
	public ResponseEntity<List<Pacient>> findAll() {
		List<Pacient> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Pacient> findById(@PathVariable Long id) {
		Pacient obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	

}
