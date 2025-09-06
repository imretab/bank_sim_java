package com.bank_sim.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_id", unique = false)
    @JsonIgnore
    private Login from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_id", unique = false)
    @JsonIgnore
    private Login to;

    @Column(name ="amount")
    private BigDecimal amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Login getFrom() {
        return from;
    }

    public void setFrom(Login from) {
        this.from = from;
    }

    public Login getTo() {
        return to;
    }

    public void setTo(Login to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Transaction(){}

    public Transaction(Login from, Login to, BigDecimal amount){
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
