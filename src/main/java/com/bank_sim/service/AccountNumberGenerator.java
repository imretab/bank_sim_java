package com.bank_sim.service;

import java.util.Random;

public class AccountNumberGenerator {
    Random rnd = new Random();
    private String accountNumber;
    public String getAccountNumber(){
        return this.accountNumber;
    }
    public AccountNumberGenerator(){}
    public String generateAccountNumber(int lengthWithoutChecksum) {
        if (lengthWithoutChecksum <= 0) {
            throw new IllegalArgumentException("Length must be positive.");
        }

        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < lengthWithoutChecksum; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }

        int checksum = calculateLuhnChecksum(accountNumber.toString());
        accountNumber.append(checksum);
        this.accountNumber = accountNumber.toString();
        return accountNumber.toString();
    }
    private static int calculateLuhnChecksum(String number) {
        int sum = 0;
        boolean doubleDigit = true;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return checksum;
    }
}
