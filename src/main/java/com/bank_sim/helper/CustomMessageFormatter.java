package com.bank_sim.helper;

import com.bank_sim.model.Login;

import java.math.BigDecimal;
import java.text.MessageFormat;
//This class will auto-generate messages after a successful/failed transaction
public class CustomMessageFormatter {

    private String message;
    private boolean TransactionSuccess;

    public String getMessage() {
        return message;
    }
    public boolean isTransactionSuccess() {
        return TransactionSuccess;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTransactionSuccess(boolean transactionSuccess) {
        TransactionSuccess = transactionSuccess;
    }

    public CustomMessageFormatter(){}

    public String GenerateMessage(Login login, boolean isTransaction, BigDecimal balance){
        if(isTransaction){
            message = String.format("""
            Dear %s,
            We've updated your balance based on your recent transaction. New balance: %.2f.
            """,login.getUsername(),balance);
            return message;
        }
        message = String.format("""
            Dear %s,
            We've had issues processing your transaction.
            """,login.getUsername());
        return message;
    }
}
