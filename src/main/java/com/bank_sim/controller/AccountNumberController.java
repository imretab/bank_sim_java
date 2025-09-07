package com.bank_sim.controller;

import com.bank_sim.Repository.AccountNumberRepository;
import com.bank_sim.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountNumberController {
    @Autowired
    AccountNumberRepository accountNumberRepository;

    @GetMapping("/balance")
    public BigDecimal getCurrentBalance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Login user = (Login) auth.getPrincipal();
        if(accountNumberRepository.findById(user.getId()).isPresent()) {
            return accountNumberRepository.findById(user.getId()).get().getBalance();
        }
        return BigDecimal.ZERO;
    }
}
