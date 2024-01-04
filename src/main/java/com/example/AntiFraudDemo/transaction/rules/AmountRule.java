package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.TransactionDTO;

public class AmountRule implements TransactionRule {


    public boolean isManual(TransactionDTO dto) {
        return dto.getAmount() > 200 && dto.getAmount() <= 1500;
    }
    @Override
    public boolean isProhibited(TransactionDTO dto) {
        return dto.getAmount() > 1500;
    }

    @Override
    public String getInfo() {
        return "amount";
    }
}
