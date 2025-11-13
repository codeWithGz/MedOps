package com.medicaloperations.MedOps.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.medicaloperations.MedOps.entities.Doctor;
import com.medicaloperations.MedOps.entities.enums.Specialty;
import com.medicaloperations.MedOps.services.DoctorService;

@RestController
@RequestMapping(value = "/doctors")
public class DoctorResource {

	@Autowired
	private DoctorService service;

	@GetMapping
	public ResponseEntity<List<Doctor>> findAll() {
		List<Doctor> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Doctor> findById(@PathVariable Long id) {
		Doctor obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
    @GetMapping(value = "/specialty/{specialtyName}")
    public ResponseEntity<List<Doctor>> findBySpecialtyName(@PathVariable String specialtyName) {
        try {
            Specialty specialtyEnum = Specialty.valueOf(specialtyName.toUpperCase());
            Integer specialtyCode = specialtyEnum.getCode();
            List<Doctor> list = service.findBySpecialty(specialtyCode);
            return ResponseEntity.ok().body(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Doctor> insert(@RequestBody Doctor doc){
    	doc = service.insert(doc);
    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(doc.getId()).toUri();
    	return ResponseEntity.created(uri).body(doc);
    }
    
   @DeleteMapping(value = "/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id){
	   service.delete(id);
	   return ResponseEntity.noContent().build();
   }
    
    

}
