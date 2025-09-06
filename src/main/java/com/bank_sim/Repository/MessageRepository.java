package com.bank_sim.Repository;

import java.util.List;

import com.bank_sim.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByLoginId(long id);
}
