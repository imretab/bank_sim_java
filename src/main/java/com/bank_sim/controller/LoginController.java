package com.bank_sim.controller;

import com.bank_sim.JwtUtil.JwtUtil;
import com.bank_sim.model.Login;
import com.bank_sim.Repository.LoginRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    private JwtUtil jwtUtil;
    private Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> tryLogin(
            @RequestBody Login login
    )
    {
        Login storedLogin = loginRepository.findByEmail(login.getEmail());

        if (storedLogin == null ||
                !passwordEncoder.matches(login.getPassword(), storedLogin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Bad credentials"));
        }

        String jwt = jwtUtil.GenerateToken(storedLogin);
        return ResponseEntity.ok(Map.of("accessToken", jwt));
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody Login login){
        String encpwd = passwordEncoder.encode(login.getPassword());
        Login newUser = new Login(login.getEmail(),login.getUsername(),encpwd);
        loginRepository.save(newUser);
        return ResponseEntity.ok().body(Map.of("message","Registration Success"));
    }
}
