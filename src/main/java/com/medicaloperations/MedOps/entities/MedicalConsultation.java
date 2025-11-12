package com.medicaloperations.MedOps.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_consultation")
public class MedicalConsultation implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant moment;
	
	@ManyToOne
	@JoinColumn(name = "doctorId")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "pacientId")
	private Pacient pacient;
	
	private String motive;
	private String diagnosis;
	
	
	
	
	public MedicalConsultation() {}


	public MedicalConsultation(Long id, Instant moment, Doctor doctor, Pacient pacient, String motive, String diagnosis) {
		super();
		this.id = id;
		this.moment = moment;
		this.doctor = doctor;
		this.pacient = pacient;
		
		this.motive = motive;
		this.diagnosis = diagnosis;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Instant getMoment() {
		return moment;
	}


	public void setMoment(Instant moment) {
		this.moment = moment;
	}


	public Doctor getDoctor() {
		return doctor;
	}


	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}


	public Pacient getPacient() {
		return pacient;
	}


	public void setPacient(Pacient pacient) {
		this.pacient = pacient;
	}


	public String getMotive() {
		return motive;
	}


	public void setMotive(String motive) {
		this.motive = motive;
	}


	public String getDiagnosis() {
		return diagnosis;
	}


	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalConsultation other = (MedicalConsultation) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
}
