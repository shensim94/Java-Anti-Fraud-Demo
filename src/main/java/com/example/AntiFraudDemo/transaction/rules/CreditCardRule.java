package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.creditcard.CreditCardService;
import com.example.AntiFraudDemo.transaction.TransactionDTO;


public class CreditCardRule implements TransactionRule {
    private CreditCardService creditCardService;

    public CreditCardRule(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Override
    public boolean isManual(TransactionDTO dto) {
        return false;
    }

    @Override
    public boolean isProhibited(TransactionDTO dto) {
        return creditCardService.hasCard(dto.getNumber());
    }

    @Override
    public String getInfo() {
        return "card-number";
    }
}
