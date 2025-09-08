package com.bank_sim;

import java.util.HashMap;
import java.util.Map;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;

import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.model.Login;
import com.bank_sim.service.TransactionService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;
    @Autowired
    LoginRepository loginRepository;
    Map<String, String> transaction = new HashMap<>();
    ResponseEntity<Map<String, String>> initTestCases(String accountNumber, String amount){
        Login user = loginRepository.findByEmail("user@user.me");
        transaction.put("accNum",accountNumber);
        transaction.put("amount",amount);
        return transactionService.processTransaction(transaction,user);
    }

    ResponseEntity<Map<String,String>> tryTransaction;
    @Test
    @WithUserDetails("user@user.me")
    void shouldRejectWithMissingAmount(){
        tryTransaction = initTestCases("4297468443805588","");
        assertTrue(tryTransaction.getBody().get("message").contains("invalid"));
    }

    @Test
    @WithUserDetails("user@user.me")
    void shouldRejectTransactionWhenAccountNumberIsNotFound(){
        tryTransaction = initTestCases("22121212","500");
        assertTrue(tryTransaction.getBody().get("message").contains("wasn't found"));
    }
    @Test
    @WithUserDetails("user@user.me")
    void shouldRejectTransactionWhenAmountIsNegative(){
        tryTransaction = initTestCases("4297468443805588","-800");
        assertTrue(tryTransaction.getBody().get("message").contains("negative"));
    }

    @Test
    @WithUserDetails("user@user.me")
    void shouldRejectTransactionWhenAccountNumberMatchesUser(){
        tryTransaction = initTestCases("1427476874406882","50000");
        assertTrue(tryTransaction.getBody().get("message").contains("yourself"));
    }

    @Test
    @WithUserDetails("user@user.me")
    void shouldRejectTransactionWhenAmountIsLargerUserBalance(){
        tryTransaction = initTestCases("4297468443805588","5000000");
        assertTrue(tryTransaction.getBody().get("message").equals("Insufficient funds"));
    }
    @Test
    @WithUserDetails("user@user.me")
    @Transactional
    void shouldAllowTransaction(){
        tryTransaction = initTestCases("4297468443805588","5000");
        assertTrue(tryTransaction.getBody().get("message").contains("success"));
    }
}
