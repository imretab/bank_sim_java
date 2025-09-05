package com.bank_sim.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bank_sim.Repository.AccountNumberRepository;
import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.model.AccountNumber;
import com.bank_sim.model.Login;

public class TransactionController {
    @Autowired
    AccountNumberRepository accountNumberRepository;
    @Autowired
    LoginRepository loginRepository;
    @PostMapping("/transaction")
    public ResponseEntity<Map<String,String>> makeTransaction(@RequestBody AccountNumber account){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Login user = (Login) auth.getPrincipal();
        if(account == null){
            return ResponseEntity.status(409).body(Map.of("message","Account values are empty"));
        }

        accountNumberRepository.save(account);
        return ResponseEntity.ok().body(Map.of("message","Transaction success"));
    }
}
