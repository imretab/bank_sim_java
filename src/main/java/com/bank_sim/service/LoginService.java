package com.bank_sim.service;

import com.bank_sim.Repository.AccountNumberRepository;
import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.helper.AccountNumberGenerator;
import com.bank_sim.model.AccountNumber;
import com.bank_sim.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    AccountNumberRepository accountNumberRepository;
    private final String responseKey = "message";
    private AccountNumberGenerator accountNumberGenerator;
    private Argon2PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public ResponseEntity<Map<String,String>> register(Login login){
        if(login.getEmail().isEmpty() ||
                login.getUsername().isEmpty()||
                login.getPassword().isEmpty()){
            return ResponseEntity.status(409).body(Map.of(responseKey,"Problem with registration due to missing data(s)"));
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
