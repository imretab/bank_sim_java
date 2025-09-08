package com.bank_sim.controller;

import com.bank_sim.JwtUtil.JwtUtil;
import com.bank_sim.model.Login;

import com.bank_sim.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @GetMapping("/profile")
    public ResponseEntity<?> profile(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Login user = (Login) auth.getPrincipal();

        return ResponseEntity.ok().body(Map.of("email",user.getEmail(),"username",user.getUsername()));
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest) {
        return loginService.login(loginRequest);
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody Login login){
        return loginService.register(login);
    }
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody Login login,@AuthenticationPrincipal Login authUser){
        return loginService.updateDetails(login, authUser);
    }
}
