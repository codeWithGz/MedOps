package com.medicaloperations.MedOps.services.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String authenticate(String email, String password) {
        if ("teste@medical.com".equals(email) && "senha123".equals(password)) {
             return "sucesso-token-jwt-aqui";
        } else {
             return null; 
        }
    }
}