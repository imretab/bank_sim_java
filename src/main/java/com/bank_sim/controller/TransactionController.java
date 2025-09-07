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
        Login currentUser = (Login) auth.getPrincipal();
        if (accountNumber == null || accountNumber.get("accNum") == null || accountNumber.get("amount") == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Account values are empty or invalid"));
        }
        String accNum = accountNumber.get("accNum");
        AccountNumber recipientAccount  = accountNumberRepository.findByAccountNumber(accNum);
        if(recipientAccount == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Account number wasn't found"));
        }
        AccountNumber senderAccount = accountNumberRepository.findById(currentUser.getId()).orElse(null);
        if(recipientAccount.getLogin().getId() == currentUser.getId()){
            return ResponseEntity.status(400).body(Map.of("message","You can't send money to yourself"));
        }

        BigDecimal amount = new BigDecimal(accountNumber.get("amount"));
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            return ResponseEntity.status(400).body(Map.of("message","Sorry, you cannot enter a negative number"));
        }
        if(senderAccount.getBalance().compareTo(amount) < 0){
            return ResponseEntity.status(400).body(Map.of("message","Insufficent funds"));
        }
        recipientAccount.setBalance(recipientAccount.getBalance().add(amount));

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));

        CustomMessageFormatter customMessageFormatter = new CustomMessageFormatter();
        Message from = new Message(customMessageFormatter.GenerateMessage(currentUser,true,senderAccount.getBalance()),
                currentUser);
        Message to = new Message(customMessageFormatter.GenerateMessage(recipientAccount.getLogin(),true,recipientAccount.getBalance()),
                recipientAccount.getLogin());
        Transaction newTransaction = new Transaction(currentUser,recipientAccount.getLogin(),amount);
        transactionRepository.save(newTransaction);
        messageRepository.save(from);
        messageRepository.save(to);
        accountNumberRepository.save(senderAccount);
        accountNumberRepository.save(recipientAccount);
        return ResponseEntity.ok().body(Map.of("message","Transaction success"));
    }
}
