package com.bank_sim.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="username",nullable = false)
    private String username;
    @Column(name="password",nullable = false)
    private String password;

    public String getPassword() {
        return password;
    }
    public String getUsername(){
        return username;
    }
    public Login(String username, String password){
        this.username = username;
        this.password = password;
    }
}
