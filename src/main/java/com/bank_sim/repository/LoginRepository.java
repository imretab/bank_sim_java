package com.bank_sim.Repository;

import com.bank_sim.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {
    Login findByEmail(String email);
}
