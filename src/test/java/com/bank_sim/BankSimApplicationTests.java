package com.bank_sim;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BankSimApplicationTests {

	@Test
	void contextLoads() {
		Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		String result = encoder.encode("asd");
		assertTrue(encoder.matches("as",result));
	}

}
