package com.bank_sim.controller;

import com.bank_sim.model.Login;
import com.bank_sim.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @PostMapping("/login")
    public String tryLogin(
            @RequestBody Login login
    ){
        System.out.println(login.getUsername());
        return login.getUsername();
    }
}
