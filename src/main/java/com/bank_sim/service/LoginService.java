package com.bank_sim.service;

import com.bank_sim.JwtUtil.JwtUtil;
import com.bank_sim.Repository.AccountNumberRepository;
import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.helper.AccountNumberGenerator;
import com.bank_sim.model.AccountNumber;
import com.bank_sim.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    AccountNumberRepository accountNumberRepository;
    private final String responseKey = "message";
    private AccountNumberGenerator accountNumberGenerator;
    private Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public ResponseEntity<?> login(Login loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            Login user = (Login) authentication.getPrincipal();
            String token = jwtUtil.GenerateToken(user);

            return ResponseEntity.ok(Map.of("accessToken", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    public ResponseEntity<Map<String,String>> register(Login login){
        if(login.getEmail().isEmpty() ||
                login.getUsername().isEmpty()||
                login.getPassword().isEmpty()){
            return ResponseEntity.status(409).body(Map.of(responseKey,"Problem with registration due to missing data(s)"));
        }
        if(loginRepository.findByEmail(login.getEmail()) != null){
            return ResponseEntity.status(400).body(Map.of(responseKey,"This email exists in our database"));
        }
        String encpwd = String.format("{argon2}%s",passwordEncoder.encode(login.getPassword()));
        Login newUser = new Login(login.getEmail(),login.getUsername(),encpwd);
        loginRepository.save(newUser);
        accountNumberGenerator = new AccountNumberGenerator();
        String generatedAccountNumber = accountNumberGenerator.generateAccountNumber(15);
        AccountNumber newAccount = new AccountNumber(generatedAccountNumber,newUser);
        accountNumberRepository.save(newAccount);
        return ResponseEntity.ok().body(Map.of(responseKey,"Registration Success"));
    }
    public ResponseEntity<Map<String,String>> updateDetails(Login login, Login authLogin){
        if(login.getEmail().isEmpty() ||
                login.getUsername().isEmpty() ||
                login.getPassword().isEmpty()){
            return ResponseEntity.status(409).body(Map.of(responseKey,"Problem with changes due to missing data(s)"));
        }
        Login updated = loginRepository.findById(authLogin.getId()).orElse(null);
        String encpwd = String.format("{argon2}%s",passwordEncoder.encode(login.getPassword()));
        updated.setEmail(login.getEmail());
        updated.setUsername(login.getUsername());
        updated.setPassword(encpwd);
        loginRepository.save(updated);
        return ResponseEntity.status(200).body(Map.of(responseKey,"Update successful"));
    }
}
