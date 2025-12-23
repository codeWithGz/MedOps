package com.medicaloperations.MedOps.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicaloperations.MedOps.entities.enums.ExamStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_exam")
public class Exam implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String protocolNumber;
	
	@PrePersist
    private void generateProtocol() {
        this.protocolNumber = generateRandomPattern();
    }
	
	private String generateRandomPattern() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 4; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        sb.append("-");

        for (int i = 0; i < 4; i++) {
            sb.append(numbers.charAt(random.nextInt(numbers.length())));
        }

        return sb.toString();
    }

	private String examName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant executionDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
	private Instant resultReleaseDate;
	private String resultFilePath;
	private Integer examStatus;
	@Column(columnDefinition = "TEXT")
	private String preparationInstructions;
	private String externalDoctorName;
	
	@ManyToOne
	@JoinColumn(name = "doctorId", nullable = true)
	@JsonIgnoreProperties({"password", "email", "accountStatus", "specialty", "consultations"})
	private Doctor requestingDoctor;
	
	@ManyToOne
	@JoinColumn(name = "pacientId")
	@JsonIgnoreProperties({"password", "email", "accountStatus", "consultations", "consultationsIds"})
	private Pacient pacient;
	
	public Exam() {}

	public Exam(String examName, Instant executionDate, Instant resultReleaseDate,
			String resultFilePath, ExamStatus examStatus, Doctor requestingDoctor, Pacient pacient, 
			String preparationInstructions, String externalDoctorName) {
		super();
		this.examName = examName;
		this.executionDate = executionDate;
		this.resultReleaseDate = resultReleaseDate;
		this.resultFilePath = resultFilePath;
		setExamStatus(examStatus);
		this.requestingDoctor = requestingDoctor;
		this.pacient = pacient;
		this.preparationInstructions = preparationInstructions;
		this.externalDoctorName = externalDoctorName;
	}

	public ExamStatus getExamStatus() {
		if (examStatus != null) {
			return ExamStatus.valueOf(examStatus);
		}
		return null;
	}
	
	public void setExamStatus(ExamStatus status) {
		if (status != null) {
			this.examStatus = status.getCode();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getProtocolNumber() {
		return protocolNumber;
	}

	public void setProtocolNumber(String protocolNumber) {
		this.protocolNumber = protocolNumber;
	}

	public Instant getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Instant executionDate) {
		this.executionDate = executionDate;
	}

	public Instant getResultReleaseDate() {
		return resultReleaseDate;
	}

	public void setResultReleaseDate(Instant resultReleaseDate) {
		this.resultReleaseDate = resultReleaseDate;
	}

	public String getResultFilePath() {
		return resultFilePath;
	}

	public void setResultFilePath(String resultFilePath) {
		this.resultFilePath = resultFilePath;
	}

	public String getPreparationInstructions() {
		return preparationInstructions;
	}

	public void setPreparationInstructions(String preparationInstructions) {
		this.preparationInstructions = preparationInstructions;
	}

	public String getExternalDoctorName() {
		return externalDoctorName;
	}

	public void setExternalDoctorName(String externalDoctorName) {
		this.externalDoctorName = externalDoctorName;
	}

	public Doctor getRequestingDoctor() {
		return requestingDoctor;
	}

	public void setRequestingDoctor(Doctor requestingDoctor) {
		this.requestingDoctor = requestingDoctor;
	}

	public Pacient getPacient() {
		return pacient;
	}

	public void setPacient(Pacient pacient) {
		this.pacient = pacient;
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
		Exam other = (Exam) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", examName=" + examName + ", protocolNumber=" + protocolNumber + ", executionDate="
				+ executionDate + ", resultReleaseDate=" + resultReleaseDate + ", resultFilePath=" + resultFilePath
				+ ", requestingDoctor=" + requestingDoctor + ", externalDoctorName=" + externalDoctorName + ", pacient=" + pacient + "]";
	}
}