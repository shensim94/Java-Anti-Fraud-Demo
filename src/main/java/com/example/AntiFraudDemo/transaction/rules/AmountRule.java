package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.Transaction;

public class AmountRule implements TransactionRule {


    public boolean isManual(Transaction dto) {
        return dto.getAmount() > 200 && dto.getAmount() <= 1500;
    }
    @Override
    public boolean isProhibited(Transaction dto) {
        return dto.getAmount() > 1500;
    }

    @Override
    public String getInfo() {
        return "amount";
    }
}
