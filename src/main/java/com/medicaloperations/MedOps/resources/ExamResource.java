package com.medicaloperations.MedOps.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicaloperations.MedOps.entities.Exam;
import com.medicaloperations.MedOps.services.ExamService;

@RestController
@RequestMapping(value = "/exams")
public class ExamResource {

	@Autowired
	private ExamService service;

	@GetMapping
	public ResponseEntity<List<Exam>> findAll() {
		List<Exam> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Exam> findById(@PathVariable Long id) {
		Exam obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
//	@PostMapping
//    public ResponseEntity<Pacient> insert(@RequestBody Pacient pacient){
//		pacient = service.insert(pacient);
//    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pacient.getId()).toUri();
//    	return ResponseEntity.created(uri).body(pacient);
//    }
//    
//   @DeleteMapping(value = "/{id}")
//   public ResponseEntity<Void> delete(@PathVariable Long id){
//	   service.delete(id);
//	   return ResponseEntity.noContent().build();
//   }
//   
//   @PutMapping(value = "/{id}")
//   public ResponseEntity<Pacient> update(@PathVariable Long id, @RequestBody Pacient pacient){
//	   pacient = service.update(id, pacient);
//	   return ResponseEntity.ok().body(pacient);
//   }
	

}
