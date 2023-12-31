package com.example.AntiFraudDemo.creditcard;

import com.example.AntiFraudDemo.validation.CreditCardValidator;
import com.example.AntiFraudDemo.validation.ValidCreditCard;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "credit card number is required")
    @ValidCreditCard
    private String number;

    public CreditCard() {}
    public CreditCard(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
