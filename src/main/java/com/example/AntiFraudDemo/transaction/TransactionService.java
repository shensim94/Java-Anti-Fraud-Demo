package com.example.AntiFraudDemo.transaction;

import com.example.AntiFraudDemo.creditcard.CreditCardService;
import com.example.AntiFraudDemo.exception.ResourceAlreadyExistException;
import com.example.AntiFraudDemo.exception.ResourceNotFoundException;
import com.example.AntiFraudDemo.exception.UnprocessableFeedbackException;
import com.example.AntiFraudDemo.ipaddress.IpAddressService;
import com.example.AntiFraudDemo.transaction.rules.*;
import com.example.AntiFraudDemo.util.Util;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private IpAddressService ipAddressService;
    private CreditCardService creditCardService;
    private TransactionRepository transactionRepository;

    private TransactionCardLimitRepository transactionCardLimitRepository;
    private List<TransactionRule> transactionRules;

    private final Long DEFAULT_MAX_ALLOWED = 200L;

    private final Long DEFAULT_MAX_MANUAL = 1500L;

    public TransactionService(IpAddressService ipAddressService,
                              CreditCardService creditCardService,
                              TransactionRepository transactionRepository,
                              TransactionCardLimitRepository transactionCardLimitRepository,
                              List<TransactionRule> transactionRules) {
        this.ipAddressService = ipAddressService;
        this.creditCardService = creditCardService;
        this.transactionRepository = transactionRepository;
        this.transactionCardLimitRepository = transactionCardLimitRepository;
        this.transactionRules = transactionRules;
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));
    }

    public Iterable<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public Iterable<Transaction> findAllByNumber(String ccNumber) {
        return transactionRepository.findByNumber(ccNumber);
    }

    private void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    //TODO: refactor this
    public TransactionResult processTransaction(Transaction transaction) {

        boolean transactionProhibited = false;
        boolean manualProcessing = false;
        List<String> info = new ArrayList<>();


        for (TransactionRule rule : transactionRules) {
            if (rule.isProhibited(transaction)) {
                info.add(rule.getInfo());
                transactionProhibited = true;
            }
        }

        if (!transactionProhibited) {
            for (TransactionRule rule : transactionRules) {
                if (rule.isManual(transaction)) {
                    info.add(rule.getInfo());
                    manualProcessing = true;
                }
            }
        }

        String transactionInfo = info.isEmpty() ? "none" : Util.constructString(info);

        if (info.isEmpty()) {
            transaction.setResult("ALLOWED");
        } else if (manualProcessing) {
            transaction.setResult("MANUAL_PROCESSING");
        } else {
            transaction.setResult("PROHIBITED");
        }

        saveTransaction(transaction);
        return new TransactionResult(transaction.getResult(), transactionInfo);
    }

    public Transaction addFeedback(TransactionFeedback feedback) {
        Transaction transaction = getTransactionById(feedback.getTransactionId());
        if (!transaction.getFeedback().isBlank()) {
            throw new ResourceAlreadyExistException("This transaction already has feedback.");
        }
        if (transaction.getResult().equals(feedback.getFeedback())) {
            throw new UnprocessableFeedbackException("This transaction is already: " + transaction.getResult());
        }
        adjustTransactionLimit(transaction, feedback.getFeedback());
        transaction.setFeedback(feedback.getFeedback());
        transactionRepository.save(transaction);
        return transaction;
    }

    public void adjustTransactionLimit(Transaction transaction, String transactionFeedback) {

        TransactionCardLimit cardLimit = transactionCardLimitRepository
                .findByNumber(transaction.getNumber())
                .orElse(new TransactionCardLimit(transaction.getNumber(), DEFAULT_MAX_ALLOWED, DEFAULT_MAX_MANUAL));

        Long transactionValue = transaction.getAmount();

        switch (transaction.getResult()) {
            case "ALLOWED":
                switch (transactionFeedback) {
                    case "MANUAL_PROCESSING":
                        cardLimit.decreaseAllowedLimit(transactionValue);
                        break;
                    case "PROHIBITED":
                        cardLimit.decreaseAllowedLimit(transactionValue);
                        cardLimit.decreaseManualLimit(transactionValue);
                        break;
                }
                break;
            case "MANUAL_PROCESSING":
                switch (transactionFeedback) {
                    case "ALLOWED":
                        cardLimit.increaseAllowedLimit(transactionValue);
                        System.out.println("HELLO FROM CARDLIMIT: " + cardLimit.getAllowedLimit());
                        break;
                    case "PROHIBITED":
                        cardLimit.decreaseManualLimit(transactionValue);
                        break;
                }
                break;
            case "PROHIBITED":
                switch (transactionFeedback) {
                    case "ALLOWED":
                        cardLimit.increaseAllowedLimit(transactionValue);
                        cardLimit.increaseManualLimit(transactionValue);
                        break;
                    case "MANUAL_PROCESSING":
                        cardLimit.increaseManualLimit(transactionValue);
                        break;
                }
                break;
        }
        transactionCardLimitRepository.save(cardLimit);
    }

}
