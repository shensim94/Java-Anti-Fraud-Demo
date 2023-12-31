package com.example.AntiFraudDemo.creditcard;

import com.example.AntiFraudDemo.validation.ValidCreditCard;
import jakarta.validation.constraints.NotBlank;

public class CreditCardDTO {

    @NotBlank(message = "credit card number is required")
    @ValidCreditCard
    private String number;

    public CreditCardDTO() {
    }

    public CreditCardDTO(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
