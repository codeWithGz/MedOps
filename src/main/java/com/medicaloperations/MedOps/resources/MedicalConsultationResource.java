package com.medicaloperations.MedOps.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.medicaloperations.MedOps.entities.Doctor;
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
	
	   
    @PostMapping
    public ResponseEntity<MedicalConsultation> insert(@RequestBody MedicalConsultation consultation){
    	consultation = service.insert(consultation);
    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(consultation.getId()).toUri();
    	return ResponseEntity.created(uri).body(consultation);
    }
    
   @DeleteMapping(value = "/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id){
	   service.delete(id);
	   return ResponseEntity.noContent().build();
   }
   
   @PutMapping(value = "/{id}")
   public ResponseEntity<MedicalConsultation> update(@PathVariable Long id, @RequestBody MedicalConsultation consultation){
	   consultation = service.update(id, consultation);
	   return ResponseEntity.ok().body(consultation);
   }
    
	

}
