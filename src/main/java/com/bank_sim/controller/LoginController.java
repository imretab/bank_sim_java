package com.bank_sim.controller;

import com.bank_sim.JwtUtil.JwtUtil;
import com.bank_sim.model.Login;
import com.bank_sim.Repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.argon2.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    private JwtUtil jwtUtil;
    private Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            Login user = (Login) authentication.getPrincipal(); // or use UserDetails
            String token = jwtUtil.GenerateToken(user);

            return ResponseEntity.ok(Map.of("accessToken", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody Login login){
        String encpwd = passwordEncoder.encode(login.getPassword());
        if(login.getEmail().isEmpty() ||
                login.getUsername().isEmpty()||
                login.getPassword().isEmpty()){
            return ResponseEntity.status(409).body(Map.of("message","Problem with registration due to missing data(s)"));
        }
        Login newUser = new Login(login.getEmail(),login.getUsername(),"{argon2}"+encpwd);
        loginRepository.save(newUser);
        return ResponseEntity.ok().body(Map.of("message","Registration Success"));
    }
}
