package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.creditcard.CreditCardService;
import com.example.AntiFraudDemo.transaction.Transaction;


public class CreditCardRule implements TransactionRule {
    private CreditCardService creditCardService;

    public CreditCardRule(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Override
    public boolean isManual(Transaction transaction) {
        return false;
    }

    @Override
    public boolean isProhibited(Transaction transaction) {
        return creditCardService.hasCard(transaction.getNumber());
    }

    @Override
    public String getInfo() {
        return "card-number";
    }
}
