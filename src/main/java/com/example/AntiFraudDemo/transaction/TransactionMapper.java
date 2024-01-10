package com.example.AntiFraudDemo.transaction;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionMapper {

    public Transaction toTransaction(TransactionRequestDTO dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setIp(dto.getIp());
        transaction.setNumber(dto.getNumber());
        transaction.setRegion(dto.getRegion());
        transaction.setDate(LocalDateTime.parse(dto.getDate(), formatter));
        return transaction;
    }

    public TransactionResponseDTO transactionResponseDTO(Transaction transaction) {
        TransactionResponseDTO dto = new TransactionResponseDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setIp(transaction.getIp());
        dto.setNumber(transaction.getNumber());
        dto.setRegion(transaction.getRegion());
        dto.setDate(transaction.getDate().toString());
        dto.setResult(transaction.getResult());
        dto.setFeedback(transaction.getFeedback());
        return dto;
    }
}
