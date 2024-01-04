package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.TransactionDTO;

public interface TransactionRule {
    boolean isManual(TransactionDTO dto);

    boolean isProhibited(TransactionDTO dto);

    String getInfo();
}
