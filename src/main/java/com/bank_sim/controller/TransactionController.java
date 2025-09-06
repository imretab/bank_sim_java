package com.bank_sim.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank_sim.Repository.AccountNumberRepository;
import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.Repository.MessageRepository;
import com.bank_sim.Repository.TransactionRepository;
import com.bank_sim.model.AccountNumber;
import com.bank_sim.model.Login;
import com.bank_sim.model.Message;
import com.bank_sim.model.Transaction;
import com.bank_sim.service.CustomMessageFormatter;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {
    @Autowired
    AccountNumberRepository accountNumberRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    MessageRepository messageRepository;
    @PostMapping("/transaction")
    public ResponseEntity<Map<String,String>> makeTransaction(@RequestBody Map<String,String> accountNumber){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Login user = (Login) auth.getPrincipal();
        if(accountNumber == null){
            return ResponseEntity.status(409).body(Map.of("message","Account values are empty"));
        }
        AccountNumber findNumber = accountNumberRepository.findByAccountNumber(accountNumber.get("accNum"));
        if(findNumber == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Account number wasn't found"));
        }
        AccountNumber getUser = accountNumberRepository.findById(user.getId()).orElse(null);
        BigDecimal amount = new BigDecimal(accountNumber.get("amount"));
        BigDecimal fromAccount = findNumber.getBalance();

        BigDecimal toAccount = getUser.getBalance();
        BigDecimal addBalance = fromAccount.add(amount);
        findNumber.setBalance(addBalance);
        BigDecimal subtractBalance = toAccount.subtract(amount);
        getUser.setBalance(subtractBalance);

        CustomMessageFormatter customMessageFormatter = new CustomMessageFormatter();
        String fromGenerated = customMessageFormatter.GenerateMessage(user,true);
        String toGenerated = customMessageFormatter.GenerateMessage(findNumber.getLogin(),true);
        Message from = new Message(fromGenerated,user);
        Message to = new Message(toGenerated,findNumber.getLogin());
        Transaction newTransaction = new Transaction(user,findNumber.getLogin(),amount);
        transactionRepository.save(newTransaction);
        messageRepository.save(from);
        messageRepository.save(to);
        accountNumberRepository.save(getUser);
        accountNumberRepository.save(findNumber);
        return ResponseEntity.ok().body(Map.of("message","Transaction success"));
    }
}
