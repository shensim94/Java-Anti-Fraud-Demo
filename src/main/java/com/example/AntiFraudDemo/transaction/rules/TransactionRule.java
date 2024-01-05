package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.Transaction;

public interface TransactionRule {
    boolean isManual(Transaction dto);

    boolean isProhibited(Transaction dto);

    String getInfo();
}
