package com.bank_sim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/messages/{id}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable("id") Long userId){
        if(!loginRepository.existsById(userId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Message> userMessages = messageRepository.findByLoginId(userId);
        return ResponseEntity.status(200).body(userMessages);
    }
}
