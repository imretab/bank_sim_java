package com.bank_sim.service;


import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Login user = loginRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return user; // return Login as UserDetails
    }
}