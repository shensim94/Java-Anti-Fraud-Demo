package com.example.AntiFraudDemo.transaction;

import com.example.AntiFraudDemo.creditcard.CreditCardDTO;
import com.example.AntiFraudDemo.exception.ResourceAlreadyExistException;
import com.example.AntiFraudDemo.user.User;
import com.example.AntiFraudDemo.user.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {

    private TransactionService transactionService;
    private TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @GetMapping("/history")
    public ResponseEntity<Object> getAllTransactions() {
        Iterable<Transaction> transactions = transactionService.findAll();
        ArrayList<TransactionResponseDTO> transactionsDTO = new ArrayList<>();
        for (Transaction transaction: transactions) {
            transactionsDTO.add(transactionMapper.transactionResponseDTO(transaction));
        }
        return new ResponseEntity<>(transactionsDTO, HttpStatus.OK);
    }

    @GetMapping("/history/{number}")
    public ResponseEntity<Object> getAllTransactionsByCCNumber(@Valid CreditCardDTO dto) {
        Iterable<Transaction> transactions = transactionService.findAllByNumber(dto.getNumber());
        ArrayList<TransactionResponseDTO> transactionsDTO = new ArrayList<>();
        for (Transaction transaction: transactions) {
            transactionsDTO.add(transactionMapper.transactionResponseDTO(transaction));
        }
        return new ResponseEntity<>(transactionsDTO, HttpStatus.OK);
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionResult> getTransactionResult(@Valid @RequestBody TransactionRequestDTO dto) {
        Transaction transaction = transactionMapper.toTransaction(dto);
        return new ResponseEntity<>(transactionService.processTransaction(transaction), HttpStatus.OK);
    }

    @PutMapping("/transaction")
    public ResponseEntity<TransactionResponseDTO> addFeedback(@Valid @RequestBody TransactionFeedback feedback) {
        Transaction transaction = transactionService.getTransactionById(feedback.getTransactionId());
        if (!transaction.getFeedback().isBlank()) {
            return new ResponseEntity<>(transactionMapper.transactionResponseDTO(transaction), HttpStatus.CONFLICT);
        }
        if (transaction.getResult().equals(feedback.getFeedback())) {
            return new ResponseEntity<>(transactionMapper.transactionResponseDTO(transaction), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Transaction modifiedTransaction = transactionService.addFeedback(feedback);
        return new ResponseEntity<>(transactionMapper.transactionResponseDTO(modifiedTransaction), HttpStatus.OK);
    }

}
