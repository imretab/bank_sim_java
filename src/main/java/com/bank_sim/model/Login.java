package com.bank_sim.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class Login implements UserDetails {
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
    public long getId(){return id;}
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
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;  // customize as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // customize as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // customize as needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // customize as needed
    }
}
