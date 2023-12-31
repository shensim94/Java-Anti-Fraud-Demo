package com.example.AntiFraudDemo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreditCardValidator implements ConstraintValidator<ValidCreditCard, String> {

    @Override
    public boolean isValid(String number, ConstraintValidatorContext context) {
        if (number == null || number.isBlank()) {
            return false;
        }
        int checksum = Character.getNumericValue(number.charAt(number.length() - 1));
        int total = 0;

        for (int i = number.length() - 2; i >= 0; i--) {
            int sum = 0;
            int digit = Character.getNumericValue(number.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
            }

            sum = digit / 10 + digit % 10;
            total += sum;
        }

        return 10 - total % 10 == checksum;
    }

}
