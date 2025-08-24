package com.bank_sim.controller;

import com.bank_sim.JSON_format.JSON_Formatter;
import com.bank_sim.model.Login;
import com.bank_sim.Repository.LoginRepository;

import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    LoginRepository loginRepository;

    private Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    @PostMapping("/login")
    public ResponseEntity<String> tryLogin(
            @RequestBody Login login
    )
    {
        JSON_Formatter jsonFormatter = new JSON_Formatter();
        Login storedLogin = loginRepository.findByEmail(login.getEmail());
        if (storedLogin == null) {
            return ResponseEntity.status(401).body(jsonFormatter.FormatJSON("message","Bad credentials"));
        }
        String rawPassword = login.getPassword();
        String storedEncodedPassword = storedLogin.getPassword();
        boolean areCredentialsGood = passwordEncoder.matches(rawPassword, storedEncodedPassword);
        if(!areCredentialsGood){
            return ResponseEntity.status(401).body(jsonFormatter.FormatJSON("message","Bad credentials"));
        }
        return ResponseEntity.status(200).body('{'+"\"message\""+":"+"\"Successful login\""+'}');
    }
}
