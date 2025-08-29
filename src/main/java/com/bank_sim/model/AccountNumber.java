package com.bank_sim.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "account_number")
public class AccountNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "number")
    private String account_number;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    @JsonIgnore
    private Login login;
    public AccountNumber(){}
}
