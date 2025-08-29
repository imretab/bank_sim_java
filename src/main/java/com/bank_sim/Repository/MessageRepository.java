package com.bank_sim.Repository;

import com.bank_sim.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Message> {
}
