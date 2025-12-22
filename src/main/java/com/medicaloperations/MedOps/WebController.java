package com.medicaloperations.MedOps;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String welcome() {
        return "home"; 
    }
    
    @GetMapping("/pacientpannel")
    public String pacientPannel() {
        return "pacientpannel"; 
    }
    
    @GetMapping("/myschedule")
    public String schedule() {
        return "scheduling"; 
    }
    
    @GetMapping("/agendamento")
    public String agendamentoConsulta() {
        return "agendamento"; 
    }
    
    @GetMapping("/myexams")
    public String exams() {
        return "exams"; 
    }
    
    @GetMapping("/createaccount")
    public String createAccount() {
        return "createaccount"; 
    }
    
    @GetMapping("/forgotpassword")
    public String reset_password() {
        return "forgotpassword"; 
    }
    
}