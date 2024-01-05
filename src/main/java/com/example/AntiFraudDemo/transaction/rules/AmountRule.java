package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.Transaction;

public class AmountRule implements TransactionRule {


    public boolean isManual(Transaction transaction) {
        return transaction.getAmount() > 200 && transaction.getAmount() <= 1500;
    }
    @Override
    public boolean isProhibited(Transaction transaction) {
        return transaction.getAmount() > 1500;
    }

    @Override
    public String getInfo() {
        return "amount";
    }
}
