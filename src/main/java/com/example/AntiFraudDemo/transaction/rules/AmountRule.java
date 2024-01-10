package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.Transaction;
import com.example.AntiFraudDemo.transaction.TransactionCardLimit;
import com.example.AntiFraudDemo.transaction.TransactionCardLimitRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AmountRule implements TransactionRule {

    private final long MAX_ALLOWED = 200L;

    private final long MAX_MANUAL = 1500L;

    private TransactionCardLimitRepository cardLimitRepository;

    public AmountRule(TransactionCardLimitRepository cardLimitRepository) {
        this.cardLimitRepository = cardLimitRepository;
    }

    @Override
    public boolean isManual(Transaction transaction) {
        Optional<TransactionCardLimit> cardLimit = cardLimitRepository.findByNumber(transaction.getNumber());
        if (cardLimit.isPresent()) {
            return transaction.getAmount() > cardLimit.get().getAllowedLimit() && transaction.getAmount() <= cardLimit.get().getManualLimit();
        } else {
            return transaction.getAmount() > MAX_ALLOWED && transaction.getAmount() <= MAX_MANUAL;
        }
    }
    @Override
    public boolean isProhibited(Transaction transaction) {
        Optional<TransactionCardLimit> cardLimit = cardLimitRepository.findByNumber(transaction.getNumber());
        if (cardLimit.isPresent()) {
            return transaction.getAmount() > cardLimit.get().getManualLimit();
        } else {
            return transaction.getAmount() > MAX_MANUAL;
        }
    }

    @Override
    public String getInfo() {
        return "amount";
    }
}
