package com.example.AntiFraudDemo.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class TransactionFeedback {

    @NotNull
    private Long transactionId;

    @NotBlank
    @Pattern(regexp = "ALLOWED|MANUAL_PROCESSING|PROHIBITED", message = "Invalid feedback type")
    private String feedback;

    public TransactionFeedback() {
    }

    public TransactionFeedback(Long transactionId, String feedback) {
        this.transactionId = transactionId;
        this.feedback = feedback;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
