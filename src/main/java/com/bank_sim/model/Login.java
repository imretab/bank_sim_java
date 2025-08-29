package com.bank_sim.model;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name="username",nullable = false)
    private String username;
    @Column(name="password",nullable = false)
    private String password;
    public Login(){}
    public Login(String email,String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public String getId(){return Long.toString(id);}
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername(){
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
