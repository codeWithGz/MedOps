package com.medicaloperations.MedOps.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medicaloperations.MedOps.entities.enums.AccountStatus;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pacient")
public class Pacient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String password;
	private Integer accountStatus;

	@JsonIgnore
	@OneToMany(mappedBy = "pacient")
	private List<MedicalConsultation> consultations = new ArrayList<>();
	
	private List<Long> consultationsIds = new ArrayList<>();
	
		
	public Pacient() {
	}

	public Pacient(Long id, String name, String email, String password,
			AccountStatus accountStatus) {
		super();
		this.id = id;
		this.name = name;
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


	public List<MedicalConsultation> getConsultations() {
		return consultations;
	}
	
	public List<Long> getConsultationsIds() {
		
		for (MedicalConsultation m : consultations) {
			consultationsIds.add(m.getId());
		}
		
		return consultationsIds;
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
		Pacient other = (Pacient) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Professional [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", accountStatus=" + accountStatus + "]";
	}

}
