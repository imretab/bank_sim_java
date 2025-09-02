package com.bank_sim.controller;

import java.util.List;

import com.bank_sim.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.Repository.MessageRepository;
import com.bank_sim.model.Message;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    LoginRepository loginRepository;
    @GetMapping("/messages/")
    public ResponseEntity<?> getMessages(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Login user = (Login) auth.getPrincipal();
        List<Message> messages = messageRepository.findByLoginId(user.getId());

        return ResponseEntity.ok(messages);
    }
}
