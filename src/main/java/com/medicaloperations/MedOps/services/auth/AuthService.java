package com.medicaloperations.MedOps.services.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicaloperations.MedOps.entities.Pacient;
import com.medicaloperations.MedOps.repositories.PacientRepository;

@Service
public class AuthService {

	@Autowired
	private PacientRepository pacientRepository;
	
	public String authenticate(String email, String rawPassword) {

		Optional<Pacient> pacientOpt = pacientRepository.findByEmail(email);

		if (pacientOpt.isEmpty()) {
			return null;
		}

		Pacient user = pacientOpt.get();

		if (user.getPassword().equals(rawPassword)) { 

			return "token-jwt-gerado-com-sucesso-para-o-id-" + user.getId();

		} else {
			return null;
		}
	}
}