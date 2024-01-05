package com.example.AntiFraudDemo.transaction;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionMapper {

    public Transaction toTransaction(TransactionRequestDTO dto){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setIp(dto.getIp());
        transaction.setNumber(dto.getNumber());
        transaction.setRegion(dto.getRegion());
        transaction.setDate(LocalDateTime.parse(dto.getDate(), formatter));
        return transaction;
    }
}
