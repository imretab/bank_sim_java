package com.bank_sim.controller;

import java.math.BigDecimal;
import java.util.Map;

import com.bank_sim.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.bank_sim.model.Login;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @PostMapping("/transaction")
    public ResponseEntity<Map<String,String>> makeTransaction(@RequestBody Map<String,String> accountNumber, @AuthenticationPrincipal Login currentUser){
        return transactionService.processTransaction(accountNumber,currentUser);
    }
}
