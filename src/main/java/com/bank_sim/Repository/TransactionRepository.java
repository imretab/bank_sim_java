package com.bank_sim.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_sim.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
