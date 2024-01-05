package com.example.AntiFraudDemo.transaction;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    private TransactionService transactionService;
    private TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping("/transaction")
    public ResponseEntity<Iterable<Transaction>> getAllTransactions() {
        return new ResponseEntity<>(transactionService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResponse> getTransactionResult(@Valid @RequestBody TransactionRequestDTO dto) {
        Transaction transaction = transactionMapper.toTransaction(dto);
        return new ResponseEntity<>(transactionService.processTransaction(transaction), HttpStatus.OK);
    }
}
