package com.bank_sim.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "account_number")
public class AccountNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private String accountNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    @JsonIgnore
    private Login login;

    @Column(name = "balance")
    private BigDecimal balance;
    public AccountNumber(){}
    public AccountNumber(String accountNumber, Login login){
        this.accountNumber = accountNumber;
        this.login = login;
        this.balance = new BigDecimal(300000); //example starting balance
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
