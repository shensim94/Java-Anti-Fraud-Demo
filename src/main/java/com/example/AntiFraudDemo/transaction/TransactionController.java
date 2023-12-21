package com.example.AntiFraudDemo.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/antifraud")
public class TransactionController {
    @PostMapping("/transaction")
    public ResponseEntity<TransactionResult>getTransactionResult(@RequestBody Map<String, Long> payload) {
        if (payload == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (payload.get("amount") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long amount = payload.get("amount");
        if (amount <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (amount <= 200) {
            return new ResponseEntity<>(new TransactionResult("ALLOWED"), HttpStatus.OK);
        } else if (amount <= 1500) {
            return new ResponseEntity<>(new TransactionResult("MANUAL_PROCESSING"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new TransactionResult("PROHIBITED"), HttpStatus.OK);
        }
    }
}
