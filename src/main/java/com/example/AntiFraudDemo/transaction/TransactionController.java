package com.example.AntiFraudDemo.transaction;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    private TransactionService transactionService;
    private TransactionMapper transactionMapper;
    private TransactionRepository transactionRepository;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper, TransactionRepository transactionRepository) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/transaction")
    public ResponseEntity<Object> getAllTransactions(@RequestBody TransactionRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime endDate = LocalDateTime.parse(request.date(), formatter);
        LocalDateTime startDate = endDate.minusHours(1);
        Long count = 2L;
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> getTransactionResult(@Valid @RequestBody TransactionRequestDTO dto) {
        Transaction transaction = transactionMapper.toTransaction(dto);
        return new ResponseEntity<>(transactionService.processTransaction(transaction), HttpStatus.OK);
    }

    public record TransactionRequest(String ip, String date) {}
}
