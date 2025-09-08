package com.bank_sim;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;

import com.bank_sim.JwtUtil.JwtUtil;
import com.bank_sim.Repository.LoginRepository;
import com.bank_sim.model.Login;
import com.bank_sim.service.LoginService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginServiceTest {
    @Autowired
    LoginService loginService;
    @Autowired
    private LoginRepository loginRepository;
    ResponseEntity<?> setAuth(String email, String password){
        Login login = new Login();
        login.setEmail(email);
        login.setPassword(password);
        return loginService.login(login);
    }
    ResponseEntity<Map<String,String>> setTestRegister(String email, String username, String password){
        Login login = new Login(email,username,password);
        return loginService.register(login);

    }
    @Test
    void shouldRejectLoginAttempt(){
        assertTrue(setAuth("user@user.me","").getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(401)));
    }
    @Test
    void shouldAllowLoginAttempt(){
        assertTrue(setAuth("user@user.me","password").getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200)));
    }
    @Test
    void shouldRejectRegistrationWhenValuesMissing(){
        assertTrue(setTestRegister("tryUser@user.me","","pass").getBody().get("message").contains("Problem"));
    }
    @Test
    void shouldRejectRegistrationWhenEmailExists(){
        assertTrue(setTestRegister("user@user.me","user","pass").getBody().get("message").contains("exists"));
    }
    @Test
    void shouldThrowNullPointerExpectionWhenJWTTokenIsNotValid() throws NullPointerException{

        Login user = new Login("user@user.me", "user", "pass");
        try {
            ResponseEntity<?> tryUpdate = loginService.updateDetails(user, null);
            System.out.println(tryUpdate.getBody());
        }
        catch (NullPointerException e){
            assertThrows(NullPointerException.class,()->{
                loginService.updateDetails(new Login("user@user.me", "user", "pass"),null);
            });
        }
    }
    @Test
    @WithUserDetails("user@user.me")
    void shouldAllowUpdateWhenJWTTokenIsValid(){
        Login authLogin = loginRepository.findByEmail("user@user.me");
        Login newUser = new Login("user@user.me", "user", "password");
        ResponseEntity<Map<String,String>> tryUpdate = loginService.updateDetails(newUser,authLogin);
        assertTrue(tryUpdate.getBody().get("message").contains("success"));
    }
}
