package com.example.AntiFraudDemo.transaction.rules;

import com.example.AntiFraudDemo.transaction.Transaction;
import com.example.AntiFraudDemo.transaction.TransactionRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegionCorrelationRule implements TransactionRule{
    private TransactionRepository transactionRepository;

    public RegionCorrelationRule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public boolean isManual(Transaction transaction) {
        String region = transaction.getRegion();
        String number = transaction.getNumber();
        LocalDateTime endDate = transaction.getDate();
        LocalDateTime startDate = endDate.minusHours(1);
        return transactionRepository.countDistinctByRegionNotAndDateBetween(region, number, startDate, endDate) == 2;
    }

    @Override
    public boolean isProhibited(Transaction transaction) {
        String region = transaction.getRegion();
        String number = transaction.getNumber();
        LocalDateTime endDate = transaction.getDate();
        LocalDateTime startDate = endDate.minusHours(1);
        return transactionRepository.countDistinctByRegionNotAndDateBetween(region, number, startDate, endDate) > 2;
    }

    @Override
    public String getInfo() {
        return "region-correlation";
    }
}
