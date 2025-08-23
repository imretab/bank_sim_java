package com.bank_sim.repository;

import com.bank_sim.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {
}
