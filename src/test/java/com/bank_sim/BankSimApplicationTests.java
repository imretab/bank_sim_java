package com.bank_sim;

import com.bank_sim.helper.AccountNumberGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BankSimApplicationTests {

	/*@Test
	void contextLoads() {
		Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
		String result = encoder.encode("asd");
		assertTrue(encoder.matches("as",result));
	}*/
	@Test
	void GenerateAccountNumber(){
		AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator();
		accountNumberGenerator.generateAccountNumber(15);
		System.out.println(accountNumberGenerator.getAccountNumber());
	}
}
