package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.Transaction;

public interface TransactionRule {
    boolean isManual(Transaction transaction);

    boolean isProhibited(Transaction transaction);

    String getInfo();
}
