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
	
	@PostMapping
    public ResponseEntity<Pacient> insert(@RequestBody Pacient pacient){
		pacient = service.insert(pacient);
    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pacient.getId()).toUri();
    	return ResponseEntity.created(uri).body(pacient);
    }
    
   @DeleteMapping(value = "/{id}")
   public ResponseEntity<Void> delete(@PathVariable Long id){
	   service.delete(id);
	   return ResponseEntity.noContent().build();
   }
   
   @PutMapping(value = "/{id}")
   public ResponseEntity<Pacient> update(@PathVariable Long id, @RequestBody Pacient pacient){
	   pacient = service.update(id, pacient);
	   return ResponseEntity.ok().body(pacient);
   }
	

}
