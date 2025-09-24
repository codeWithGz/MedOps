package com.medicaloperations.MedOps.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;
import com.medicaloperations.MedOps.entities.enums.Specialty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_professional")
public class Professional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private Integer specialty;
	private Integer accountStatus;

	@JsonIgnore
	@OneToMany(mappedBy = "doctor")
	private List<MedicalConsultation> consultations = new ArrayList<>();
	
	public Professional() {
	}

	public Professional(Long id, String name, Specialty specialty, String email, String password,
			AccountStatus accountStatus) {
		super();
		this.id = id;
		this.name = name;
		setSpecialty(specialty);
		this.email = email;
		this.password = password;
		setAccountStatus(accountStatus);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountStatus getAccountStatus() {
		return AccountStatus.valueOf(accountStatus);
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		if (accountStatus != null) {
			this.accountStatus = accountStatus.getCode();
		}
	}

	public Specialty getSpecialty() {
		return Specialty.valueOf(specialty);
	}

	public void setSpecialty(Specialty specialty) {
		if (specialty != null) {
			this.specialty = specialty.getCode();
		}
	}

	public List<MedicalConsultation> getConsultations() {
		return consultations;
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
		Professional other = (Professional) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Professional [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", specialty=" + specialty + ", accountStatus=" + accountStatus + "]";
	}

}
