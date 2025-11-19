package com.medicaloperations.MedOps.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicaloperations.MedOps.auth.dto.LoginRequest;
import com.medicaloperations.MedOps.services.auth.AuthService;

@RestController
@RequestMapping("/auth") 
public class AuthController {

    @Autowired
    private AuthService authService;
    


    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
             return new ResponseEntity<>("Email e senha são obrigatórios.", HttpStatus.BAD_REQUEST);
        }

        String token = authService.authenticate(
            loginRequest.getEmail(), 
            loginRequest.getPassword()
        );

        if (token != null) {
            return ResponseEntity.ok()
                                .body(new LoginResponse(token, "Login bem-sucedido."));
        } else {
            return new ResponseEntity<>("Credenciais inválidas.", HttpStatus.UNAUTHORIZED); 
        }
    }
}

class LoginResponse {
    public String token;
    public String message;
    
    public LoginResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
}