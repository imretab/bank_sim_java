package com.bank_sim.Repository;

import com.bank_sim.model.AccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountNumberRepository extends JpaRepository<AccountNumber,AccountNumber> {
}
